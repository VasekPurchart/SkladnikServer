package cz.cvut.jboss.storagecycle.Warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import javax.persistence.FetchType;

@Entity
@XmlRootElement
public class Warehouse implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long id;

   @OneToMany(cascade={CascadeType.PERSIST}, fetch=FetchType.EAGER)
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
		   throw new IllegalArgumentException("Warehouse already contains stock of type " + stock.getProductType().getName());
	   }

	   items.add(stock);
   }

   public List<ProductStock> getItems() {
	   return items;
   }

}