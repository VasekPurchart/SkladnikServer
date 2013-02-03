package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.Person.Person;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class LocalFacade {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public Warehouse getWarehouse() {
		return em.find(Warehouse.class, 1L);
	}


}
