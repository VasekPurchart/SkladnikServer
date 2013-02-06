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

}
