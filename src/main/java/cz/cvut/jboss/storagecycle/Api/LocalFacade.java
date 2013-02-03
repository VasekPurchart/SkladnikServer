package cz.cvut.jboss.storagecycle.Api;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class LocalFacade {
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;


}
