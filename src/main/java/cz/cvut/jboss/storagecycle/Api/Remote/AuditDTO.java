package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author vasek
 */
public class AuditDTO {

	public long id;

	public long personId;

	public long vendingMachineId;

	public Date timestamp;

	public Collection<AuditLogDTO> logs;

	public AuditDTO() {
	}

	public AuditDTO(Audit audit) {
		id = audit.getId();
		personId = audit.getAuditor().getId();
		vendingMachineId = audit.getVendingMachine().getId();
		timestamp = audit.getDateTime();
		logs = new ArrayList<AuditLogDTO>();
		for (AuditLog auditLog : audit.getRecipeLogs()) {
			logs.add(new AuditLogDTO(auditLog));
		}
	}
}
