package at.rbg.registry.model;

import java.util.Calendar;


public abstract class RBGGovernanceArtifact {
	private String identifier;
	private String name;
	private String applCode;
	private String version;  
	private String description;
	private Calendar created;
	private Calendar updated;
	private LifecycleState lifecycleState;
	private String lifecycleName;
	private String path;
	
	private final String DEFAULT_LIFECYCLENAME = "ServiceLifeCycle";
	
	abstract public String getRegistryType();

	
	public String getName() {
		return name;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public void setName(String name) { 
		this.name = replaceUniqueName(name);
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = replaceUmlauts(description);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getApplCode() {
		return applCode;
	}
	public void setApplCode(String applCode) {
	       this.applCode = replaceUniqueName(applCode);
	}

	public LifecycleState getLifecycleState() {
		return lifecycleState;
	}

	public void setLifecycleState(LifecycleState lifecycleState) {
		this.lifecycleState = lifecycleState;
		// if we set a state, we also set a default lifecyclename
		if (this.lifecycleName == null) {
			this.lifecycleName = DEFAULT_LIFECYCLENAME;
		}
	}

	public String getLifecycleName() {
		return lifecycleName;
	}

	public void setLifecycleName(String lifecycleName) {
		this.lifecycleName = lifecycleName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		RBGGovernanceArtifact other = (RBGGovernanceArtifact) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	
	/*
	 * workaround
	 */
	private String replaceUmlauts(String toConvert) {
		if (toConvert!=null) {
			toConvert = toConvert.replaceAll("ä", "ae");
			toConvert = toConvert.replaceAll("ö", "oe");
			toConvert = toConvert.replaceAll("ü", "ue");
			toConvert = toConvert.replaceAll("ß", "ss");
			toConvert = toConvert.replaceAll("Ä", "Ae");
			toConvert = toConvert.replaceAll("Ö", "Oe");
			toConvert = toConvert.replaceAll("Ü", "Ue");
		}
		return toConvert;
	}
	
	private String replaceUniqueName(String toConvert) {
		if (toConvert!=null) {
			toConvert = replaceUmlauts(toConvert);
			toConvert = toConvert.replaceAll("\\.", "_");
			toConvert = toConvert.replaceAll(" ", "_");
			toConvert = toConvert.replaceAll("\\+", "plus");
			toConvert = toConvert.replaceAll("\"", "_");
			toConvert = toConvert.replaceAll("\\:", "_");
			toConvert = toConvert.replaceAll("/", "_");
	        toConvert = toConvert.replaceAll("\\*", "_");
		}
		return toConvert;
	}
}
