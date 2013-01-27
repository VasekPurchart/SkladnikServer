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

@Entity
@XmlRootElement
public class Warehouse implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long id;
   
   @OneToMany(cascade={CascadeType.PERSIST})
   private List<ProductStock> items = new ArrayList<ProductStock>();
   
}