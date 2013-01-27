package cz.cvut.jboss.storagecycle.Person;

import java.io.Serializable;
import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Person implements Serializable, Principal {
	/** Default value included to remove warning. Remove or modify at will. **/
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

	@Override
	public String getName() {
		return name;
	}
   
}