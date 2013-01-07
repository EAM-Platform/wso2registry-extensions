package at.rbg.registry.model;


public class WebComponent extends ApplicationService {
	// for generic UI
	public final static String UI_NAME = "WebComponent";
	

	@Override
	public String getRegistryType() {
		return ModelConstants.WEBCOMPONENT_ARTIFACT;
	}

}
