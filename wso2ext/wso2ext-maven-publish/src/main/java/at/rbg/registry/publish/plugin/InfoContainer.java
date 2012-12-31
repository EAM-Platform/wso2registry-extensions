package at.rbg.registry.publish.plugin;

/**
 * this class contains the caclulated paths to  
 *
 * 1) metadata entry : e.g. web service 
 * 2) artifact itself : e.g. wsdl, xsd
 */
public class InfoContainer {
	private String metadataPath; 
	private String wsdlPath;
	
	public String getMetadataPath() {
		return metadataPath;
	}
	public void setMetadataPath(String metadataPath) {
		this.metadataPath = metadataPath;
	}
	
	public String getWsdlPath() {
		return wsdlPath;
	}
	public void setWsdlPath(String wsdlPath) {
		this.wsdlPath = wsdlPath;
	}

}
