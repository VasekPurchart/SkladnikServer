package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.EntityManagerProducer;
import cz.cvut.jboss.storagecycle.Person.Auditor;
import cz.cvut.jboss.storagecycle.Person.Person;
import cz.cvut.jboss.storagecycle.Person.Technician;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import cz.cvut.jboss.storagecycle.VendingMachine.Recipe;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LocalFacadeTest {

	@Inject
	private EntityManager em;

    @Inject
    private LocalFacade facade;

    @Deployment
    public static Archive<?> getDeployment() {
    	return ShrinkWrap.create(WebArchive.class, "test.war")
    			.addClasses(
					EntityManagerProducer.class,
					LocalFacade.class,
					StockNotAvailableException.class,
					Person.class,
					Technician.class,
					ProductStock.class,
					ProductType.class,
					Audit.class,
					AuditLog.class,
					AuditReport.class,
					Auditor.class,
					Recipe.class,
					ServiceVisit.class,
					VendingMachine.class,
					Warehouse.class,
					TechnicianUpdateData.class
				)
    			.addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
				.addAsResource(new File("src/main/resources/import.sql"), "import.sql")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml");
    }

	@Before
	public void setUp() {
		em.getTransaction().begin();
	}

	@After
	public void tearDown() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
	}

    @Test
    public void testGetWarehouse() {
		assertTrue(facade.getWarehouse() instanceof Warehouse);
    }

	@Test
	public void testImportToWarehouse() {
		ProductType type = new ProductType();
		type.setName("CocaCola");
		em.persist(type);

		facade.importToWarehouse(type, 20);
		facade.importToWarehouse(type, 20);
		em.getTransaction().commit();

		assertEquals(40, facade.getWarehouse().getStockOfType(type).getCount());
	}

	@Test(expected=StockNotAvailableException.class)
	public void testTransferToTechnicianNonexistentStock() throws StockNotAvailableException {
		ProductType type = new ProductType();
		type.setName("Pepsi");
		em.persist(type);

		Technician technician = em.find(Technician.class, 1L);
		facade.transferToTechnician(type, 10, technician);
	}

	@Test(expected=StockNotAvailableException.class)
	public void testTransferToTechnicianDepletedStock() throws StockNotAvailableException {
		ProductType type = new ProductType();
		type.setName("RC Cola");
		em.persist(type);

		facade.importToWarehouse(type, 10);

		Technician technician = em.find(Technician.class, 1L);
		facade.transferToTechnician(type, 20, technician);
	}

	@Test
	public void testTransferToTechnician() throws StockNotAvailableException {
		ProductType type = new ProductType();
		type.setName("Coca Cola");
		em.persist(type);

		facade.importToWarehouse(type, 20);

		Technician technician = em.find(Technician.class, 1L);
		facade.transferToTechnician(type, 5, technician);
		facade.transferToTechnician(type, 5, technician);

		em.getTransaction().commit();

		assertEquals(10, facade.getWarehouse().getStockOfType(type).getCount());
		assertEquals(10, technician.getStockOfType(type).getCount());
	}

	@Test
	public void testVisitVendingMachine() {
		ProductType type = new ProductType();
		type.setName("Foo Bar");
		em.persist(type);

		Technician technician = em.find(Technician.class, 1L);
		ProductStock stock = new ProductStock();
		stock.setProductType(type);
		stock.incrementCount(10);
		em.persist(stock);
		technician.addStock(stock);

		VendingMachine machine = em.find(VendingMachine.class, 1L);
		Date date = new Date();

		Collection<ProductStock> items = new ArrayList<ProductStock>();
		items.add(stock);
		ServiceVisit visit = facade.visitVendingMachine(technician, machine, date, items);
		em.getTransaction().commit();

		assertNull(technician.getStockOfType(type));
		assertEquals(10, visit.getStockOfType(type).getCount());
	}

	@Test
	public void testSetCashWithdrawnForVisit() {
		ServiceVisit visit = new ServiceVisit();
		visit.visit(em.find(VendingMachine.class, 1L), em.find(Technician.class, 1L), new Date());
		em.persist(visit);
		facade.setCashWithdrawnForVisit(visit, 100);
		em.getTransaction().commit();
		assertEquals(100, visit.getWithdrawnCash());
	}

	@Test
	public void testTechnicianUpdateData() {
		ProductType type = new ProductType();
		type.setName("Mirinda");
		em.persist(type);

		facade.importToWarehouse(type, 20);
		em.getTransaction().commit();

		TechnicianUpdateData data = facade.technicianUpdateData(em.find(Technician.class, 1L));
		assertTrue(data.getItems().size() > 1);
		assertEquals(1, data.getVendingMachines().size());
	}

	@Test
	public void testSendAuditOfFiveSpriteBottles() {
		Auditor auditor = em.find(Auditor.class, 2L);
		VendingMachine machine = em.find(VendingMachine.class, 1L);
		Collection<AuditLog> logs = new ArrayList<AuditLog>();
		Date date = new Date();

		final ProductType type = new ProductType();
		type.setName("Sprite");
		em.persist(type);

		List<ProductType> types = new ArrayList<ProductType>() {{
			add(type);
		}};
		Recipe recipe = new Recipe(types, 20);

		AuditLog log = new AuditLog(5, recipe);
		logs.add(log);
		Audit audit = facade.sendAudit(auditor, machine, logs, date);
		em.getTransaction().commit();

		assertSame(date, audit.getDateTime());
		assertSame(machine, audit.getVendingMachine());
		assertSame(auditor, audit.getAuditor());
		assertSame(log, audit.getRecipeLogs().get(0));
	}

	private Audit createAudit(Date date, VendingMachine machine) {
		Auditor auditor = em.find(Auditor.class, 2L);
		Collection<AuditLog> logs = new ArrayList<AuditLog>();

		final ProductType type = new ProductType();
		type.setName("Mirinda");
		em.persist(type);

		List<ProductType> types = new ArrayList<ProductType>() {{
			add(type);
		}};
		Recipe recipe = new Recipe(types, 20);

		AuditLog log = new AuditLog(5, recipe);
		logs.add(log);
		return facade.sendAudit(auditor, machine, logs, date);
	}

	@Test
	public void testExportAudits() {
		VendingMachine machine = new VendingMachine();
		machine.setAddress("Jihlavská 9");
		machine.setNumber(230);
		em.persist(machine);

		Audit audit = createAudit(new Date(113, 1, 5), machine);

		final ProductType type = new ProductType();
		type.setName("Cappy");
		em.persist(type);

		ProductStock stock = new ProductStock();
		stock.setProductType(type);
		stock.incrementCount(10);
		em.persist(stock);

		Collection<ProductStock> items = new ArrayList<ProductStock>();
		items.add(stock);
		ServiceVisit visit = facade.visitVendingMachine(em.find(Technician.class, 1L), machine, new Date(113, 1, 2), items);
		em.getTransaction().commit();

		AuditReport report = facade.exportAudits(audit);
		assertNull(report.getLastAudit());
		assertSame(audit, report.getCurrentAudit());
		assertEquals(1, report.getServiceVisits().size());
		assertTrue(report.getServiceVisits().contains(visit));
	}

	@Test
	public void testExportMoreAudits() {
		VendingMachine machine = new VendingMachine();
		machine.setAddress("Jihlavská 9");
		machine.setNumber(230);
		em.persist(machine);

		Audit lastAudit = createAudit(new Date(113, 1, 2), machine);
		Audit currentAudit = createAudit(new Date(113, 1, 5), machine);

		final ProductType type = new ProductType();
		type.setName("Cappy");
		em.persist(type);

		ProductStock stock = new ProductStock();
		stock.setProductType(type);
		stock.incrementCount(10);
		em.persist(stock);

		Collection<ProductStock> items = new ArrayList<ProductStock>();
		items.add(stock);
		ServiceVisit visit = facade.visitVendingMachine(em.find(Technician.class, 1L), machine, new Date(113, 1, 2), items);
		em.getTransaction().commit();

		AuditReport report = facade.exportAudits(currentAudit);
		assertSame(lastAudit, report.getLastAudit());
		assertSame(currentAudit, report.getCurrentAudit());
		assertEquals(1, report.getServiceVisits().size());
		assertTrue(report.getServiceVisits().contains(visit));
	}

	@Test
	public void testGetProductTypes() {
		ProductType type = new ProductType();
		type.setName("Fanta");
		em.persist(type);
		em.flush();
		em.getTransaction().commit();

		Collection<ProductType> types = facade.getProductTypes();
		assertTrue(types.contains(type));
	}

	@Test
	public void testGetTechnicians() {
		Technician technician = em.find(Technician.class, 1L);
		Collection<Technician> technicians = facade.getTechnicians();
		assertTrue(technicians.contains(technician));
	}

	@Test
	public void testGetVendingMachines() {
		VendingMachine machine = em.find(VendingMachine.class, 1L);
		Collection<VendingMachine> machines = facade.getVendingMachines();
		assertTrue(machines.contains(machine));
	}

	@Test
	public void testGetAudits() {
		Auditor auditor = em.find(Auditor.class, 2L);
		VendingMachine machine = em.find(VendingMachine.class, 1L);
		Collection<AuditLog> logs = new ArrayList<AuditLog>();
		Audit audit = facade.sendAudit(auditor, machine, logs, new Date());
		em.getTransaction().commit();

		Collection<Audit> audits = facade.getAudits(machine);
		assertTrue(audits.contains(audit));
	}

}
