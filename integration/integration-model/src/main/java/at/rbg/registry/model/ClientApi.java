package at.rbg.registry.model;


import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.model.validation.SvzConstraint;

public class ClientApi extends ApplicationService {
	// for generic UI
	public final static String UI_NAME = "ClientAPI";

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
		return ModelConstants.CLIENTAPI_ARTIFACT;
	}

}
