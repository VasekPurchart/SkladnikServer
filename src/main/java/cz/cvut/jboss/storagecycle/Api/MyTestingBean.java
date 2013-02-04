/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.jboss.storagecycle.Api;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author mist
 */
@Stateless
@LocalBean
//@SecurityDomain("JBossWS")
public class MyTestingBean {

    @RolesAllowed("good")
    public String helloGood(String name) {
        return "Hello good " + name;
    }
    
    @RolesAllowed("bad")
    public String helloBad(String name) {
        return "Hello bad " + name;
    }

    public String hello(String name) {
        return "Hello " + name;
    }
}
