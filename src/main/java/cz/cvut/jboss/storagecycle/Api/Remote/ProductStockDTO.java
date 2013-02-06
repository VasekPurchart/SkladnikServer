package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Product.ProductStock;

/**
 *
 * @author vasek
 */
public class ProductStockDTO extends ProductTypeDTO {

	public int count;

	public ProductStockDTO() {
	}

	public ProductStockDTO(ProductStock productStock) {
		super(productStock.getProductType());
		count = productStock.getCount();
	}
}
