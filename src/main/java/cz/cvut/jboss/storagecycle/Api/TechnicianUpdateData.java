package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import java.util.Collection;

public class TechnicianUpdateData {

	private Collection<ProductStock> items;

	private Collection<VendingMachine> vendingMachines;

	public TechnicianUpdateData(Collection<ProductStock> items, Collection<VendingMachine> vendingMachines) {
		this.items = items;

		this.vendingMachines = vendingMachines;
	}

	public Collection<ProductStock> getItems() {
		return items;
	}

	public Collection<VendingMachine> getVendingMachines() {
		return vendingMachines;
	}

}
