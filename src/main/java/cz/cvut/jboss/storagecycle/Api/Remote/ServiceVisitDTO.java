package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author vasek
 */
public class ServiceVisitDTO {

	public long id;

	public long technicianId;

	public long vendingMachineId;

	public Date timestamp;

	public int withdrawnCash;

	public Collection<ProductStockDTO> items;

	public ServiceVisitDTO() {
	}

	public ServiceVisitDTO(ServiceVisit serviceVisit) {
		id = serviceVisit.getId();
		technicianId = serviceVisit.getTechnician().getId();
		vendingMachineId = serviceVisit.getVendingMachine().getId();
		timestamp = serviceVisit.getDateTime();
		withdrawnCash = serviceVisit.getWithdrawnCash();
		items = new ArrayList<ProductStockDTO>();
		for (ProductStock productStock : serviceVisit.getItems()) {
			items.add(new ProductStockDTO(productStock));
		}
	}
}
