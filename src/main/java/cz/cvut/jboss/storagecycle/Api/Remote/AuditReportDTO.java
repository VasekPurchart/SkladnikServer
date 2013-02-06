package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Api.Local.AuditReport;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author vasek
 */
public class AuditReportDTO {

	public AuditDTO lastAudit;

	public AuditDTO currentAudit;

	public Collection<ServiceVisitDTO> serviceVisits;

	public AuditReportDTO() {
	}

	public AuditReportDTO(AuditReport auditReport) {
		lastAudit = new AuditDTO(auditReport.getLastAudit());
		currentAudit = new AuditDTO(auditReport.getCurrentAudit());
		serviceVisits = new ArrayList<ServiceVisitDTO>();
		for (ServiceVisit serviceVisit : auditReport.getServiceVisits()) {
			serviceVisits.add(new ServiceVisitDTO(serviceVisit));
		}
	}
}
