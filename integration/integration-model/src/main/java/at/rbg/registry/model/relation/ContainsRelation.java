package at.rbg.registry.model.relation;

import at.rbg.registry.model.RBGGovernanceArtifact;

public class ContainsRelation extends BaseRelation {
	// for the generic UI
	private final static String UI_RELATION_NAME = "contains_entry";
	private final static String UI_RELATION_INVERSE_NAME = "isContained_entry";

	public final static String RELATION_NAME = "contains";
	public final static String RELATION_INVERSE_NAME = "isContained";
		
	public ContainsRelation(RBGGovernanceArtifact src, RBGGovernanceArtifact dest) {
		super.name = RELATION_NAME;
		super.inverseName = RELATION_INVERSE_NAME;
		super.bidirectional = true;
		super.setUIRelationName(UI_RELATION_NAME);
		super.setUIRelationInverseName(UI_RELATION_INVERSE_NAME);
		setSource(src);
		setDestination(dest);
	}

	@Override
	public String toString() {
		return "ContainsRelation [getSource()=" + getSource()
				+ ", getDestination()=" + getDestination() + ", getName()="
				+ getName() + "]";
	}
	
}
