/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.jboss.storagecycle.Product;

/**
 *
 * @author ondrej
 */
public class StockNotAvailableException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>StockNotAvailableException</code> without detail message.
	 */
	public StockNotAvailableException() {
	}

	/**
	 * Constructs an instance of
	 * <code>StockNotAvailableException</code> with the specified detail
	 * message.
	 *
	 * @param msg the detail message.
	 */
	public StockNotAvailableException(String msg) {
		super(msg);
	}
}
