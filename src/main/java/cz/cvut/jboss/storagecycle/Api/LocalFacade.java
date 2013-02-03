package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.Person.Person;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class LocalFacade {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public Person findPerson(Long id) {
		return em.find(Person.class, id);
	}


}
