package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Product.StockNotAvailableException;
import java.util.Collection;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@WebService(name="storageCycleService")
public interface StorageCycleWS {

	public void importToWarehouse(String barcode, int count) throws StorageCycleRemoteFacadeException;

	public void transferToTechnician(String barcode, int count, long technicianId) throws StorageCycleRemoteFacadeException;

	public void visitVendingMachine(ServiceVisitDTO serviceVisitDTO) throws StorageCycleRemoteFacadeException;

	public void setCashWithdrawnForVisit(long serviceVisitId, int cash) throws StorageCycleRemoteFacadeException;

	public void sendAudit(AuditDTO auditDTO) throws StorageCycleRemoteFacadeException;

	public AuditReportDTO exportAudits(long currentAuditId);

	public Collection<ProductTypeDTO> getProductTypes();

	public Collection<TechnicianDTO> getTechnicians();

	public Collection<ProductStockDTO> getTechnicianItems(long technicianId);

	public Collection<VendingMachineDTO> getVendingMachines();

	public Collection<AuditDTO> getAudits(long vendingMachineId);
}
