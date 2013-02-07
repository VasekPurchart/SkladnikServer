package cz.cvut.jboss.storagecycle.Product;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ProductStock implements Serializable {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = {CascadeType.PERSIST})
	private ProductType productType;

	@NotNull
	@Min(1)
	private int count;

	public ProductStock() {}

	public ProductStock(int count, ProductType productType) {
		this.count = count;
		this.productType = productType;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType type) {
		this.productType = type;
	}

	public int getCount() {
		return count;
	}

	public void incrementCount(int by) {
		count += by;
	}

	public void decrementCount(int by) {
		count -= by;
	}
}