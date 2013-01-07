package at.rbg.registry.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.registry.api.Association;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import at.rbg.registry.cache.RegistryCache;
import at.rbg.registry.communication.query.ArtifactDTO;

public class RegistryInteraction {
	private static final Logger log = LoggerFactory
			.getLogger(RegistryInteraction.class);
	private RegistryFactory registryFactory;
	private RegistryCache registryCache;

	public void setRegistryCache(RegistryCache registryCache) {
		this.registryCache = registryCache;
	}
	

	public void setRegistryFactory(RegistryFactory registryFactory) {
		this.registryFactory = registryFactory;
	}


	public List<String> getAssociationDestinationPaths(String path,
			String assocType) {
		List<String> destinationPaths = new ArrayList<String>();
		try {
			Association[] assocs = registryFactory.getRemoteRegistryInstance()
					.getAssociations(path, assocType);
			for (Association assoc : assocs) {
				destinationPaths.add(assoc.getDestinationPath());
			}
		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
		return destinationPaths;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> findArtifactPaths(String query) {
		List<String> retval = new ArrayList<String>();
		Map parameters = new HashMap();
		parameters.put("query", query);

		try {
			Resource result = registryFactory.getRemoteRegistryInstance().executeQuery(
					null, parameters);
			String[] paths = (String[]) result.getContent();
			if (paths != null) {
				for (String path : paths) {
					retval.add(path);
				}
			}
		} catch (RegistryException e) {
		}
		return retval;
	}

	public List<ArtifactDTO> getGenericArtifactsforPaths(
			Collection<String> paths) {
		List<ArtifactDTO> retval = new ArrayList<ArtifactDTO>();
		if (paths != null) {
			for (String path : paths) {
				ArtifactDTO ra = registryCache.getGenericArtifactForPath(path);
				if (ra!=null) {
					retval.add(ra);
				}
			}
		}
		return retval;
	}

	
	/**
	 * 
	 * @param paths
	 * @param assocType
	 * @param parentOnly   give only those assocs, where we a re on the src side
	 * @return
	 */
	public List<Association> getAssociationsforPaths(Collection<String> paths, Collection<String> assocTypes, boolean parentOnly) {
		List<Association> retval = new ArrayList<Association>();
		if (paths != null) {
			for (String path : paths) {			
				for (String assocType : assocTypes) {
					retval.addAll(registryCache.getAssociationsforPath(path, assocType, parentOnly));
				}
			}
		}
		return retval;
	}

}
