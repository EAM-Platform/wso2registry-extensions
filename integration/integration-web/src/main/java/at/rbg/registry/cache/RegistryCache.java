package at.rbg.registry.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceArtifactConfiguration;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.api.Association;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import at.rbg.registry.communication.ModelBuilder;
import at.rbg.registry.communication.RegistryFactory;
import at.rbg.registry.communication.RelationAttribute;
import at.rbg.registry.communication.RelationAttributeType;
import at.rbg.registry.communication.query.ArtifactDTO;
import at.rbg.registry.model.LifecycleState;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.persistance.dao.KeyCodeDao;

public class RegistryCache {
	private static final Logger log = LoggerFactory
			.getLogger(RegistryCache.class);
	private static Map<String, ArrayList<RelationAttribute>> relAttrs = new HashMap<String, ArrayList<RelationAttribute>>();
	private static Pattern p = Pattern.compile("\\@\\{(\\w*?):value\\}"); // extract
	private RegistryFactory registryFactory;
	private static KeyCodeDao keyCodeDao;

	public void setKeyCodeDao(KeyCodeDao keyCodeDao) {
		RegistryCache.keyCodeDao = keyCodeDao;
	}
	public void setRegistryFactory(RegistryFactory registryFactory) {
		this.registryFactory = registryFactory;
	}
	
	@Cacheable(value="assocs")
	public List<Association> getAssociationsforPath(String path, String assocType, boolean parentOnly) {
		List<Association> retval = new ArrayList<Association>();
		Association[] aas;
		try {
			aas = registryFactory.getRemoteRegistryInstance().getAssociations(path, assocType);
			for (Association aa : aas) {
				// these are archived versions. skip these..
				if (skipAssociation(aa)) {
					continue;
				}
				if (parentOnly) {
					if (aa.getSourcePath().equals(path)) {
						retval.add(aa);
					}
					continue;
				}
				retval.add(aa);
			}		
		} catch (RegistryException e) {
			log.error(e.getMessage());		
		}
		return retval;
	}
	
	@Cacheable(value="artifactAllAssocs")
	public List<Association> getAllAssociationsforPath(String path, boolean consumer, boolean provider) {
		List<Association> retval = new ArrayList<Association>();
		Association[] aas;
		try {
			aas = registryFactory.getRemoteRegistryInstance().getAllAssociations(path);
			for (Association aa : aas) {
				// these are archived versions. skip these..
				if (skipAssociation(aa)) {
					continue;
				}
				if (consumer) {
					if (aa.getSourcePath().equals(path)) {
						retval.add(aa);
					}
				}
				if (provider) {
					if (aa.getDestinationPath().equals(path)) {
						retval.add(aa);
					}
				}
			}		
		} catch (RegistryException e) {
			log.error(e.getMessage());		
		}
		return retval;
	}

	
	@Cacheable(value="genericArtifact")
	public  ArtifactDTO getGenericArtifactForPath(String path) {
		ArtifactDTO retval = null;
		try {
			if (registryFactory.getRemoteRegistryInstance().resourceExists(path)) {
				Resource r = registryFactory.getRemoteRegistryInstance().get(path);		
				String key = registryFactory.getKeyForMediaType(r.getMediaType());
				// process only known mediatypes!
				if (key!=null) {
					GovernanceArtifact ga = GovernanceUtils.retrieveGovernanceArtifactByPath(
							registryFactory.getRemoteRegistryInstance(), r.getPath());
					retval = new ArtifactDTO();
					List<RelationAttribute> ras = getRelationAttributeList(r.getMediaType());
					RBGGovernanceArtifact ra = ModelBuilder.createArtifact(ga,
							key, path, r.getCreatedTime(), r.getLastModified());
		
					// for performance!
					String lifecycleName = r.getProperty("registry.LC.name");
					String lifecycleState = r.getProperty("registry.lifecycle." + lifecycleName + ".state");
					ra.setLifecycleName(lifecycleName);
					if (lifecycleState!=null) {
						String code = keyCodeDao.resolveKeyValue("lifecycle", lifecycleState);
						ra.setLifecycleState(LifecycleState.valueOf(code));
					}
					
					List<Association> sas = ModelBuilder.createAssociations(ga, ras, ra.getPath());
					retval.setArtifact(ra);
					retval.setAssocs(sas);
				}
			}
			else {
				log.error(path + " as link reference does not exist!");
			}
		} catch (RegistryException e) {
		}
		return retval;
	}

	private  List<RelationAttribute> getRelationAttributeList(String mediaType) {
		List<RelationAttribute> retval = relAttrs.get(mediaType);
		if (retval == null) {
			// check for associations, which do not originate from this entity
			GovernanceArtifactConfiguration c = registryFactory
					.getConfigForArtifactType(registryFactory
							.getKeyForMediaType(mediaType));
			ArrayList<RelationAttribute> ra = new ArrayList<RelationAttribute>();
			Association[] as = c.getRelationshipDefinitions();
			for (Association a : as) {
				String matchString = null;
				String type = null;
				if (a.getDestinationPath() != null) {
					matchString = a.getDestinationPath();
					type = "destination";
				}
				if (a.getSourcePath() != null) {
					matchString = a.getSourcePath();
					type = "source";
				}
				if (matchString != null) {
					Matcher m = p.matcher(matchString);
					m.find();
					MatchResult s = m.toMatchResult();
					if (s.groupCount() > 0) {
						String result = s.group(1);
						ra.add(new RelationAttribute(a.getAssociationType(),
								result, RelationAttributeType.getByKey(type)));
					}
				}
			}
			relAttrs.put(mediaType, ra);
			retval = ra;
		}
		return retval;
	}
	private static boolean skipAssociation(Association aa) {
		boolean retval = false;
		// these are archived versions. skip these..
		if ((aa.getSourcePath() != null && aa.getSourcePath().contains(
				";version:"))
				|| (aa.getDestinationPath() != null && aa.getDestinationPath()
						.contains(";version:"))) {
			retval = true;
		}
		return retval;
	}
}
