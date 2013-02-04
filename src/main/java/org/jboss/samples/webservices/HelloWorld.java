package org.jboss.samples.webservices;

import cz.cvut.jboss.storagecycle.Api.MyTestingBean;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.apache.cxf.interceptor.InInterceptors;

@WebService()
@InInterceptors(interceptors = {
      "org.jboss.wsf.stack.cxf.security.authentication.SubjectCreatingPolicyInterceptor"}
)
public class HelloWorld {

    @Inject
    MyTestingBean helloBean;
    
	@WebMethod()
	public String sayHelloBad(@WebParam String name) {
	    return helloBean.helloBad(name);
	}
        
	@WebMethod()
	public String sayHelloGood(@WebParam String name) {
	    return helloBean.helloGood(name);
	}
        
	@WebMethod()
	public String sayHello(@WebParam String name) {
	    return helloBean.hello(name);
	}
}
