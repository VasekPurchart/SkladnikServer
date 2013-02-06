package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Api.Local.StorageCycleLocalFacade;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author vasek
 */
@Stateless
public class StorageCycleRemoteFacade {

	@Inject
	private StorageCycleLocalFacade local;

}
