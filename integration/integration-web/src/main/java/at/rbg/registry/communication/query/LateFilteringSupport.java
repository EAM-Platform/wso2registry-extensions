package at.rbg.registry.communication.query;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.model.Application;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Key;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.persistance.dao.KeyCodeDao;

public class LateFilteringSupport {
	private static final Logger log = LoggerFactory
			.getLogger(LateFilteringSupport.class);

	private static KeyCodeDao keyCodeDao;

	public void setKeyCodeDao(KeyCodeDao keyCodeDao) {
		LateFilteringSupport.keyCodeDao = keyCodeDao;
	}
	/**
	 * late filtering support for
	 * Application [name. version]
	 * Module [name, version]
	 * @param qr
	 * @param mcons
	 * @return
	 */
	public static boolean accept(QueryRequest qr, ModelComposite mcons) {
		boolean retval = true;
		if (qr.getAppl()!=null) {
			ModelComposite mc = qr.getAppl();
			boolean retval1 = compareEntities(mc.getApplication(),mcons.getApplication());
			retval = retval && retval1;
		}
		if (qr.getModule()!=null) {
			ModelComposite mc = qr.getModule();
			boolean retval2 = compareEntities( mc.getModule(),mcons.getModule());
			retval = retval && retval2;
		}
		if (qr.getAppService()!=null) {
			ModelComposite mc = qr.getAppService();
			boolean retval1 = compareEntities(mc.getApplicationService(),mcons.getApplicationService());
			retval = retval && retval1;
		}
		if (qr.getTier()!=null) {
			boolean retval3 = compareTier( qr.getTier(),mcons.getModule());
			retval = retval && retval3;			
		}		
		return retval;
	}
	
	private static boolean compareTier(Key tier, Module module) {
		if (tier!=null && module != null) {
			if (tier.getCode()!= null && !tier.getCode().equals(module.getTier())) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean compareEntities(RBGGovernanceArtifact filter, RBGGovernanceArtifact fromBackend) {
		if (filter!=null && fromBackend!=null) {
			return compareValues(filter.getName(),fromBackend.getName()) && 
					compareValues(filter.getVersion(),fromBackend.getVersion());				
		}
		// if returned entity is null and we WANTED to filter => no match!
		if (filter!=null && fromBackend==null) {
			return false;
		}
		return true;
	}
	
	private static boolean compareValues(String filter, String fromBackend) {
		String fb = (fromBackend!=null)?fromBackend.toUpperCase():"";
		if (filter!=null && !fb.startsWith(filter.toUpperCase())) {
			return false;
		}
		return true;
	}
	
	/**
	 * filter for version
	 * @param consumers
	 * @param mc
	 * @return
	 */
	public static List<ArtifactDTO> applyApplicationServiceFilter(List<ArtifactDTO> consumers, QueryRequest qr) {	
		
		List<ArtifactDTO> retval = new ArrayList<ArtifactDTO>();
		for (ArtifactDTO ad : consumers) {
			RBGGovernanceArtifact a = ad.getArtifact();
			// check the domain if defined, then the rest...
			if (compareDomain(qr,a) && compareVersion(qr, a)) {
				retval.add(ad);						
			}
		}
		return retval;
	}
	
	private static boolean compareVersion(QueryRequest qr, RBGGovernanceArtifact a) {
		boolean retval = false;
		ModelComposite mc = qr.getAppService();
		if (mc!=null) {
			String appServiceVersion = "";

			ApplicationService as = mc.getApplicationService();
			if ((as != null && as.getVersion()!=null)) {
				appServiceVersion = as.getVersion();
			}
			if (appServiceVersion.length()>0) {
				if (a.getVersion().startsWith(appServiceVersion)) {
					retval = true;
				}
			}
			// empty query, we do not care..
			else {
				retval = true;
			}
		}
		else {
			retval = true;
		}
		return retval;
	}
	
	// FIX ME
	private static boolean compareDomain(QueryRequest qr, RBGGovernanceArtifact a) {
		boolean retval = false;
		
		if ((a instanceof Application) && qr.getDomains()!=null) {
			Application app = (Application) a;
			for (Key key : qr.getDomains()) {
				// we resolve to value and use that to query stuff
				if (key.getCode() != null) {
					String value = keyCodeDao.resolveKeyCode(
							KeyTypes.DOMAIN_KEYTYPE, key.getCode());
					if (value != null) {
						retval = retval && compareValues(value, app.getDomain());
					} else {
						log.warn("Domain resolution for: " + key.getCode()
								+ " failed");
					}
				} else if (key.getName() != null) {
					// sanity check: security related. hope that helps...
					String value = key.getName().replace("--", "");
					retval = retval && compareValues(value,app.getDomain());
				}
			
			}
		}
		// if no domain is queried we do not care!
		else {
			retval = true;
		}
		return retval;
	}
}
