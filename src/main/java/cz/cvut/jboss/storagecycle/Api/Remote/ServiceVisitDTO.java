package cz.cvut.jboss.storagecycle.Api.Remote;

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

}
