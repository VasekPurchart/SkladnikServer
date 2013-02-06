package cz.cvut.jboss.storagecycle.Api.Local;

import cz.cvut.jboss.storagecycle.Product.StockNotAvailableException;
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
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

@Stateless
public class StorageCycleLocalFacade {

	@Inject
	private EntityManager em;

	@Inject
	private CacheContainer cacheContainer;

	private Cache<String, Object> getCache() {
		return cacheContainer.getCache();
	}

	public Warehouse getWarehouse() {
		return em.find(Warehouse.class, 1L);
	}

	public void importToWarehouse(ProductType type, int count) {
		Warehouse warehouse = getWarehouse();
		warehouse.addStock(new ProductStock(count, type));
		em.flush();
	}

	public void transferToTechnician(ProductType type, int count, Technician technician) throws StockNotAvailableException {
		Warehouse warehouse = getWarehouse();
		ProductStock stock = new ProductStock(count, type);
		warehouse.removeStock(stock);
		technician.addStock(stock);
		em.flush();
	}

	public ServiceVisit visitVendingMachine(Technician technician, VendingMachine vendingMachine, Date date, Collection<ProductStock> items) throws StockNotAvailableException {
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
		String key = "audits" + auditTo.getId();
		if (!getCache().containsKey(key)) {
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

			getCache().put(key, new AuditReport(visits, auditFrom, auditTo));
		}

		return (AuditReport) getCache().get(key);
	}

	public Collection<ProductType> getProductTypes() {
		return em.createQuery("SELECT e FROM ProductType e ORDER BY e.name ASC").getResultList();
	}

	public Collection<Technician> getTechnicians() {
		return em.createQuery("SELECT e FROM Technician e ORDER BY e.name ASC").getResultList();
	}

	public Collection<VendingMachine> getVendingMachines() {
		return em.createQuery("SELECT e FROM VendingMachine e ORDER BY e.number ASC").getResultList();
	}

	public Collection<Audit> getAudits(VendingMachine machine) {
		return em.createQuery(
			"SELECT e FROM Audit e WHERE e.vendingMachine = :vendingMachine ORDER BY e.dateTime ASC"
		).setParameter("vendingMachine", machine).getResultList();
	}

}
