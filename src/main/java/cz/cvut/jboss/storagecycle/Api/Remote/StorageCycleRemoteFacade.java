package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Api.Local.StockNotAvailableException;
import cz.cvut.jboss.storagecycle.Api.Local.StorageCycleLocalFacade;
import cz.cvut.jboss.storagecycle.Person.PersonRepository;
import cz.cvut.jboss.storagecycle.Person.Technician;
import cz.cvut.jboss.storagecycle.Product.ProductRepository;
import cz.cvut.jboss.storagecycle.Product.ProductType;
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

	@Inject
	private ProductRepository productRepository;

	@Inject
	private PersonRepository personRepository;

	public void importToWarehouse(String barcode, int count) {
		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		local.importToWarehouse(productType, count);
	}

	public void transferToTechnician(String barcode, int count, long technicianId) throws StockNotAvailableException {
		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		Technician technician = personRepository.findTechnicianById(technicianId);
		local.transferToTechnician(productType, count, technician);
	}

}
