package cz.cvut.jboss.storagecycle.VendingMachine;

import cz.cvut.jboss.storagecycle.Product.ProductType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Recipe implements Serializable {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<ProductType> productTypes = new ArrayList<ProductType>();

	@NotNull
	@Min(0)
	private int price;

	@NotNull
	@Min(1)
	private int position;

	public Recipe() {
	}

	public Recipe(List<ProductType> productTypes, int price, int position) {
		this.productTypes = productTypes;
		this.price = price;
		this.position = position;
	}

	public Long getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}
}