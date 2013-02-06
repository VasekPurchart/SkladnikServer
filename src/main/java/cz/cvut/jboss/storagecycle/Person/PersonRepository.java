package cz.cvut.jboss.storagecycle.Person;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author vasek
 */
public class PersonRepository {

	@Inject
	private EntityManager em;

	public Technician findTechnicianById(long id) {
		return em.find(Technician.class, id);
	}

	public Auditor findAuditorById(long id) {
		return em.find(Auditor.class, id);
	}
}
