package cz.cvut.jboss.storagecycle.Person;

import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Person implements Serializable, Principal {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*")
	private String name;

	@NotNull
	@Pattern(regexp = "\\d{15,17}")
	private String phoneId;

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

	public List<ProductStock> getItems() {
		return items;
	}

	@Override
	public String getName() {
		return name;
	}
}