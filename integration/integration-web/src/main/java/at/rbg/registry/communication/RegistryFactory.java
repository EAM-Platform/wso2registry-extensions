package at.rbg.registry.communication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.wso2.carbon.authenticator.stub.AuthenticationAdminStub;
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.governance.api.generic.GenericArtifactManager;
import org.wso2.carbon.governance.api.util.GovernanceArtifactConfiguration;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.governance.custom.lifecycles.checklist.stub.CustomLifecyclesChecklistAdminServiceStub;
import org.wso2.carbon.registry.core.Association;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.session.UserRegistry;
import org.wso2.carbon.registry.search.stub.SearchAdminServiceStub;
import org.wso2.carbon.registry.ws.client.registry.WSRegistryServiceClient;

import at.rbg.registry.model.ModelConstants;

public class RegistryFactory {
	private static final Logger log = LoggerFactory
			.getLogger(RegistryFactory.class);
	private Map<String, GenericArtifactManager> managers = new HashMap<String, GenericArtifactManager>();
	private Map<String, GovernanceArtifactConfiguration> managerConfigs = new HashMap<String, GovernanceArtifactConfiguration>();
	private Map<String, String> mediaTypeKeyMap = new HashMap<String, String>();
	// TODO: extract stuff like this into an own project
	private static final String WEBSERVICE_PATHEXPRESSION = "/custom/services/@{namespace}/@{name}";
	 
	private Registry registry;
	private Registry wsRegistry; // htis is the WS registry
	private SearchAdminServiceStub sas;
	private CustomLifecyclesChecklistAdminServiceStub clc;
	// registry
	private String baseUrl;
	private String username;
	private String password;
	private String sessionCookie;
	// truststore
	private String truststorePassword;
	private Resource truststore;
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setTruststorePassword(String truststorePassword) {
		this.truststorePassword = truststorePassword;
	}
	
	public void setTruststore(Resource truststore) {
		this.truststore = truststore;
	}
	
	public void destroy() {
		managers.clear();
		managerConfigs.clear();
	}
	
	public void init() {
		
		setSSLSettings();
		// needed to avoid the workaround with carbon.xml 
	    System.setProperty("carbon.repo.write.mode", "true");

		
		try {
			
			initSession();
			initSearchAdminServiceStub();
			initLifecycleServiceStub();
			
			this.wsRegistry = new WSRegistryServiceClient(baseUrl, sessionCookie);		
			registry = GovernanceUtils.getGovernanceUserRegistry(wsRegistry, username);

			GovernanceUtils.loadGovernanceArtifacts((UserRegistry) registry);
			    
			for (String name : ModelConstants.getManagedArtifacts()) {
				GovernanceArtifactConfiguration configuration = GovernanceUtils
						.findGovernanceArtifactConfiguration(name, wsRegistry);
				if ("webservice".equals(name)) {
					configuration = getConfigForWebService();
				}
				managerConfigs.put(name, configuration);
				String artifactNameAttribute = configuration 
						.getArtifactNameAttribute();
				String artifactNamespaceAttribute = configuration
						.getArtifactNamespaceAttribute();
				String artifactElementNamespace = configuration
						.getArtifactElementNamespace();

				String path = configuration.getPathExpression(); 
				String mediaType = configuration.getMediaType();
				GenericArtifactManager gam = new GenericArtifactManager(
						registry, mediaType, artifactNameAttribute,
						artifactNamespaceAttribute,
						configuration.getArtifactElementRoot(),
						artifactElementNamespace, path,
						configuration.getRelationshipDefinitions());

				managers.put(name, gam);
				mediaTypeKeyMap.put(mediaType, name); // commutative relation
														
			}

		} catch (AxisFault e) {
			log.error(e.getMessage());

		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
	}
	
	public void checkAlive() {
		Registry reg = this.wsRegistry;
		// out first run through here! this is a lazy init
		if (reg==null) {
			init();
		}
		// session timeout or connection in network broke
		else {
			try { 
				reg.resourceExists("/");
			} catch (Exception e) {
				// registry is down
				if (e.getCause() instanceof AxisFault || reg == null) {
					// retry
					init();
				}
				log.error("retrying registry init");
			}
		}
	}

	/**
	 * workaround for web services.
	 * @return
	 */
	private GovernanceArtifactConfiguration getConfigForWebService() {
		GovernanceArtifactConfiguration c = new GovernanceArtifactConfiguration();
		c.setMediaType(GovernanceConstants.SERVICE_MEDIA_TYPE);
		c.setArtifactNameAttribute(GovernanceConstants.SERVICE_NAME_ATTRIBUTE);
		c.setArtifactNamespaceAttribute(GovernanceConstants.SERVICE_NAMESPACE_ATTRIBUTE);
		c.setArtifactElementRoot(GovernanceConstants.SERVICE_ELEMENT_ROOT);
		c.setArtifactElementNamespace(GovernanceConstants.SERVICE_ELEMENT_NAMESPACE);
		c.setPathExpression(WEBSERVICE_PATHEXPRESSION);
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
		c.setRelationshipDefinitions(assocs
				.toArray(new Association[assocs.size()]));
		return c;
	}

	private void setSSLSettings() {
		try {
			InputStream is = truststore.getInputStream();
			OutputStream out = new java.io.FileOutputStream(System.getProperty("java.io.tmpdir") + truststore.getFilename());
			byte buf[]=new byte[1024];
			  int len;
			  while((len=is.read(buf))>0)
			  out.write(buf,0,len);
			  out.close();
			  is.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} 
		
		System.setProperty("carbon.repo.write.mode", "true");
		System.setProperty("javax.net.ssl.trustStore", System.getProperty("java.io.tmpdir") +
				"client-truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);

		if (log.isDebugEnabled()) {
			System.setProperty("javax.net.debug", "all");
			System.setProperty("java.security.debug", "all");
		}		
	}
	
	public Registry getRemoteRegistryInstance() {
		return registry;
	}

	public SearchAdminServiceStub getSearchAdminServiceStub() {
		return sas;
	}
	
	public CustomLifecyclesChecklistAdminServiceStub getCustomLifecyclesChecklistAdminServiceStub() {
		return clc;
	}
	
	/**
	 * 
	 * @param name
	 *            generic artifact key
	 * @return
	 */
	public GenericArtifactManager getManagerForArtifactType(String key) {
		GenericArtifactManager g = managers.get(key);
		if (g == null) {
			throw new IllegalStateException("ArtifactType " + key
					+ " is not known!");
		}
		return g;
	}

	public String getKeyForMediaType(String mediaType) {
		String key = mediaTypeKeyMap.get(mediaType);
		// we do not care anymore :)
//		if (key == null) {
//			throw new IllegalStateException("Key for MediaType " + mediaType
//					+ " is not known!");
//		}
		return key;

	}

	public GovernanceArtifactConfiguration getConfigForArtifactType(String key) {
		return managerConfigs.get(key);
	}

	public Set<String> getMediaTypes() {
		return mediaTypeKeyMap.keySet();
	}
	
    private void initSearchAdminServiceStub() throws AxisFault {
        String serviceURL = this.baseUrl + "SearchAdminService";
    	this.sas = new SearchAdminServiceStub(serviceURL);
        org.apache.axis2.client.ServiceClient client = sas._getServiceClient();
        org.apache.axis2.client.Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, sessionCookie);
        sas._getServiceClient().getOptions().setTimeOutInMilliSeconds(600000);
        log.info("searchAdminServiceStub created");
    }
    
    private void initLifecycleServiceStub() throws AxisFault {
        String serviceURL = this.baseUrl + "CustomLifecyclesChecklistAdminService";
    	this.clc = new CustomLifecyclesChecklistAdminServiceStub(serviceURL);
        org.apache.axis2.client.ServiceClient client = clc._getServiceClient();
        org.apache.axis2.client.Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, sessionCookie);
        clc._getServiceClient().getOptions().setTimeOutInMilliSeconds(600000);
        log.info("lifecycleServiceStub created");
    }

    
    private void initSession() throws AxisFault {
    	AuthenticationAdminStub as = new AuthenticationAdminStub(this.baseUrl + "AuthenticationAdmin");
        try {
			as.login(username, password, "127.0.0.1");
		} catch (RemoteException e) {
			log.error(e.getMessage());
		} catch (LoginAuthenticationExceptionException e) {
			log.error(e.getMessage());
		}
        ServiceContext serviceContext = as. 
                _getServiceClient().getLastOperationContext().getServiceContext(); 
        sessionCookie = (String) serviceContext.getProperty(HTTPConstants.COOKIE_STRING); 
    }

}
