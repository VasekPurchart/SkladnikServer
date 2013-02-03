package cz.cvut.jboss.storagecycle.Api;

import static org.junit.Assert.assertEquals;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.cvut.jboss.storagecycle.Api.LocalFacade;

@RunWith(Arquillian.class)
public class LocalFacadeTest {

    @Inject
    private LocalFacade facade;

    @Deployment
    public static Archive<?> getDeployment() {
    	return ShrinkWrap.create(WebArchive.class, "test.war")
    			.addPackage(LocalFacade.class.getPackage())
    			.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
                //.addAsWebResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testSerialFactorial() {
        assertEquals(true, true);
    }
}
