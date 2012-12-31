package at.rbg.registry.handler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.generic.GenericArtifactManager;
import org.wso2.carbon.governance.api.generic.dataobjects.GenericArtifact;
import org.wso2.carbon.governance.api.generic.dataobjects.GenericArtifactImpl;
import org.wso2.carbon.governance.api.util.GovernanceArtifactConfiguration;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Association;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.CollectionImpl;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.config.RegistryContext;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.jdbc.EmbeddedRegistryService;
import org.wso2.carbon.registry.core.jdbc.Repository;
import org.wso2.carbon.registry.core.jdbc.handlers.Handler;
import org.wso2.carbon.registry.core.jdbc.handlers.RequestContext;
import org.wso2.carbon.registry.core.session.CurrentSession;
import org.wso2.carbon.registry.core.session.UserRegistry;
import org.wso2.carbon.registry.extensions.utils.CommonUtil;

import at.rbg.registry.handler.internal.DeleteDestinationEntryStrategy;
import at.rbg.registry.handler.internal.DestinationEntryProcessingInfo;
import at.rbg.registry.handler.internal.DestinationEntryProcessor;
import at.rbg.registry.handler.internal.QueryHelper;
import at.rbg.registry.handler.internal.RelationAttribute;
import at.rbg.registry.handler.internal.UpdateDestinationEntryStrategy;

/**
 * this handler takes care of copying incoming relations, a) when creating new
 * versions (PUT) b) and doing consistency work when deleting (DELETE) c) for
 * WebServices it links to the previous version and copies associations from
 * there.
 * 
 * The handler also creates the next-/previousVersion association.
 * 
 * ATTENTION: in case, you have to use this handler for different mediaTypes,
 * you need to use the specialized classes. The service registry does not allow
 * the config of the same handler class via User Interface for different
 * mediaTypes (matchers).
 */
public class ServiceRelationHandler extends Handler {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceRelationHandler.class);

	private final String APPLICATION_MEDIA_TYPE = "application/vnd.wso2-application+xml";
	
	private final String ARTIFACT_VERSION = "details_version";
	private final String ARTIFACT_NEXTVERSION = "details_nextversion";
	private final String ARTIFACT_NEXTNAMESPACE = "overview_nextnamespace"; // for
	private final String ARTIFACT_NAMESPACE = "overview_namespace"; // for web services

	private final String ASSOC_NEXTVERSION = "nextVersion";
	private final String ASSOC_PREVIOUSVERSION = "previousVersion";

	// for application indexing in DB we create a property
	private static final String ATTRIBUTE_SECTION = "details";
	private static final String ATTRIBUTE_SECTION_WEBSERVICE = "overview";
	private static final String APPLICATION_PROPERTY = "app_code";	
	private static final String NAME_PROPERTY = "app_name";	
	private static final String VERSION_PROPERTY = "app_version";		
	private static final String APPLICATION_ATTRIBUTE = "application";	
	private static final String NAME_ATTRIBUTE = "name";	
	private static final String VERSION_ATTRIBUTE = "version";	
	private static final String DESCRIPTION_ATTRIBUTE = "description";	
	private static final String DOMAIN_ATTRIBUTE = ATTRIBUTE_SECTION + "domain";	
	private static final String DOMAIN_PROPERTY = "app_domain";	

	private final String[] FILTER_ASSOC_PATHS = {"/endpoints/", "/wsdls/"};

	private static final String WEBSERVICE_PATHEXPRESSION =	"/custom/services/@{namespace}/@{name}";
	private Map<String, GenericArtifactManager> managers = new HashMap<String, GenericArtifactManager>();
	private Map<String, GovernanceArtifactConfiguration> configs = new HashMap<String, GovernanceArtifactConfiguration>();
	// extract attribute-name from associations
	private static Pattern p = Pattern.compile("\\@\\{(\\w*?):value\\}"); 
	private Map<String, ArrayList<RelationAttribute>> relAttrs = new HashMap<String, ArrayList<RelationAttribute>>();
	
	private static Pattern versionExtractPattern = Pattern.compile("\\d+[.]\\d+[.]\\d+(-[a-zA-Z0-9]+)?$");
	private static Pattern shortVersionExtractPattern = Pattern.compile("\\d+[.]\\d+$");

	public static final String[] PATHS_TO_CREATE = {
		RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + "/custom/documents/@{details_name}"
	};

	/**
	 * tested OK with 4.5.x. OPEN:  do application rename test
	 */
	@Override
	public String rename(RequestContext requestContext) throws RegistryException {
		if (!CommonUtil.isUpdateLockAvailable())
			  return null;
		
		CommonUtil.acquireUpdateLock();		
		GovernanceUtils.setTenantGovernanceSystemRegistry(CurrentSession.getTenantId());
		
		String src = requestContext.getSourcePath();
		String dest = requestContext.getTargetPath();
		String oldName = getNameFromPath(src);
		String destName = getNameFromPath(dest);
		String srcVersion = getVersionFromPath(src);
		String destVersion = getVersionFromPath(dest);
		try {
			Registry registry = requestContext.getSystemRegistry();
			registry.move(src, dest);
			Resource renamed = registry.get(dest);
			// index data
			if (oldName!=null && !oldName.equals(destName)) {
				renamed.setProperty(APPLICATION_PROPERTY, destName);
			}
			if (srcVersion!=null && !srcVersion.equals(destVersion)) {
				renamed.setProperty(VERSION_ATTRIBUTE, destVersion);
			}
			OMElement om = getOMElementFromResource(renamed);
			OMElement details = om.getFirstChildWithName(new QName("http://www.wso2.org/governance/metadata",
					getAttributeSectionName(renamed.getMediaType())));
			if (details!=null) {		
				OMElement el= details.getFirstChildWithName(new QName("http://www.wso2.org/governance/metadata",NAME_ATTRIBUTE));
				OMElement version = om.getFirstElement().getFirstChildWithName(new QName("http://www.wso2.org/governance/metadata",VERSION_ATTRIBUTE));
				if (el!=null && version!=null) {
					Repository repo =requestContext.getRepository();
					el.setText(destName);
					// only set this, if it is part of the pathExpression! otherwise let it be
					if (destVersion != null) {
						version.setText(destVersion);
					}
		            String updatedContent = GovernanceUtils.serializeOMElement(om);
		            renamed.setContent(updatedContent.getBytes()); 
		            repo.put(dest, renamed);            

		            // record this for later addition
		            adaptAssocs(dest,src, registry);
		            
		            // only application renaming has greater impact!
		            if (APPLICATION_MEDIA_TYPE.equals(renamed.getMediaType())) {
			            String [] paths = QueryHelper.getAffectedResources(oldName, registry);
						if (paths!=null) {
							for (String path : paths) {
								String newPath = path.replace("/" + oldName + "/", "/" + destName + "/");
								registry.move(path, newPath);
								Resource affected = registry.get(newPath);
								affected.setProperty(APPLICATION_PROPERTY, destName);
	
								OMElement aom = getOMElementFromResource(affected);
								String sectionName = "details";
								if (RegistryConstants.SERVICE_MEDIA_TYPE.equals(affected.getMediaType())) {
									sectionName = "overview";
								}
								OMElement adet = aom.getFirstChildWithName(new QName("http://www.wso2.org/governance/metadata",sectionName));
								OMElement ael = adet.getFirstChildWithName(new QName("http://www.wso2.org/governance/metadata","application"));
								if (ael!=null) {
									ael.setText(destName);
						            String ac = GovernanceUtils.serializeOMElement(aom);
						            affected.setContent(ac.getBytes()); 
						            repo.put(newPath, affected);

						            adaptAssocs(newPath,path, registry);
								}
							}
						}
		            }
				}
			}
	        requestContext.setProcessingComplete(true);
		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
		CommonUtil.releaseUpdateLock();
		return dest;
 	}
	
	private void adaptAssocs(String newPath, String oldPath, Registry reg) {
		oldPath = oldPath.replace(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH, "");
		String relativeNewPath = newPath.replace(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH, "");
		
		Association[] aas;
		try {
			aas = reg.getAllAssociations(newPath);
			if (aas!=null) {
				for (Association aa : aas) {
					if (isArchived(aa)) {
						continue;
					}
					// get the resource on the other side
					if (aa.getDestinationPath().equals(newPath)) {
						Resource other = reg.get(aa.getSourcePath());
						GenericArtifactManager gam = lookupArtifactManager(other.getMediaType());
						// only known mediatypes please
						if (gam!=null) {
							GenericArtifact	ga = getGenericArtifactInternally(other);
							String attrName = getAttributeNameForAssocType(other.getMediaType(), aa.getAssociationType());
							String entries[] = ga.getAttributes(attrName);
							Set<String> newArray = new HashSet<String>();
							boolean changedEntry = false;
							if (entries!=null ) {
								for (String entry : entries) {
									if (entry.contains(oldPath)) {
										entry = entry.replace(oldPath, relativeNewPath);
										changedEntry = true;

									}
									newArray.add(entry);
								}
								if (changedEntry) {
									ga.setAttributes(attrName, newArray.toArray(new String[0]));
									gam.updateGenericArtifact(ga);		
								}
							}
						}
					}					
				}
			}
		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
		
	}

	private String getNameFromPath(String path) {
		int pos = path.lastIndexOf("/");
		String nameVersion = path.substring(pos+1);
		pos = nameVersion.indexOf("_");
		if (pos > 0) {
			return nameVersion.substring(0, pos);
		}
		else {
			return nameVersion;
		}
	} 
	
	private String getVersionFromPath(String path) {
		int pos = path.lastIndexOf("/");
		String nameVersion = path.substring(pos+1);
		pos = nameVersion.indexOf("_");
		if (pos>0) {
			return nameVersion.substring(pos+1);
		}
		else {
			return null;
		}
	} 
	
	/**
	 * tested with 4.5.x: OK (OPEN: Test service to Service/Hostprog)
	 */
	@Override
	public void delete(RequestContext requestContext) throws RegistryException {
		GovernanceUtils.setTenantGovernanceSystemRegistry(CurrentSession.getTenantId());
		
		Resource r = requestContext.getResource();
		String absolutePath = requestContext.getResourcePath().getPath();
		Registry registry = requestContext.getSystemRegistry();
		GovernanceUtils.loadGovernanceArtifacts((UserRegistry) registry);
	
		String mediaType = r.getMediaType();
		String relPath = absolutePath.replace(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH,"");
	
		Association[] aas = registry.getAllAssociations(absolutePath);
		processAssociations(aas, relPath, null, registry, mediaType,
					new DeleteDestinationEntryStrategy());
	}

	@Override
	public void put(RequestContext requestContext) throws RegistryException {
		if (!CommonUtil.isUpdateLockAvailable() || !CommonUtil.isAddingAssociationLockAvailable())
		  return;
		// a weird case of strangeness
		if (RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH.equals(requestContext.getParentPath())) {
			return;
		}		
		CommonUtil.acquireAddingAssociationLock();
		CommonUtil.acquireUpdateLock();
		
		Resource r = requestContext.getResource();		
		
		// backport from 4.5.3
		if ("true".equals(r.getProperty("registry.WSDLImport"))) {
			r.removeProperty("registry.WSDLImport");                    	
		}
		
		String mediaType = r.getMediaType();
		GovernanceUtils.setTenantGovernanceSystemRegistry(CurrentSession.getTenantId());
		// this comes once with chroot wrapped, and once without!
		Registry registry = GovernanceUtils.getGovernanceSystemRegistry(requestContext.getRegistry());
		
		String absolutePath = r.getPath();
		if (absolutePath == null) {
			absolutePath = requestContext.getResourcePath().getPath();
		}
		if (!absolutePath.startsWith(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH)) {
			absolutePath = RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + absolutePath;
		}
		String relPath = absolutePath;
		relPath= relPath.replace(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH, "");		
		
		GovernanceUtils.loadGovernanceArtifacts((UserRegistry) registry);
		GenericArtifact ga = getGenericArtifactInternally(r);
		if (RegistryConstants.SERVICE_MEDIA_TYPE.equals(mediaType)) {
			updateWebServiceVersionAttribute(ga, false);
		}
		// this will be null for "application" artifacts. those are handled in
		// ApplicationCreateHandler
		boolean idxChange = indexProperty(r,ga,NAME_ATTRIBUTE,NAME_PROPERTY);
		// in case of an application the appl_code comes from details_name!
		if (APPLICATION_MEDIA_TYPE.equals(mediaType)) {
			idxChange |= indexProperty(r,ga,NAME_ATTRIBUTE,APPLICATION_PROPERTY);	
			
			String attrApplCode = ATTRIBUTE_SECTION + "_" + NAME_ATTRIBUTE;
			String kuerzel = ga.getAttribute(attrApplCode);
			if (kuerzel!=null) {
				// create all subfolders
				String[] destPaths = PATHS_TO_CREATE;
				if (destPaths!=null) {
					for (String destPath : destPaths) {
						destPath = destPath.replaceAll("@\\{"+attrApplCode+"\\}", kuerzel);
						if (!requestContext.getRegistry().resourceExists(destPath)) {				
							Collection c = requestContext.getRegistry().newCollection();
							CollectionImpl ci = (CollectionImpl) c;
							requestContext.getRegistry().put(destPath, ci);
						}
					}
				}
			}

		}
		else {
			idxChange |= indexProperty(r,ga,APPLICATION_ATTRIBUTE,APPLICATION_PROPERTY);
		}
		idxChange |= indexProperty(r,ga,VERSION_ATTRIBUTE,VERSION_PROPERTY);
		idxChange |= indexProperty(r,ga,DESCRIPTION_ATTRIBUTE,null);
		idxChange |= indexProperty(r,ga,DOMAIN_ATTRIBUTE,DOMAIN_PROPERTY);

		if (idxChange) {
			requestContext.getRepository().put(absolutePath, r);
		}

		GenericArtifactManager g = lookupArtifactManager(mediaType);
		if (RegistryConstants.SERVICE_MEDIA_TYPE.equals(mediaType)) {
			g.updateGenericArtifact(ga);
		}

		boolean isNextNamespace = false;
		String att = ga.getAttribute(ARTIFACT_NEXTVERSION);
		// try the alternative
		if (att == null) {
			att = ga.getAttribute(ARTIFACT_NEXTNAMESPACE);
			if (att!=null) {
				isNextNamespace = true;
			}
		}
		// remove existing version assocs, if there is no relation
		// commented... do it manually..performance
	    if (att != null) {
			Association[] assocs = registry.getAssociations(absolutePath, ASSOC_NEXTVERSION);
			boolean isDone=false;
			if (assocs!=null) { 
				for (Association aa : assocs) {
					if (!isArchived(aa)) {
						// seems we did this already once
						if (aa.getSourcePath().equals(absolutePath)) {
							isDone = true;
						}
					}
				}
			}
			// ADDING should NOT trigger the new version already (i.e. do this
			// only on EDIT)
			if (!isDone && registry
					.resourceExists(relPath)) {
				// the new path where the artifact will be put
				GenericArtifact gaNew = g.newGovernanceArtifact(new QName(att, ga.getAttribute("overview_name")));
				if ( ga.getAttributeKeys()!=null) {
					// copy the attributes
					for (String key : ga.getAttributeKeys()) {
						gaNew.setAttributes(key, ga.getAttributes(key));
					}
				}
				if (!isNextNamespace) {
					gaNew.setAttribute(ARTIFACT_VERSION, att);
					gaNew.removeAttribute(ARTIFACT_NEXTVERSION);
				} else {
					gaNew.setAttribute("namespace", att);
					gaNew.setAttribute(ARTIFACT_NAMESPACE, att);
					gaNew.removeAttribute(ARTIFACT_NEXTNAMESPACE);
				}
				
				// this is a relative path
				String path = getArtifactPath(gaNew, mediaType);

				// create or copy stuff
				if (!registry.resourceExists(path)) {
						// force version extraction for new resource
						updateWebServiceVersionAttribute(gaNew, true);
						g.addGenericArtifact(gaNew);
				}
				else {
					Resource fix = registry.get(path);
					GenericArtifact fga = getGenericArtifactInternally(fix);
					List<RelationAttribute> ras = getRelationAttributeList(mediaType);
					for (RelationAttribute ra : ras) {
						if ("source".equals(ra.getType())) {
							String[] entries = gaNew.getAttributes(ra.getAttributeName());
							if (entries!=null) {
								fga.setAttributes(ra.getAttributeName(), entries);
							}
						}
					}
					g.updateGenericArtifact(fga);
				}
				Resource old = requestContext.getResource();
				if (old != null) {
					String existingPath = relPath;
					Association[] aas = registry
							.getAllAssociations(existingPath);
					processAssociations(aas, existingPath, path,
							registry, mediaType,
							new UpdateDestinationEntryStrategy());
					// create version assocs
					registry.addAssociation(existingPath,
								path, ASSOC_NEXTVERSION);
					registry.addAssociation(
								path, existingPath,
								ASSOC_PREVIOUSVERSION);
				}
			}
		}

		//requestContext.setProcessingComplete(true);
		CommonUtil.releaseAddingAssociationLock();
		CommonUtil.releaseUpdateLock();
	}

	private GenericArtifactManager lookupArtifactManager(String mediaType) {
		GenericArtifactManager retval = managers.get(mediaType);
		if (retval == null) {

			try {
				EmbeddedRegistryService rs = new EmbeddedRegistryService(
						RegistryContext.getBaseInstance());
				UserRegistry registry = rs.getGovernanceSystemRegistry();

				GovernanceArtifactConfiguration config = lookupArtifactConfiguration(mediaType);
				if (config!=null) {
					String artifactNameAttribute = config
							.getArtifactNameAttribute();
					String artifactNamespaceAttribute = config
							.getArtifactNamespaceAttribute();
					String artifactElementNamespace = config
							.getArtifactElementNamespace();
					String path = config.getPathExpression();
					retval = new GenericArtifactManager(registry,
							config.getMediaType(), artifactNameAttribute,
							artifactNamespaceAttribute,
							config.getArtifactElementRoot(),
							artifactElementNamespace, path,
							config.getRelationshipDefinitions());
					managers.put(mediaType, retval);
				}
			} catch (RegistryException e) {
				log.error(e.getMessage());
			}
		}
		return retval;
	}

	private GovernanceArtifactConfiguration lookupArtifactConfiguration(
			String mediaType) {
		GovernanceArtifactConfiguration retval = configs.get(mediaType);
		if (retval == null) {
			// special handling for web services. Legacy related
			if (RegistryConstants.SERVICE_MEDIA_TYPE.equals(mediaType)) {
				GovernanceArtifactConfiguration gac = new GovernanceArtifactConfiguration();
				gac.setPathExpression(WEBSERVICE_PATHEXPRESSION);
				gac.setArtifactNameAttribute(GovernanceConstants.SERVICE_NAME_ATTRIBUTE);
				gac.setMediaType(RegistryConstants.SERVICE_MEDIA_TYPE);
				gac.setArtifactNamespaceAttribute(GovernanceConstants.SERVICE_NAMESPACE_ATTRIBUTE);
				gac.setArtifactElementNamespace(GovernanceConstants.SERVICE_ELEMENT_NAMESPACE);
				gac.setArtifactElementRoot(GovernanceConstants.SERVICE_ELEMENT_ROOT);
				// assoc handling
				List<Association> assocs = new ArrayList<Association>();
				Association aa = new Association(null,
						"@{isContained_entry:value}", "isContained");
				assocs.add(aa);
				aa = new Association("@{isContained_entry:value}", null,
						"contains");
				assocs.add(aa);
				aa = new Association(null, "@{calls_entry:value}", "calls");
				assocs.add(aa);
				aa = new Association("@{calls_entry:value}", null, "isCalled");
				assocs.add(aa);
				aa = new Association(null, "@{uses_entry:value}", "uses");
				assocs.add(aa);
				aa = new Association("@{uses_entry:value}", null, "isUsed");
				assocs.add(aa);
				aa = new Association(null, "@{isResponsible_entry:value}", "isResponsible");
				assocs.add(aa);
				aa = new Association("@{isResponsible_entry:value}", null, "responsible");
				assocs.add(aa);
				aa = new Association("@{lokaleDokumente_entry:value}", null, "referencedBy");
				assocs.add(aa);
				gac.setRelationshipDefinitions(assocs
						.toArray(new Association[assocs.size()]));
				configs.put(RegistryConstants.SERVICE_MEDIA_TYPE, gac);
				retval = gac;
			} else {
				EmbeddedRegistryService rs;
				try {
					rs = new EmbeddedRegistryService(
							RegistryContext.getBaseInstance());
					UserRegistry configRegistry = rs.getSystemRegistry();
					List<GovernanceArtifactConfiguration> configurations = GovernanceUtils
							.findGovernanceArtifactConfigurations(configRegistry);
					for (GovernanceArtifactConfiguration config : configurations) {
						configs.put(config.getMediaType(), config);
						if (config.getMediaType().equals(mediaType)) {
							retval = config;
						}
					}
				} catch (RegistryException e) {
					log.error(e.getMessage());
				}
			}
		}

		return retval;
	}

	private List<RelationAttribute> getRelationAttributeList(String mediaType) {
		List<RelationAttribute> retval = relAttrs.get(mediaType);
		if (retval == null) {
			// check for associations, which do not originate from this entity
			GovernanceArtifactConfiguration c = lookupArtifactConfiguration(mediaType);
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
								result, type));
					}
				}
			}
			relAttrs.put(mediaType, ra);
			retval = ra;
		}
		return retval;
	}

	private OMElement getOMElementFromResource(Resource r) {
		OMElement om = null;
		try {
			String content = null;
			if (r.getContent() instanceof String) {
				content = (String) r.getContent();
			} else {
				content = new String((byte[]) r.getContent());
			}
			XMLStreamReader reader = XMLInputFactory.newInstance()
					.createXMLStreamReader(new StringReader(content));
			 om = new StAXOMBuilder(reader).getDocumentElement();
		} catch (RegistryException e) {
			log.error(e.getMessage());
		} catch (XMLStreamException e) {
			log.error(e.getMessage());
		} catch (FactoryConfigurationError e) {
			log.error(e.getMessage());
		}
		return om;
	}

	private String getArtifactPath(GovernanceArtifact ga, String mediaType) {
		GovernanceArtifactConfiguration cf = lookupArtifactConfiguration(mediaType);
		String path = cf.getPathExpression();
		try {
			path = GovernanceUtils.getPathFromPathExpression(path, ga);
			// web service put the prefix back in place... we do not need that
			path = path.replace(
					RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH, "");
		} catch (GovernanceException e) {
			log.error(e.getMessage());
		}
		return path;
	}

	private String getAttributeNameForAssocType(String mediaType,
			String assocType) {
		String retval = null;
		List<RelationAttribute> ras = getRelationAttributeList(mediaType);
		for (RelationAttribute ra : ras) {
			if (ra.getRelationName().equals(assocType)) {
				retval = ra.getAttributeName();
				break;
			}
		}
		return retval;
	}

	/**
	 * 
	 * @param aas
	 * @param oldPath
	 *            it is relative (without /_system/governance)
	 * @param newPath
	 *            it is relative (without /_system/governance)
	 * @param registry
	 * @param mediaType
	 */
	private void processAssociations(Association[] aas, String oldPath,
			String newPath, Registry registry, String mediaType,
			DestinationEntryProcessor dep) {
		if (oldPath.equals(newPath)) {
			return;
		}
		if (aas != null) {
			String absolutePath = RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH
					+ oldPath;
			List<RelationAttribute> ras = getRelationAttributeList(mediaType);
			Set<String> blackList = new HashSet<String>(); // for the exclusion
															// of "depends"
															// backlinks
			for (Association aa : aas) {
				String type = null;
				// these are archived versions!
				if (isArchived(aa)) {
					continue;
				}
				// we only do assocs
				if ((aa.getAssociationType().endsWith("depends"))) {
					blackList.add(aa.getDestinationPath());
					continue;
				}
				// skip endpoints
				if (FILTER_ASSOC_PATHS!=null) {
					boolean toBeFiltered = false;
					for (String filter: FILTER_ASSOC_PATHS) {
						if ((aa.getSourcePath().contains(filter))) {
							toBeFiltered = true;
							break;
						}						
					}
					if (toBeFiltered) {
						continue;
					}
				}
				// check backlinked "depends" (usually "usedBy"). we ignore them
				boolean ignore = false;
				for (String entry : blackList) {
					if (aa.getSourcePath().equals(entry)) {
						ignore = true;
						break;
					}
				}
				if (ignore) {
					continue;
				}
				if (ASSOC_NEXTVERSION.equals(aa.getAssociationType())
						|| ASSOC_PREVIOUSVERSION
								.equals(aa.getAssociationType())) {
					continue;
				}
				if (absolutePath.equals(aa.getSourcePath())) {
					type = "source";
				}
				if (absolutePath.equals(aa.getDestinationPath())) {
					type = "destination";
				}
				boolean match = false;
				for (RelationAttribute ra : ras) {
					// not one of ours
					if (!ra.getRelationName().equals(aa.getAssociationType())) {
						continue;
					}
					if (ra.getType().equals(type)
							&& ra.getRelationName().equals(
									aa.getAssociationType())) {
						continue;
					} else {
						// in there are the relevant equally named ones
						match = true;
					}
				}
				// we need to copy these assocs
				if (!match) {
					if ("destination".equals(type) ) {
						try {
							Resource fix = registry.get(aa.getSourcePath());
							GenericArtifact fga = getGenericArtifactInternally(fix);
							String attrName = getAttributeNameForAssocType(
									fix.getMediaType(), aa.getAssociationType());
							GenericArtifactManager gam = lookupArtifactManager(fix
									.getMediaType());

							// delete operation has no new Path
							dep.process(new DestinationEntryProcessingInfo(gam,
									fga, attrName, newPath, oldPath));

							// there should be a more elegant solution
							if (dep instanceof UpdateDestinationEntryStrategy) {
								registry.addAssociation(
										aa.getSourcePath(),
										RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH
												+ newPath,
										aa.getAssociationType());
							}
						} catch (RegistryException e) {
							log.error(e.getMessage());
						}
					}
					// there should be a more elegant solution
					if (dep instanceof UpdateDestinationEntryStrategy) {
						if ("source".equals(type)) {
							try {
								registry.addAssociation(
										RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH
												+ newPath,
										aa.getDestinationPath(),
										aa.getAssociationType());
							} catch (RegistryException e) {
								log.error(e.getMessage());
							}
						}
					}
				}
			}
		}
	}

	private boolean isArchived(Association aa) {
		boolean retval = false;
		// these are archived versions!
		if ((aa.getSourcePath() != null && aa.getSourcePath().contains(
				";version:"))
				|| (aa.getDestinationPath() != null && aa.getDestinationPath()
						.contains(";version:"))) {
			retval = true;
		}
		return retval;
	}

	private boolean indexProperty(Resource r, GovernanceArtifact ga, String attrName, String propertyName) {
		boolean isChanged = false; 
		String kuerzel;
		attrName = getAttributeSectionName(r.getMediaType()) + "_" + attrName;
		try {
			kuerzel = ga.getAttribute(attrName);
			if (kuerzel != null) {
				// special handling for description!
				if (propertyName == null && !kuerzel.equals(r.getDescription())) {
					r.setDescription(kuerzel);
					isChanged = true;
				}
				else if (propertyName!=null && !kuerzel.equals(r.getProperty(propertyName))) {
					r.setProperty(propertyName, kuerzel);
					isChanged = true;
				}
			}
		} catch (GovernanceException e) {
			log.error("problem reading attribute: " + e.getMessage());
		}
		return isChanged;
	}
	
	private String getAttributeSectionName(String mediaType) {
		String retval = ATTRIBUTE_SECTION ;			
		if (RegistryConstants.SERVICE_MEDIA_TYPE.equals(mediaType)) {
			retval = ATTRIBUTE_SECTION_WEBSERVICE;
		}
		return retval;
	}
	
	private boolean updateWebServiceVersionAttribute(GovernanceArtifact ga, boolean force) {
		boolean isChanged = false;
		try {
			String oldVersion = ga.getAttribute("overview_version");
			String baseVersionExtract = ga.getAttribute("overview_namespace");
			if (baseVersionExtract != null && ("1.0.0-SNAPSHOT".equals(oldVersion) || force)) {
				Matcher m = versionExtractPattern.matcher(baseVersionExtract);
		        if (m.find()) {
		        	ga.setAttribute("overview_version",  m.group());
		        	isChanged=true;		        	
		        }
		        else {
					m = shortVersionExtractPattern.matcher(baseVersionExtract);        
			        if (m.find()) {
			        	ga.setAttribute("overview_version", m.group() + ".0");
			        	isChanged=true;
			        }
		        }
			}				
		} catch (GovernanceException e) {
		}		
		return isChanged;
	}
	
	private GenericArtifact getGenericArtifactInternally(Resource r) throws RegistryException {
        Object rawContent = r.getContent();
        String content; 
        if (rawContent instanceof String) {
        	content = (String) rawContent;
        } else {
        	content = new String((byte[]) rawContent);
        } 		
 		XMLStreamReader reader = null;
		try {
			reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(content));
		} catch (XMLStreamException e) {
			log.error(e.getMessage());
		}
		GenericArtifactManager g = lookupArtifactManager(r.getMediaType());
		GenericArtifact ga = g
				.newGovernanceArtifact(new StAXOMBuilder(reader)
						.getDocumentElement());
		ga.setId(r.getUUID());
		if (GovernanceConstants.SERVICE_MEDIA_TYPE.equals(r.getMediaType())) {
			((GenericArtifactImpl) ga).setQName(new QName (ga.getAttribute(GovernanceConstants.SERVICE_NAMESPACE_ATTRIBUTE), ga.getAttribute(GovernanceConstants.SERVICE_NAME_ATTRIBUTE)));
			}
		else {
			((GenericArtifactImpl) ga).setQName(new QName(ga.getAttribute("details_name")));
		}
		return ga;
	}
}
