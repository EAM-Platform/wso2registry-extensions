package at.rbg.registry.communication.query;

import java.util.List;

import org.wso2.carbon.registry.api.Association;

import at.rbg.registry.model.RBGGovernanceArtifact;

public class ArtifactDTO {
	private RBGGovernanceArtifact artifact;
	private List<Association> assocs;
	
	public RBGGovernanceArtifact getArtifact() {
		return artifact;
	}
	public void setArtifact(RBGGovernanceArtifact artifact) {
		this.artifact = artifact;
	}
	public List<Association> getAssocs() {
		return assocs;
	}
	public void setAssocs(List<Association> assocs) {
		this.assocs = assocs;
	}
	
	
}
