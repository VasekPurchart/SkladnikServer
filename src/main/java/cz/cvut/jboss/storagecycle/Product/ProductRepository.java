package cz.cvut.jboss.storagecycle.Product;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author vasek
 */
public class ProductRepository {

	@Inject
	private EntityManager em;

	public ProductType findProductTypeByBarcode(String barcode) {
		return (ProductType) em.createQuery(
				"SELECT p FROM ProductType p WHERE p.barcode = :barcode")
				.setParameter("barcode", barcode)
				.getSingleResult();
	}

}
