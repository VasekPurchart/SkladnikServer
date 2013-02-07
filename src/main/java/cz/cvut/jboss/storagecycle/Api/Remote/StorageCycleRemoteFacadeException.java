/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.jboss.storagecycle.Api.Remote;

/**
 *
 * @author ondrej
 */
public class StorageCycleRemoteFacadeException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>RemoteFacadeException</code> without detail message.
	 */
	public StorageCycleRemoteFacadeException() {
	}

	/**
	 * Constructs an instance of
	 * <code>RemoteFacadeException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public StorageCycleRemoteFacadeException(String msg) {
		super(msg);
	}

	public StorageCycleRemoteFacadeException(String msg, Throwable t) {
		super(msg, t);
	}
}
