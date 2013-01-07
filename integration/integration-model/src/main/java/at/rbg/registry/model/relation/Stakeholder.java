package at.rbg.registry.model.relation;

import at.rbg.registry.model.Person;
import at.rbg.registry.model.RBGGovernanceArtifact;

public class Stakeholder  {
	private final static String UI_RELATION_NAME = "contact_entry"; // in the XML <stakeholder><entry>
	private Person source;
	private RBGGovernanceArtifact destination;
	protected String name;
	protected String inverseName = "responsible";
	protected boolean bidirectional = true;
	

	public Stakeholder(Person src, RBGGovernanceArtifact dest, String name) {
		this.source = src;
		this.destination = dest;
		this.name =name;
	}
	
	public String getUIRelationName() {
		return Stakeholder.UI_RELATION_NAME;
	}
	
	public Person getSource() {
		return source;
	}

	public void setSource(Person source) {
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

	@Override
	public String toString() {
		return "Stakeholder [source=" + source + ", destination=" + destination
				+ ", name=" + name + ", inverseName=" + inverseName
				+ ", bidirectional=" + bidirectional + "]";
	}
	
	
	
}
