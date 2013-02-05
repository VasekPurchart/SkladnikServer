package cz.cvut.jboss.storagecycle.VendingMachine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import cz.cvut.jboss.storagecycle.Product.ProductType;

@Entity
@XmlRootElement
public class Recipe implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long id;

   @OneToMany(cascade={CascadeType.PERSIST})
   private List<ProductType> productTypes = new ArrayList<ProductType>();

   @NotNull
   @Min(0)
   private int price;

   public Recipe()
   {
       
   }
   
   public Recipe(List<ProductType> productTypes, int price) {
	   this.productTypes = productTypes;
	   this.price = price;
   }

}