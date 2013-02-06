package cz.cvut.jboss.storagecycle.VendingMachine;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author vasek
 */
public class VendingMachineRepository {

	@Inject
	private EntityManager em;

	public VendingMachine findVendingMachineById(long id) {
		return em.find(VendingMachine.class, id);
	}

	public ServiceVisit findServiceVisitById(long id) {
		return em.find(ServiceVisit.class, id);
	}

	public Recipe findRecipeById(long id) {
		return em.find(Recipe.class, id);
	}

	public Audit findAuditById(long id) {
		return em.find(Audit.class, id);
	}
}
