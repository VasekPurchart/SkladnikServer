package cz.cvut.jboss.storagecycle.VendingMachine;

import cz.cvut.jboss.storagecycle.Person.Auditor;
import cz.cvut.jboss.storagecycle.Person.Person;
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
import java.util.Collection;
import javax.persistence.Temporal;

@Entity
@XmlRootElement
public class Audit implements Serializable {

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

	@ManyToOne(cascade = {CascadeType.PERSIST})
	private VendingMachine vendingMachine;

	@ManyToOne
	private Auditor auditor;

	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<AuditLog> recipeLogs = new ArrayList<AuditLog>();

	public static Audit create(Auditor auditor, VendingMachine machine, Date dateTime) {
		Audit audit = new Audit();
		audit.auditor = auditor;
		audit.vendingMachine = machine;
		audit.dateTime = dateTime;
		return audit;
	}

	public Long getId() {
		return id;
	}

	public void addRecipeLog(AuditLog log) {
		recipeLogs.add(log);
	}

	public Date getDateTime() {
		return dateTime;
	}

	public VendingMachine getVendingMachine() {
		return vendingMachine;
	}

	public Auditor getAuditor() {
		return auditor;
	}

	public List<AuditLog> getRecipeLogs() {
		return recipeLogs;
	}

}