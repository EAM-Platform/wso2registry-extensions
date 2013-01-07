package at.rbg.registry.model.relation;

import at.rbg.registry.model.RBGGovernanceArtifact;

public abstract class BaseRelation {
	private RBGGovernanceArtifact source;
	private RBGGovernanceArtifact destination; 
	protected String name;
	protected String inverseName;
	protected boolean bidirectional = false;
	private String UI_RELATION_NAME = "BaseRelation";
	private String UI_RELATION_INVERSE_NAME = "BaseRelation";
		
	protected void setUIRelationName(String uiRelationName) {
		this.UI_RELATION_NAME = uiRelationName;
	}
	
	protected void setUIRelationInverseName(String uiRelationInverseName) {
		this.UI_RELATION_INVERSE_NAME = uiRelationInverseName;
	}
	
	public String getUIRelationName() {
		return UI_RELATION_NAME;
	}
	
	public String getUIRelationInverseName() {
		return UI_RELATION_INVERSE_NAME;
	}

	public RBGGovernanceArtifact getSource() {
		return source;
	}
	public void setSource(RBGGovernanceArtifact source) {
		this.source = source;
	}
	public RBGGovernanceArtifact getDestination() {
		return destination;
	}
	public void setDestination(RBGGovernanceArtifact destination) {
		this.destination = destination;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public String getName() {
		return name;
	}

	public String getInverseName() {
		return inverseName;
	}

	
	
}

