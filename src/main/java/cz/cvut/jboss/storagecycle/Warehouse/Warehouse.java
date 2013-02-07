package cz.cvut.jboss.storagecycle.Warehouse;

import cz.cvut.jboss.storagecycle.Product.StockNotAvailableException;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.Product.StockService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Warehouse implements Serializable {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

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