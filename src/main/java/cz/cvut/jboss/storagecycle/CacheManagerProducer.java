package cz.cvut.jboss.storagecycle;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;

@ApplicationScoped
public class CacheManagerProducer {

	private CacheContainer container;

	@Produces
	@RequestScoped
    public CacheContainer getCacheContainer() throws IOException {
        if (container == null) {
			container = new DefaultCacheManager("infinispan.xml");
		}
		return container;
    }

}
