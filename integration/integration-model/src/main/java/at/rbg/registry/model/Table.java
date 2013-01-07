package at.rbg.registry.model;


public class Table extends InformationObject {
    public static final String UI_NAME = "Tabelle";

	@Override
	public String toString() {
		return "Table [getName()=" + getName()
				+ ", getVersion()=" + getVersion() + ", getDescription()="
				+ getDescription() + "]";
	}

	@Override
	public String getRegistryType() {
		return ModelConstants.TABLE_ARTIFACT;
	}
}
