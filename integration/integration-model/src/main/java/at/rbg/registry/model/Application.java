package at.rbg.registry.model;

import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.model.validation.SvzConstraint;

public class Application extends RBGGovernanceArtifact {
	// for generic UI
	public final static String UI_NAME = "Application";
	public final static String contains_attribute = "contains_entry";

    @SvzConstraint(KeyTypes.DOMAIN_KEYTYPE) 
    private String domain;
    
    private String longname;
 	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}
	
	@Override
	public String toString() {
		return "Application [getName()=" + getName() + ", getVersion()="
				+ getVersion() + ", getDescription()=" + getDescription() + "]";
	}

	@Override
	public
	String getRegistryType() {
		return ModelConstants.APPLICATION_ARTIFACT;
	}

}
