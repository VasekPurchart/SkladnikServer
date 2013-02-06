package cz.cvut.jboss.storagecycle.Person;

import cz.cvut.jboss.storagecycle.Product.StockNotAvailableException;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.Product.StockService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Technician extends Person {

	@OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private List<ProductStock> items = new ArrayList<ProductStock>();

	public ProductStock getStockOfType(ProductType type) {
		return StockService.getStockOfType(getItems(), type);
	}

	public void addStock(ProductStock stock) {
		StockService.addStock(getItems(), stock);
	}

	public void removeStock(ProductStock stock) throws StockNotAvailableException {
		StockService.removeStock(getItems(), stock);
	}

	public List<ProductStock> getItems() {
		return items;
	}
}
