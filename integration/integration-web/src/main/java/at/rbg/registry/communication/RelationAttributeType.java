package at.rbg.registry.communication;

/**
 * relates to RelationAttribute, field "type"
 *
 */
public enum RelationAttributeType { 
	source("source"),
	destination("destination");
	
	private String type;
	
	RelationAttributeType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return type;
	}
	
	public static RelationAttributeType getByKey(String key)
	{
		for (int i = 0; i < RelationAttributeType.values().length; i++)
		{
			RelationAttributeType rat = RelationAttributeType.values()[i];
			if (rat.getName().equals(key))
			{
				return rat;
			}
		}
		throw new IllegalArgumentException("Dieser key existiert nicht");
	}

}
