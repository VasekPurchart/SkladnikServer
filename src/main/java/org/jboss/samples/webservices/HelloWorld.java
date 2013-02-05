package org.jboss.samples.webservices;

import cz.cvut.jboss.storagecycle.Api.MyTestingBean;
import java.util.Properties;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.jboss.ejb3.annotation.SecurityDomain;

@WebService()
@SecurityDomain("helloworld-webservice-login")
public class HelloWorld {

    @Inject
    MyTestingBean helloBean;

    @WebMethod()
    @RolesAllowed("say-hello")
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
