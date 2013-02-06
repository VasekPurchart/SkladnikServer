package cz.cvut.jboss.storagecycle;

import cz.cvut.jboss.storagecycle.Person.Auditor;
import cz.cvut.jboss.storagecycle.Person.Person;
import cz.cvut.jboss.storagecycle.Person.Technician;
import cz.cvut.jboss.storagecycle.Product.ProductStock;
import cz.cvut.jboss.storagecycle.Product.ProductType;
import cz.cvut.jboss.storagecycle.Product.StockNotAvailableException;
import cz.cvut.jboss.storagecycle.Product.StockService;
import cz.cvut.jboss.storagecycle.VendingMachine.Audit;
import cz.cvut.jboss.storagecycle.VendingMachine.AuditLog;
import cz.cvut.jboss.storagecycle.VendingMachine.Recipe;
import cz.cvut.jboss.storagecycle.VendingMachine.ServiceVisit;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import cz.cvut.jboss.storagecycle.Warehouse.Warehouse;
import java.io.File;
import javax.inject.Inject;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class InfinispanTest {

	@Inject
	private CacheContainer cacheContainer;

	@Deployment
    public static Archive<?> getDeployment() {
		MavenDependencyResolver resolver = DependencyResolvers.use(
			MavenDependencyResolver.class
		).loadMetadataFromPom("pom.xml");
    	return ShrinkWrap.create(WebArchive.class, "test.war")
    			.addClasses(
					Person.class,
					Technician.class,
					ProductStock.class,
					ProductType.class,
					Audit.class,
					AuditLog.class,
					Auditor.class,
					Recipe.class,
					ServiceVisit.class,
					VendingMachine.class,
					Warehouse.class,
					CacheManagerProducer.class,
					StockService.class,
					StockNotAvailableException.class
				)
				.addAsLibraries(resolver.artifact("org.infinispan:infinispan-core").resolveAsFiles())
    			.addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
				.addAsResource(new File("infinispan.xml"), "infinispan.xml")
				.addAsResource(new File("src/main/resources/import.sql"), "import.sql")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml");
    }

	@Test
	public void testCache() {
		Cache<String, String> cache = cacheContainer.getCache();
		assertFalse(cache.containsKey("test"));

		cache.put("test", "foo");
		assertTrue(cache.containsKey("test"));
		assertEquals("foo", cache.get("test"));

	}

}
