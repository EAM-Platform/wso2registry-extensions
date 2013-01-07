package at.rbg.registry.model;

import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.model.validation.SvzConstraint;

public class HostProgram extends ApplicationService {
	// for generic UI
	public final static String UI_NAME = "HostProgram";
	
	@SvzConstraint(KeyTypes.HOSTPROGRAM_ACCESS_KEYTYPE)
	private String accesstype;		

	@SvzConstraint(KeyTypes.INTERFACE_KEYTYPE)
	private String interfaceType;

	@SvzConstraint(KeyTypes.HOSTPROGRAM_TECHNOLOGY_KEYTYPE)
	private String technology;

	private String release;
	
	public String getAccesstype() {
		return accesstype;
	}

	public void setAccesstype(String accesstype) {
		this.accesstype = accesstype;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	@Override
	public String toString() {
		return "HostProgram [accesstype=" + accesstype + ", getName()=" + getName()
				+ ", getVersion()=" + getVersion() + ", getDescription()="
				+ getDescription() + "]";
	}

	@Override
	public String getRegistryType() {
		return ModelConstants.HOSTPROGRAM_ARTIFACT;
	}
	
}
