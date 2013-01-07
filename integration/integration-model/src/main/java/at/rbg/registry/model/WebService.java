package at.rbg.registry.model;

import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.model.validation.SvzConstraint;

public class WebService extends ApplicationService  {
	// for generic UI
	public final static String UI_NAME = "WebService";

	@SvzConstraint(KeyTypes.INTERFACE_KEYTYPE)
	private String interfaceType;
	
	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	
	
	@Override
	public String getRegistryType() {
		return ModelConstants.WEBSERVICE_ARTIFACT;
	}

	@Override
	public String toString() {
		return "WebService [interfaceType=" + interfaceType
				+ ", getInterfaceType()=" + getInterfaceType()
				+ ", getRegistryType()=" + getRegistryType() + ", getTier()="
				+ getTier() + ", toString()=" + super.toString()
				+ ", getName()=" + getName() + ", getIdentifier()="
				+ getIdentifier() + ", getCreated()=" + getCreated()
				+ ", getUpdated()=" + getUpdated() + ", getVersion()="
				+ getVersion() + ", getDescription()=" + getDescription()
				+ ", getPath()=" + getPath() + ", getApplCode()="
				+ getApplCode() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + "]";
	}

	
	

}
