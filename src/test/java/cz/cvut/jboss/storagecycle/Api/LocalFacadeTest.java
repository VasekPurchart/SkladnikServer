package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.EntityManagerProducer;
import cz.cvut.jboss.storagecycle.Person.Person;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import cz.cvut.jboss.storagecycle.VendingMachine.Recipe;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import java.io.File;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
					Person.class,
					ProductStock.class,
					ProductType.class,
					Audit.class,
					AuditLog.class,
					Recipe.class,
					ServiceVisit.class,
					VendingMachine.class,
					Warehouse.class
				)
    			.addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
				.addAsResource(new File("src/main/resources/import.sql"), "import.sql")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml");
    }

    @Test
    public void testGetWarehouse() {
		assertTrue(facade.getWarehouse() instanceof Warehouse);
    }

	@Test
	public void testImportToWarehouse() {
		em.getTransaction().begin();
		ProductType type = new ProductType();
		type.setName("CocaCola");
		em.persist(type);

		facade.importToWarehouse(type, 20);
		facade.importToWarehouse(type, 20);
		em.getTransaction().commit();

		assertEquals(40, facade.getWarehouse().getStockOfType(type).getCount());
	}
}
