package at.rbg.registry.cache;

import javax.management.MBeanServer;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.management.ManagementService;
import java.lang.management.ManagementFactory;

public class JmxRegister {

	public void setCacheManager(EhCacheCacheManager ehm) {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ManagementService.registerMBeans(ehm.getCacheManager(), mBeanServer, false,
				false, false, true);
	}

}
