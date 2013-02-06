package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;

/**
 *
 * @author vasek
 */
public class AuditLogDTO {

	public long recipeId;

	public int counterState;

	public AuditLogDTO() {
	}

	AuditLogDTO(AuditLog auditLog) {
		recipeId = auditLog.getRecipe().getId();
		counterState = auditLog.getPushCounterState();
	}
}
