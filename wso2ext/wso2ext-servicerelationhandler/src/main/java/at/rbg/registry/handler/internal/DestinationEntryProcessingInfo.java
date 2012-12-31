package at.rbg.registry.handler.internal;

import org.wso2.carbon.governance.api.generic.GenericArtifactManager;
import org.wso2.carbon.governance.api.generic.dataobjects.GenericArtifact;

/**
 * a container to pass all info needed for processing an entry in
 * DestinationEntryProcessor
 */
public class DestinationEntryProcessingInfo {
	private GenericArtifactManager gam;
	private GenericArtifact fga; 
	private String attrName;
	private String newPath;
	private String oldPath;

	public DestinationEntryProcessingInfo(GenericArtifactManager gam,
			GenericArtifact fga, String attrName, String newPath, String oldPath) {
		this.gam = gam;
		this.fga = fga;
		this.attrName = attrName;
		this.newPath = newPath;
		this.oldPath = oldPath;
	}

	public GenericArtifactManager getGenericArtifactManager() {
		return gam;
	}

	public GenericArtifact getGenericArtifact() {
		return fga;
	}


	public String getAttrName() {
		return attrName;
	}

	public String getNewPath() {
		return newPath;
	}

	public String getOldPath() {
		return oldPath;
	}
}
