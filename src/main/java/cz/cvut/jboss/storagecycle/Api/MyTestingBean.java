/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.jboss.storagecycle.Api;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author mist
 */
@Stateless
@LocalBean
@SecurityDomain("helloworld-webservice-login")
public class MyTestingBean {

    @RolesAllowed("say-hello")
    public String helloGood(String name) {
        return "Hello good " + name;
    }
    
    public String helloBad(String name) {
        return "Hello bad " + name;
    }

    public String hello(String name) {
        return "Hello " + name;
    }
}
