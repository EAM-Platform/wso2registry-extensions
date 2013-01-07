package at.rbg.registry.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;


/**
 * @Value annotation didnt work... 
 *  
 */
public class ModelConstants {
	public static final QName GENERIC_ARTIFACT_NAMESPACE = new QName("http://www.wso2.org/governance/metadata");

	public static final String UNKNOWN_NAME = "UNKNOWN";
	public static final String UNKNOWN_VERSION = "UNKNOWN";
	public static final String GENERIC_NAME = "GENERIC"; 
	
	public static String HOSTPROGRAM_ARTIFACT;
	public static String MODULE_ARTIFACT;
	public static String APPLICATION_ARTIFACT; 
	public static String TABLE_ARTIFACT;
	public static String HOSTTRACO_ARTIFACT;
	public static String WEBSERVICE_ARTIFACT;
	public static String WEBCOMPONENT_ARTIFACT;
	public static String CLIENTCOMPONENT_ARTIFACT;
	public static String CLIENTAPI_ARTIFACT;
	public static String PERSON_ARTIFACT;
	public static String INFRASTRUCTURE_ARTIFACT;
	
	private static Map<String, String> artifactMap = new HashMap<String,String>(); 
	
	public void setArtifactMap(Map<String,String> artifactMap) {
		ModelConstants.artifactMap = artifactMap;
		
		// for model Handling
		APPLICATION_ARTIFACT = artifactMap.get("applicationArtifactName");
		MODULE_ARTIFACT = artifactMap.get("moduleArtifactName");
		HOSTPROGRAM_ARTIFACT = artifactMap.get("hostProgramArtifactName");
		TABLE_ARTIFACT = artifactMap.get("tableArtifactName");
		HOSTTRACO_ARTIFACT = artifactMap.get("hostTracoArtifactName");
		WEBSERVICE_ARTIFACT = artifactMap.get("webServiceArtifactName");
		WEBCOMPONENT_ARTIFACT = artifactMap.get("webComponentArtifactName");
		CLIENTCOMPONENT_ARTIFACT = artifactMap.get("clientComponentArtifactName");
		CLIENTAPI_ARTIFACT = artifactMap.get("clientApiArtifactName");
		PERSON_ARTIFACT = artifactMap.get("personArtifactName");
		INFRASTRUCTURE_ARTIFACT = artifactMap.get("infrastructureArtifactName");
	}
	
	public static List<String> getManagedArtifacts() {
			return new ArrayList<String>(artifactMap.values());
	}
}
