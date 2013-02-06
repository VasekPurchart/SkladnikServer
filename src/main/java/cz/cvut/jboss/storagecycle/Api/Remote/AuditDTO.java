package cz.cvut.jboss.storagecycle.Api.Remote;

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
}
