package at.rbg.registry.model.relation;

import at.rbg.registry.model.RBGGovernanceArtifact;

public class UseRelation extends BaseRelation {
	// for the generic UI
	private final static String UI_RELATION_NAME = "uses_entry";
	
	public UseRelation(RBGGovernanceArtifact src, RBGGovernanceArtifact dest) {
		super.name = "uses";
		super.inverseName = "isUsed";
		super.bidirectional = true;
		super.setUIRelationName(UI_RELATION_NAME);
		setSource(src);
		setDestination(dest);
	}

	@Override
	public String toString() {
		return "UseRelation [getSource()=" + getSource()
				+ ", getDestination()=" + getDestination() + ", getName()="
				+ getName() + "]";
	}	
}
