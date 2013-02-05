package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.Person.Auditor;
import cz.cvut.jboss.storagecycle.Person.Technician;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class LocalFacade {

	@Inject
	private EntityManager em;

	public Warehouse getWarehouse() {
		return em.find(Warehouse.class, 1L);
	}

	public void importToWarehouse(ProductType type, int count) {
		Warehouse warehouse = getWarehouse();
		ProductStock stock = warehouse.getStockOfType(type);
		if (stock == null) {
			stock = new ProductStock();
			stock.setProductType(type);
			warehouse.addStock(stock);
			em.persist(stock);
		}
		stock.incrementCount(count);
		em.flush();
	}

	public void transferToTechnician(ProductType type, int count, Technician technician) throws StockNotAvailableException {
		Warehouse warehouse = getWarehouse();
		ProductStock warehouseStock = warehouse.getStockOfType(type);
		if (warehouseStock == null) {
			throw new StockNotAvailableException("Warehouse does not have stock of " + type.getName() + ".");
		}
		if (warehouseStock.getCount() < count) {
			throw new StockNotAvailableException(
				"Warehouse has only " + warehouseStock.getCount() + " pieces of " + type.getName() + ", " + count + " requested."
			);
		}
		warehouseStock.decrementCount(count);
		ProductStock technicianStock = technician.getStockOfType(type);
		if (technicianStock == null) {
			technicianStock = new ProductStock();
			technicianStock.setProductType(type);
			technician.addStock(technicianStock);
			em.persist(technicianStock);
		}
		technicianStock.incrementCount(count);
		em.flush();
	}

	public ServiceVisit visitVendingMachine(Technician technician, VendingMachine vendingMachine, Date date, Collection<ProductStock> items) {
		ServiceVisit visit = new ServiceVisit();
		visit.visit(vendingMachine, technician, date);
		em.persist(visit);

		for (ProductStock stock : items) {
			technician.removeStock(stock);
			visit.addStock(stock);
		}

		em.flush();

		return visit;
	}

	public void setCashWithdrawnForVisit(ServiceVisit visit, int cash) {
		visit.setWithdrawnCash(cash);
		em.flush();
	}

	public TechnicianUpdateData technicianUpdateData(Technician technician) {
		Collection<ProductStock> items = getWarehouse().getItems();
		Collection<VendingMachine> vendingMachines = em.createQuery("SELECT e FROM VendingMachine e").getResultList();

		return new TechnicianUpdateData(items, vendingMachines);
	}

	public Audit sendAudit(Auditor auditor, VendingMachine machine, Collection<AuditLog> logs, Date date) {
		Audit audit = Audit.create(auditor, machine, date);
		em.persist(audit);

		for (AuditLog log : logs) {
			em.persist(log);
			audit.addRecipeLog(log);
		}

		em.flush();

		return audit;
	}

	public AuditReport exportAudits(Audit auditTo) {
		List<Audit> auditList = em.createQuery(
			"SELECT e FROM Audit e WHERE e.dateTime <= :datetime AND e.id != :id AND e.vendingMachine = :vendingMachine ORDER BY e.dateTime DESC"
		).setParameter("id", auditTo.getId())
		.setParameter("datetime", auditTo.getDateTime())
		.setParameter("vendingMachine", auditTo.getVendingMachine())
		.setMaxResults(1)
		.getResultList();

		Audit auditFrom = !auditList.isEmpty() ? auditList.get(0) : null;

		Date to = auditTo.getDateTime();

		Date from = new Date(70, 1, 1);
		if (auditFrom != null) {
			from = auditFrom.getDateTime();
		}

		List<ServiceVisit> visits = em.createQuery("SELECT e FROM ServiceVisit e WHERE e.vendingMachine = :vendingMachine AND e.dateTime >= :from AND e.dateTime <= :to")
		.setParameter("vendingMachine", auditTo.getVendingMachine())
		.setParameter("from", from)
		.setParameter("to", to)
		.getResultList();

		return new AuditReport(visits, auditFrom, auditTo);
	}

	public Collection<ProductType> getProductTypes() {
		return em.createQuery("SELECT e FROM ProductType e ORDER BY e.name ASC").getResultList();
	}

}
