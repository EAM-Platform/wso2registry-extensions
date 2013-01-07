package at.rbg.registry.communication;

/**
 *  this class holds infos about relations of generic
 *  artifacts.
 *
 */
public class RelationAttribute { 

	private String relationName; 
	private String attributeName;
	private RelationAttributeType type;
	
	public RelationAttribute(String relationName, String attributeName, RelationAttributeType type) {
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

	public RelationAttributeType getType() {
		return type;
	}
	
	
}
