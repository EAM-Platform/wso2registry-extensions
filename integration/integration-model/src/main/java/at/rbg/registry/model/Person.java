package at.rbg.registry.model;

import at.rbg.registry.model.validation.SvzConstraint;
import at.rbg.registry.model.validation.KeyTypes;
/**
 * Person: a stakeholder for a governed artifact 
 *
 */
public class Person extends RBGGovernanceArtifact {
	private String userId;
	private String name;
	
	@SvzConstraint(KeyTypes.ORGANISATION_KEYTYPE)
	private String organization;
	 
	public Person (String userId, String name) {
		this.name = name;
		this.userId = userId;
	}
	
	public Person () {
		
	}

	public Person (String name) {
		this.name = name;
	}
	
	public String getUserId() {
		return userId;
	}


	public String getName() {
		return name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
	
	@Override
	public String getRegistryType() {
		return ModelConstants.PERSON_ARTIFACT;
	}

	
}
