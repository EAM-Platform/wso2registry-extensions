package at.rbg.registry.routes;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

/**
 * workaround for camel property placeholder component config!
 * Problem: 
 * Spring uses UrlResource, Camel Property uses String
 * This explodes, when giving a file URL vs file String
 * file:///tmp/demo.properties vs. file:/tmp/demo.properties
 * 
 * @author WRSNOV
 *
 */
public class RouteConfig {
	private static final Logger log = LoggerFactory.getLogger(RouteConfig.class);

	private UrlResource zoneConfiguration;

	public void setZoneConfiguration(UrlResource zoneConfiguration) {
		this.zoneConfiguration = zoneConfiguration;
	}
	
	public String getFilename() {
		String fileNotAsUri;
		try {
			fileNotAsUri = this.zoneConfiguration.getURL().getFile();
			return "file:" + fileNotAsUri ;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
