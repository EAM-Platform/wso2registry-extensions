package at.rbg.registry.model.validation;

/**
 * maps integration keytypes to EAMP keytypes.
 * if you use the keyCodeDao-Property implementation
 * you need to adapt things in svzlist.properties when doing
 * changes here!
 */
public class KeyTypes {
	public static final String DOMAIN_KEYTYPE="EAMP001";
	public static final String APPLICATIONSERVICE_KEYTYPE="EAMP002";
	public static final String APPLICATIONSERVICE_KEYTYPE_REVERSE="200PMAE";
	public static final String SERVICEROLE_KEYTYPE="EAMP003";
	public static final String TIER_KEYTYPE="EAMP004";
	public static final String INTERFACE_KEYTYPE="interfaceType";
	public static final String HOSTPROGRAM_ACCESS_KEYTYPE="hostprogramAccessType";
	public static final String ORGANISATION_KEYTYPE="organisation";
	public static final String HOSTPROGRAM_TECHNOLOGY_KEYTYPE="hostprogtech";
	public static final String HOSTTRACO_DATAFORMAT_KEYTYPE="hosttracodataformat";
	public static final String LIFECYCLE_KEYTYPE="lifecycle";

}
