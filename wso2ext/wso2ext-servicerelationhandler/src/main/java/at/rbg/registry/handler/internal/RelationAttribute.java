package at.rbg.registry.handler.internal;

public class RelationAttribute {

	private String relationName;
	private String attributeName;
	private String type; 
	
	public RelationAttribute(String relationName, String attributeName, String type) {
		this.relationName = relationName;
		this.attributeName = attributeName;
		this.type = type;
	}

	public String getRelationName() {
		return relationName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getType() {
		return type;
	}
	
	
}
