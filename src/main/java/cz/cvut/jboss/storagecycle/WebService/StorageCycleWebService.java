package cz.cvut.jboss.storagecycle.WebService;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class StorageCycleWebService {

	@WebMethod
	public String hello(String name)
	{
		return "Hello " + name;
	}
}
