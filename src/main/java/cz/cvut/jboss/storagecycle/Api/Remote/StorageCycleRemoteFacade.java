package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Api.Local.StorageCycleLocalFacade;
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

	private ProductRepository productRepository;

	public void importToWarehouse(String barcode, int count) {
		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		local.importToWarehouse(productType, count);
	}

}
