package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Api.Local.AuditReport;
import cz.cvut.jboss.storagecycle.Api.Local.StorageCycleLocalFacade;
import cz.cvut.jboss.storagecycle.Person.Auditor;
import cz.cvut.jboss.storagecycle.Person.PersonRepository;
import cz.cvut.jboss.storagecycle.Person.Technician;
import cz.cvut.jboss.storagecycle.Product.ProductRepository;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.Product.StockNotAvailableException;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachineRepository;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author vasek
 */
@Stateless
public class StorageCycleRemoteFacade {

	@Inject
	private StorageCycleLocalFacade local;

	@Inject
	private ProductRepository productRepository;

	@Inject
	private PersonRepository personRepository;

	@Inject
	private VendingMachineRepository vendingMachineRepository;

	public void importToWarehouse(String barcode, int count) {
		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		local.importToWarehouse(productType, count);
	}

	public void transferToTechnician(String barcode, int count, long technicianId) throws StockNotAvailableException {
		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		Technician technician = personRepository.findTechnicianById(technicianId);
		local.transferToTechnician(productType, count, technician);
	}

	public void visitVendingMachine(ServiceVisitDTO serviceVisitDTO) throws StockNotAvailableException {
		Technician technician = personRepository.findTechnicianById(serviceVisitDTO.technicianId);
		VendingMachine vendingMachine = vendingMachineRepository.findVendingMachineById(serviceVisitDTO.vendingMachineId);

		Collection items = new ArrayList<ProductStock>();
		for (ProductStockDTO productStockDTO : serviceVisitDTO.items) {
			ProductType productType = productRepository.findProductTypeByBarcode(productStockDTO.barcode);
			ProductStock productStock = new ProductStock(productStockDTO.count, productType);
			items.add(productStock);
		}

		local.visitVendingMachine(technician, vendingMachine, serviceVisitDTO.timestamp, items);
	}

	public void setCashWithdrawnForVisit(long serviceVisitId, int cash) {
		ServiceVisit serviceVisit = vendingMachineRepository.findServiceVisitById(serviceVisitId);
		local.setCashWithdrawnForVisit(serviceVisit, cash);
	}

	public void sendAudit(AuditDTO auditDTO) {
		Auditor auditor = personRepository.findAuditorById(auditDTO.personId);
		VendingMachine vendingMachine = vendingMachineRepository.findVendingMachineById(auditDTO.vendingMachineId);

		Collection<AuditLog> logs = new ArrayList<AuditLog>();
		for (AuditLogDTO auditLogDTO : auditDTO.logs) {
			logs.add(new AuditLog(auditLogDTO.counterState, vendingMachineRepository.findRecipeById(auditLogDTO.recipeId)));
		}

		local.sendAudit(auditor, vendingMachine, logs, auditDTO.timestamp);
	}

	public AuditReportDTO exportAudits(long currentAuditId) {
		AuditReport auditReport = local.exportAudits(vendingMachineRepository.findAuditById(currentAuditId));

		return new AuditReportDTO(auditReport);
	}

	public Collection<ProductTypeDTO> getProductTypes() {
		Collection<ProductTypeDTO> productTypeDTOs = new ArrayList<ProductTypeDTO>();
		for (ProductType productType : local.getProductTypes()) {
			productTypeDTOs.add(new ProductTypeDTO(productType));
		}

		return productTypeDTOs;
	}

	public Collection<TechnicianDTO> getTechnicians() {
		Collection<TechnicianDTO> technicianDTOs = new ArrayList<TechnicianDTO>();
		for (Technician technician : local.getTechnicians()) {
			technicianDTOs.add(new TechnicianDTO(technician));
		}

		return technicianDTOs;
	}
}
