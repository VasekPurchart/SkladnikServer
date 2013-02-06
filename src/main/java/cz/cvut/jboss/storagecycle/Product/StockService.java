package cz.cvut.jboss.storagecycle.Product;

import java.util.Collection;

/**
 *
 * @author vasek
 */
public class StockService {

	public static ProductStock getStockOfType(Collection<ProductStock> items, ProductType type) {
		for (ProductStock stock : items) {
			if (stock.getProductType().getBarcode().equals(type.getBarcode())) {
				return stock;
			}
		}

		return null;
	}

	public static void addStock(Collection<ProductStock> items, ProductStock stock) {
		ProductStock productStock = getStockOfType(items, stock.getProductType());
		if (productStock != null) {
			productStock.incrementCount(stock.getCount());
		} else {
			items.add(new ProductStock(stock.getCount(), stock.getProductType()));
		}
	}

	public static void removeStock(Collection<ProductStock> items, ProductStock stock) throws StockNotAvailableException {
		ProductStock productStock = getStockOfType(items, stock.getProductType());
		if (productStock == null) {
			throw new StockNotAvailableException("Stock with barcode " + stock.getProductType().getBarcode() + " is not available.");
		}
		if (productStock.getCount() < stock.getCount()) {
			throw new StockNotAvailableException(
					"Thera are only " + productStock.getCount() + " pieces with barcode " + stock.getProductType().getBarcode() + " available, "
					+ stock.getCount() + " requested."
			);
		}
		productStock.decrementCount(stock.getCount());
		if (productStock.getCount() <= 0) {
			items.remove(productStock);
		}
	}

}
