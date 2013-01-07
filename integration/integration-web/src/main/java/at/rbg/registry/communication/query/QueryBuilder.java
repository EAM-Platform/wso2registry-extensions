package at.rbg.registry.communication.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Key;
import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.persistance.dao.KeyCodeDao;

/**
 * we support only a few query aspects directly in the backend.
 * the rest is late filtering!
 * 
 * Supported is
 * 1) consumer, provider (isCalled, calls)
 * 2) domain query via property app_domain of application and join via property_value
 * 3) applicationService name via reg_resource.reg_name LIKE 
 *
 */
public class QueryBuilder {
	private static final Logger log = LoggerFactory
			.getLogger(QueryBuilder.class);
	private static KeyCodeDao keyCodeDao;

	public void setKeyCodeDao(KeyCodeDao keyCodeDao) {
		QueryBuilder.keyCodeDao = keyCodeDao;
	}

	// -- get all appservice elems
	private static final String SELECT_CLAUSE = "select RR.REG_PATH_ID, RR.REG_NAME FROM ";
	private static final String FROM_CLAUSE_DOMAIN = // -- domain related
	" REG_PROPERTY PP, REG_RESOURCE_PROPERTY RP, ";
	private static final String FROM_CLAUSE_APPLICATION = // -- appliation			
	" REG_RESOURCE_PROPERTY RP2, REG_PROPERTY PP2, ";
	private static final String FROM_CLAUSE_BASE_DOMAIN =
			" REG_PROPERTY PP4, REG_RESOURCE_PROPERTY RP4, ";
	private static final String FROM_CLAUSE_BASE = 
			" REG_RESOURCE RR, REG_ASSOCIATION RA, REG_PATH RPA ";
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String WHERE_CLAUSE_DOMAIN = // -- domain related
			" PP.REG_ID = RP.REG_PROPERTY_ID AND "
			+ " PP.REG_NAME='app_domain' AND PP.REG_VALUE IN (%%DOMAINS%%) AND "
			+ " RP.REG_VERSION=RP2.REG_VERSION AND ";
	private static final String WHERE_CLAUSE_APPLICATION_BASE = // --
			" PP2.REG_ID=RP2.REG_PROPERTY_ID AND " + 
			" PP2.REG_NAME='app_code' AND " +
			" PP2.REG_VALUE=PP4.REG_VALUE AND ";
	private static final String WHERE_CLAUSE_BASE_DOMAIN = 
			" PP4.REG_NAME='app_code' AND " +
			" PP4.REG_ID=RP4.REG_PROPERTY_ID AND " +
			" RP4.REG_VERSION=RR.REG_VERSION AND ";
	private static final String WHERE_CLAUSE_BASE = " RR.REG_MEDIA_TYPE IN ('application/vnd.wso2-hostprogram+xml',"
			+ " 'application/vnd.wso2-service+xml', 'application/vnd.wso2-clientcomponent+xml',"
			+ " 'application/vnd.wso2-webcomponent+xml', 'application/vnd.wso2-hosttraco+xml',"
			+ " 'application/vnd.wso2-clientapi+xml','application/vnd.wso2-table+xml') AND "
			+ " RPA.REG_PATH_ID=RR.REG_PATH_ID AND "
			+ " RA.REG_SOURCEPATH = CONCAT(RPA.REG_PATH_VALUE,'/', RR.REG_NAME) AND "
			+ " RA.REG_ASSOCIATION_TYPE IN (%%ASSOCTYPE%%)"; // -- calls/isCalled [
														// consumer/provider]
	private static final String GROUP_BY_CLAUSE = " GROUP BY RR.REG_PATH_ID, RR.REG_NAME ";

	private static final String WHERE_CLAUSE_APPSERVICE_NAME = 
			" UCASE(RR.REG_NAME) LIKE %%APP_NAME%% AND "; 
	
	private static final String APPLICATION_QUERY_BY_CODE = "SELECT RR.REG_PATH_ID, RR.REG_NAME "
			+ " FROM REG_RESOURCE RR, REG_PROPERTY PP3, REG_RESOURCE_PROPERTY RP3 "
			+ " WHERE RR.REG_MEDIA_TYPE='application/vnd.wso2-application+xml' AND "
			+ "	RP3.REG_VERSION=RR.REG_VERSION AND "
			+ "	PP3.REG_ID=RP3.REG_PROPERTY_ID AND "
			+ "	PP3.REG_NAME='app_code' AND "
			+ "	PP3.REG_VALUE IN (%%APPLICATIONS%%)";

	public static String getQueryString(QueryRequest qr, Collection<String> assocTypes) {
		boolean isDomainQuery = false;
		boolean isAppServiceNameQuery = false;
		
		// resolve domains, construct query part
		String domQueryWhere = "";
		if (qr.getDomains() != null && 1==2) {
			List<String> regDoms = new ArrayList<String>();
			for (Key key : qr.getDomains()) {
				// we resolve to value and use that to query stuff
				if (key.getCode() != null) {
					String value = keyCodeDao.resolveKeyCode(
							KeyTypes.DOMAIN_KEYTYPE, key.getCode());
					if (value != null) {
						regDoms.add(value);
					} else {
						log.warn("Domain resolution for: " + key.getCode()
								+ " failed");
					}
				} else if (key.getName() != null) {
					// sanity check: security related. hope that helps...
					String name = key.getName().replace("--", "");
					regDoms.add(name);
				}
			}
			if (regDoms.size() > 0) {
				domQueryWhere = getInClauseFromList(regDoms);
				isDomainQuery = true;
			}
		}
		String appServiceName = "";
		if (qr.getAppService()!=null) {
			ModelComposite mc = qr.getAppService();
			ApplicationService as = mc.getApplicationService();
			if (as.getName()!=null) {
				appServiceName = as.getName();
				appServiceName = appServiceName.replace("--", ""); // LOL
				// disabled bec. slow atm
				//isAppServiceNameQuery = true;
			}
		}
		
		StringBuilder from = new StringBuilder();
		StringBuilder where = new StringBuilder();
		from.append(SELECT_CLAUSE);
		where.append(WHERE_CLAUSE);

		if (isAppServiceNameQuery) {
			String wbase = WHERE_CLAUSE_APPSERVICE_NAME.replace("%%APP_NAME%%", "'" + appServiceName + "%'");	
			where.append(wbase.toUpperCase());
		}
	
		if (isDomainQuery) {
			from.append(FROM_CLAUSE_DOMAIN);
			from.append(FROM_CLAUSE_APPLICATION);
			String doms = WHERE_CLAUSE_DOMAIN.replace("%%DOMAINS%%",
					domQueryWhere.toString());
			where.append(doms);
			where.append(WHERE_CLAUSE_APPLICATION_BASE);
			// base
			where.append(WHERE_CLAUSE_BASE_DOMAIN);
			from.append(FROM_CLAUSE_BASE_DOMAIN);
		}

		from.append(FROM_CLAUSE_BASE);
        String assoc = getInClauseFromList(assocTypes);

		String wbase = WHERE_CLAUSE_BASE.replace("%%ASSOCTYPE%%",assoc);
		where.append(wbase);

		StringBuilder sb = new StringBuilder();
		sb.append(from);
		sb.append(where);
		sb.append(GROUP_BY_CLAUSE);

		return sb.toString();
}

	public static String getApplicationsQueryForCodes(Collection<String> appCodes) {
		String query = APPLICATION_QUERY_BY_CODE;
		query = query
				.replace("%%APPLICATIONS%%", getInClauseFromList(appCodes));
		return query;
	}
	
	private static String getInClauseFromList(Collection<String> datas) {
		StringBuilder sb = new StringBuilder();
		boolean firstCheck = false;
		for (String data : datas) {
			if (firstCheck) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(data);
			sb.append("'");
			firstCheck = true;
		}
		return sb.toString();
	}
}
