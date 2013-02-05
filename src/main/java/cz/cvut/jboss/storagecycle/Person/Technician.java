package cz.cvut.jboss.storagecycle.Person;

import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
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
		for (ProductStock stock : items) {
			if (stock.getProductType().getName().contains(type.getName())) {
				return stock;
			}
		}

		return null;
	}

	public void addStock(ProductStock stock) {
		if (getStockOfType(stock.getProductType()) != null) {
			throw new IllegalArgumentException("Person already has stock of type " + stock.getProductType().getName());
		}

		items.add(stock);
	}

	public void removeStock(ProductStock stock) {
		items.remove(getStockOfType(stock.getProductType()));
	}

	public List<ProductStock> getItems() {
		return items;
	}

}
