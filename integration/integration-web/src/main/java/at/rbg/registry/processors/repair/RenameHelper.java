package at.rbg.registry.processors.repair;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.governance.api.util.GovernanceUtils;

import at.rbg.registry.communication.RegistryFactory;

public class RenameHelper implements Processor {
	private static final Logger log = LoggerFactory.getLogger(RenameHelper.class);
	private RegistryFactory registryFactory;
	
	private String TABLE_MEDIATYPE = "application/vnd.wso2-table+xml";
	private String HOSTPROGRAM_MEDIATYPE = "application/vnd.wso2-hostprogram+xml";
	
	public void setRegistryFactory(RegistryFactory registryFactory) {
		this.registryFactory = registryFactory;
	}

	@Override
	public void process(Exchange ex) throws Exception {
		registryFactory.checkAlive();
		
		String[] paths = GovernanceUtils.findGovernanceArtifacts(TABLE_MEDIATYPE, registryFactory.getRemoteRegistryInstance());
		for (String path : paths) {
			String name = getNameFromPath(path);
			if (name!=null) {
				String newPath = getDirectoryFromPath(path);
				String newName = newPath + "/" + name;
				if (!registryFactory.getRemoteRegistryInstance().resourceExists(newName)) {
					registryFactory.getRemoteRegistryInstance().rename(path, newName);
				}
				else {
					log.info("resource exists: cant rename " + path + " to " + newName);
				}
			}
			// demo code only
//			else {
//				String newPath = path + "_UNKNOWN";
//				registryFactory.getWsRegistry().rename(path, newPath);
//			}
		}
		log.info("DONE renaming");

		
	}

	private String getNameFromPath(String path) {
		int pos = path.lastIndexOf("/");
		String nameVersion = path.substring(pos+1);
		pos = nameVersion.indexOf("_");
		if (pos > 0) {
			return nameVersion.substring(0, pos);
		}
		else 
			return null;
	} 
	
	private String getVersionFromPath(String path) {
		int pos = path.lastIndexOf("/");
		String nameVersion = path.substring(pos+1);
		pos = nameVersion.indexOf("_");
		if (pos > 0) {
			return nameVersion.substring(pos+1);
		}
		else 
			return null;
	} 
	
	private String getDirectoryFromPath(String path) {
		int pos = path.lastIndexOf("/");
		if (pos > 0) {
			return path.substring(0, pos);
		}
		else 
			return null;
	} 

}
