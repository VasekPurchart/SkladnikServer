package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.Person.Technician;

/**
 *
 * @author vasek
 */
public class TechnicianDTO {

	public long id;

	public String phoneId;

	public String name;

	public TechnicianDTO() {
	}

	public TechnicianDTO(Technician technician) {
		id = technician.getId();
		phoneId = technician.getPhoneId();
		name = technician.getName();
	}
}
