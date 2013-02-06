package cz.cvut.jboss.storagecycle.Product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author vasek
 */
@Entity
@XmlRootElement
public class ProductType implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue
   private Long id;

   @NotNull
   @Size(min = 1, max = 25)
   @Pattern(regexp = "[A-Za-z0-9 ]*")
   private String name;

   @NotNull
   @Column(unique=true)
   private String barcode;

   public String getName() {
	   return name;
   }

   public void setName(String name) {
	   this.name = name;
   }

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}