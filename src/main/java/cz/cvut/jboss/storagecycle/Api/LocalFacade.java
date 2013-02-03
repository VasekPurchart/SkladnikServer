package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.Person.Person;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

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


}
