package at.rbg.registry.model.relation;

import at.rbg.registry.model.RBGGovernanceArtifact;

public class CallRelation extends BaseRelation {
	// for the generic UI
	private final static String UI_RELATION_NAME = "calls_entry";
	
	public CallRelation(RBGGovernanceArtifact src, RBGGovernanceArtifact dest) {
		super.name = "calls";
		super.inverseName = "isCalled";
		super.bidirectional = true;
		super.setUIRelationName(UI_RELATION_NAME);
		setSource(src);
		setDestination(dest);
	}

	@Override
	public String toString() {
		return "CallRelation [getSource()=" + getSource()
				+ ", getDestination()=" + getDestination() + ", getName()="
				+ getName() + "]";
	}
}
