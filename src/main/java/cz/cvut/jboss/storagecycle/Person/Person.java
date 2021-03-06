package cz.cvut.jboss.storagecycle.Person;

import java.io.Serializable;
import java.security.Principal;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Person implements Serializable, Principal {

	public final static String ROLE_TECHNICIAN = "technician";

	public final static String ROLE_AUDITOR = "auditor";

	public final static String ROLE_DIRECTOR = "director";

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*")
	private String name;

	@NotNull
	@Pattern(regexp = "\\d{15,17}")
	private String phoneId;

	private String role;

	private String passwordAuth;

	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getPhoneId() {
		return phoneId;
	}
}