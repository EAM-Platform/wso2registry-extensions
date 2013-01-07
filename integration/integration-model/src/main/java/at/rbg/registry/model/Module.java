package at.rbg.registry.model;

import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.model.validation.SvzConstraint;


public class Module extends RBGGovernanceArtifact {
	// for generic UI
	public final static String UI_NAME = "Modul";
	
	@SvzConstraint(KeyTypes.TIER_KEYTYPE)
	private String tier;	

	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	
	@Override
	public String toString() {
		return "Module [getName()=" + getName() + ", getVersion()="
				+ getVersion() + ", getDescription()=" + getDescription() + "]";
	}

	@Override
	public String getRegistryType() {
		return ModelConstants.MODULE_ARTIFACT;
	}

}
