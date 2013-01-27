package cz.cvut.jboss.storagecycle.VendingMachine;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import cz.cvut.jboss.storagecycle.Product.ProductStock;

@Entity
@XmlRootElement
public class Audit implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long id;

   @NotNull
   private Date timestamp;
   
   @ManyToOne(cascade={CascadeType.PERSIST})
   private VendingMachine vendingMachine;
   
   // TODO person

   @OneToMany(cascade={CascadeType.PERSIST})
   private List<AuditLog> recipeLogs = new ArrayList<AuditLog>();
   
   @OneToMany(cascade={CascadeType.PERSIST})
   private List<ProductStock> items = new ArrayList<ProductStock>();
   
}