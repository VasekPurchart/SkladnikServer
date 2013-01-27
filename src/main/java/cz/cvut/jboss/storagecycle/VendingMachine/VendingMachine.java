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

import cz.cvut.jboss.storagecycle.Product.ProductStock;

@Entity
@XmlRootElement
public class VendingMachine implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long id;

   @NotNull
   @Min(1)
   private int number;

   @NotNull
   private String adress;
   
   @OneToMany(cascade={CascadeType.PERSIST})
   private List<Recipe> recipes = new ArrayList<Recipe>();
   
   @OneToMany(cascade={CascadeType.PERSIST})
   private List<ProductStock> items = new ArrayList<ProductStock>();
   
   @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="vendingMachine")
   private List<Audit> audits = new ArrayList<Audit>();
   
}