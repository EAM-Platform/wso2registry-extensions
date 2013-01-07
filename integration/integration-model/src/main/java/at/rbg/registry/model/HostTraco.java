package at.rbg.registry.model;

import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.model.validation.SvzConstraint;


public class HostTraco extends ApplicationService {
	// for generic UI
	public final static String UI_NAME = "HostTraco";
	
	@SvzConstraint(KeyTypes.HOSTTRACO_DATAFORMAT_KEYTYPE)
	private String dataformat;
		
	public String getDataformat() {
		return dataformat;
	}

	public void setDataformat(String dataformat) {
		this.dataformat = dataformat;
	}
	@Override
	public String getRegistryType() {
		return ModelConstants.HOSTTRACO_ARTIFACT;
	}

	@Override
	public String toString() {
		return "HostTraco [getRegistryType()=" + getRegistryType()
				+ ", getTier()=" + getTier() + ", toString()="
				+ super.toString() + ", getName()=" + getName()
				+ ", getIdentifier()=" + getIdentifier() + ", getCreated()="
				+ getCreated() + ", getUpdated()=" + getUpdated()
				+ ", getVersion()=" + getVersion() + ", getDescription()="
				+ getDescription() + ", getPath()=" + getPath()
				+ ", getApplCode()=" + getApplCode() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + "]";
	}

	
	

	

}
