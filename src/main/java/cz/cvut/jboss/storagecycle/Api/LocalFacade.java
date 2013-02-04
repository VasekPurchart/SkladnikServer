package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.Person.Person;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class LocalFacade {

	@Inject
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

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

	public void transferToTechnician(ProductType type, int count, Person technician) throws StockNotAvailableException {
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

}
