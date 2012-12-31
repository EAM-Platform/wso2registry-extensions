package at.rbg.registry.handler.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

public class QueryHelper {
	private static final Logger log = LoggerFactory
			.getLogger(QueryHelper.class);
	private static String affectedByRename = "select RR.REG_PATH_ID, RR.REG_NAME FROM "
			+ " REG_PROPERTY PP4,REG_RESOURCE_PROPERTY RP4, "
			+ " REG_RESOURCE RR "
			+ " WHERE  "
			+ " PP4.REG_NAME='app_code' AND "
			+ " PP4.REG_VALUE = ? AND "
			+ " PP4.REG_ID=RP4.REG_PROPERTY_ID AND "
			+ " RP4.REG_VERSION=RR.REG_VERSION AND "
			+ " RR.REG_MEDIA_TYPE IN ('application/vnd.wso2-hostprogram+xml', "
			+ " 'application/vnd.wso2-service+xml', 'application/vnd.wso2-clientcomponent+xml', "
			+ " 'application/vnd.wso2-webcomponent+xml', 'application/vnd.wso2-hosttraco+xml', 'application/vnd.wso2-clientapi+xml', "
			+ " 'application/vnd.wso2-table+xml','application/vnd.wso2-appmodule+xml') "
			+ " GROUP BY RR.REG_PATH_ID, RR.REG_NAME ";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] getAffectedResources(String oldName, Registry registry) {
		String[] paths = null;
		try {
			Map parameters = new HashMap();
			parameters.put("1", oldName); // mediaType
			parameters.put("query", affectedByRename);
			Resource result;
			result = registry.executeQuery(null, parameters);
			paths = (String[]) result.getContent();
		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
		return paths;
	}

}
