package cz.cvut.jboss.storagecycle.VendingMachine;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class AuditLog implements Serializable {

	/**
	 * Default value included to remove warning. Remove or modify at will. *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Min(0)
	private int pushCounterState;

	@ManyToOne(cascade = {CascadeType.PERSIST})
	private Recipe recipe;

	public AuditLog() {
	}

	public AuditLog(int pushCounterState, Recipe recipe) {
		this.pushCounterState = pushCounterState;
		this.recipe = recipe;
	}

	public int getPushCounterState() {
		return pushCounterState;
	}

	public Recipe getRecipe() {
		return recipe;
	}
}