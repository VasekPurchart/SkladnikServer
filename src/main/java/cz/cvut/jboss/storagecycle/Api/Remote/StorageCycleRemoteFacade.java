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
import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachineRepository;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;

/**
 *
 * @author vasek
 */
@Stateless
@WebService(serviceName="api", endpointInterface="cz.cvut.jboss.storagecycle.Api.Remote.StorageCycleWS")
public class StorageCycleRemoteFacade implements StorageCycleWS {

	@Inject
	private StorageCycleLocalFacade local;

	@Inject
	private ProductRepository productRepository;

	@Inject
	private PersonRepository personRepository;

	@Inject
	private VendingMachineRepository vendingMachineRepository;

	@Inject
	private EntityManager em;

	@Override
	public void importToWarehouse(String barcode, int count) {
		em.getTransaction().begin();

		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		local.importToWarehouse(productType, count);

		em.getTransaction().commit();
	}

	@Override
	public void transferToTechnician(String barcode, int count, long technicianId) throws StockNotAvailableException {
		em.getTransaction().begin();

		ProductType productType = productRepository.findProductTypeByBarcode(barcode);
		Technician technician = personRepository.findTechnicianById(technicianId);
		local.transferToTechnician(productType, count, technician);

		em.getTransaction().commit();
	}

	@Override
	public void visitVendingMachine(ServiceVisitDTO serviceVisitDTO) throws StockNotAvailableException {
		em.getTransaction().begin();

		Technician technician = personRepository.findTechnicianById(serviceVisitDTO.technicianId);
		VendingMachine vendingMachine = vendingMachineRepository.findVendingMachineById(serviceVisitDTO.vendingMachineId);

		Collection items = new ArrayList<ProductStock>();
		for (ProductStockDTO productStockDTO : serviceVisitDTO.items) {
			ProductType productType = productRepository.findProductTypeByBarcode(productStockDTO.barcode);
			ProductStock productStock = new ProductStock(productStockDTO.count, productType);
			items.add(productStock);
		}

		local.visitVendingMachine(technician, vendingMachine, serviceVisitDTO.timestamp, items);

		em.getTransaction().commit();
	}

	@Override
	public void setCashWithdrawnForVisit(long serviceVisitId, int cash) {
		em.getTransaction().begin();

		ServiceVisit serviceVisit = vendingMachineRepository.findServiceVisitById(serviceVisitId);
		local.setCashWithdrawnForVisit(serviceVisit, cash);

		em.getTransaction().commit();
	}

	@Override
	public void sendAudit(AuditDTO auditDTO) {
		em.getTransaction().begin();

		Auditor auditor = personRepository.findAuditorById(auditDTO.personId);
		VendingMachine vendingMachine = vendingMachineRepository.findVendingMachineById(auditDTO.vendingMachineId);

		Collection<AuditLog> logs = new ArrayList<AuditLog>();
		for (AuditLogDTO auditLogDTO : auditDTO.logs) {
			logs.add(new AuditLog(auditLogDTO.counterState, vendingMachineRepository.findRecipeById(auditLogDTO.recipeId)));
		}

		local.sendAudit(auditor, vendingMachine, logs, auditDTO.timestamp);

		em.getTransaction().commit();
	}

	@Override
	public AuditReportDTO exportAudits(long currentAuditId) {
		AuditReport auditReport = local.exportAudits(vendingMachineRepository.findAuditById(currentAuditId));

		return new AuditReportDTO(auditReport);
	}

	@Override
	public Collection<ProductTypeDTO> getProductTypes() {
		Collection<ProductTypeDTO> productTypeDTOs = new ArrayList<ProductTypeDTO>();
		for (ProductType productType : local.getProductTypes()) {
			productTypeDTOs.add(new ProductTypeDTO(productType));
		}

		return productTypeDTOs;
	}

	@Override
	public Collection<TechnicianDTO> getTechnicians() {
		Collection<TechnicianDTO> technicianDTOs = new ArrayList<TechnicianDTO>();
		for (Technician technician : local.getTechnicians()) {
			technicianDTOs.add(new TechnicianDTO(technician));
		}

		return technicianDTOs;
	}

	@Override
	public Collection<ProductStockDTO> getTechnicianItems(long technicianId) {
		Technician technician = personRepository.findTechnicianById(technicianId);
		Collection<ProductStockDTO> items = new ArrayList<ProductStockDTO>();
		for (ProductStock productStock : technician.getItems()) {
			items.add(new ProductStockDTO(productStock));
		}

		return items;
	}

	@Override
	public Collection<VendingMachineDTO> getVendingMachines() {
		Collection<VendingMachineDTO> machines = new ArrayList<VendingMachineDTO>();
		for (VendingMachine vendingMachine : local.getVendingMachines()) {
			machines.add(new VendingMachineDTO(vendingMachine));
		}

		return machines;
	}

	@Override
	public Collection<AuditDTO> getAudits(long vendingMachineId) {
		VendingMachine vendingMachine = vendingMachineRepository.findVendingMachineById(vendingMachineId);
		Collection<AuditDTO> audits = new ArrayList<AuditDTO>();
		for (Audit audit : local.getAudits(vendingMachine)) {
			audits.add(new AuditDTO(audit));
		}

		return audits;
	}
}
