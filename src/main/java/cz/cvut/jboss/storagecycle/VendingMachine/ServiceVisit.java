package cz.cvut.jboss.storagecycle.VendingMachine;

import cz.cvut.jboss.storagecycle.Person.Technician;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ServiceVisit implements Serializable {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateTime;

	@NotNull
	@Min(0)
	private int withdrawnCash;

	@NotNull
	@ManyToOne
	private Technician technician;

	@NotNull
	@ManyToOne
	private VendingMachine vendingMachine;

	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<ProductStock> items = new ArrayList<ProductStock>();

	public Date getDateTime() {
		return dateTime;
	}

	public int getWithdrawnCash() {
		return withdrawnCash;
	}

	public void setWithdrawnCash(int cash) {
		this.withdrawnCash = cash;
	}

	public void visit(VendingMachine vendingMachine, Technician technician, Date dateTime) {
		this.vendingMachine = vendingMachine;
		this.technician = technician;
		this.dateTime = dateTime;
	}

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