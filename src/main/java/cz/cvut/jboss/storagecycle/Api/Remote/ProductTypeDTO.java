package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Product.ProductType;

/**
 *
 * @author vasek
 */
public class ProductTypeDTO {

	public String barcode;

	public String name;

	public ProductTypeDTO() {
	}

	public ProductTypeDTO(ProductType productType) {
		barcode = productType.getBarcode();
		name = productType.getName();
	}
}
