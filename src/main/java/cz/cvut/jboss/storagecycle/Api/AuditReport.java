package cz.cvut.jboss.storagecycle.Api;

import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import java.util.Collection;

public class AuditReport {

	private Collection<ServiceVisit> serviceVisits;

	private Audit lastAudit;

	private Audit currentAudit;

	public AuditReport(Collection<ServiceVisit> serviceVisits, Audit lastAudit, Audit currentAudit) {
		this.serviceVisits = serviceVisits;
		this.lastAudit = lastAudit;
		this.currentAudit = currentAudit;
	}

	public Collection<ServiceVisit> getServiceVisits() {
		return serviceVisits;
	}

	public Audit getLastAudit() {
		return lastAudit;
	}

	public Audit getCurrentAudit() {
		return currentAudit;
	}

}
