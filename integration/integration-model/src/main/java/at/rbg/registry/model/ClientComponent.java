package at.rbg.registry.model;


public class ClientComponent extends ApplicationService {
	// for generic UI
	public final static String UI_NAME = "ClientComponent";
		
	@Override
	public String getRegistryType() {
		return ModelConstants.CLIENTCOMPONENT_ARTIFACT;
	}

}
