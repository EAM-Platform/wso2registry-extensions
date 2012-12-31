package at.rbg.registry.publish.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.stream.FactoryConfigurationError;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.app.RemoteRegistryService;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.session.UserRegistry;

/**
 * Publishes artifacts and resources to a workspace.
 * 
 * @goal execute
 */
public class PublishMojo extends AbstractMojo {
	private static final String GOVERNANCE_ARCHIVE_MEDIATYPE = "application/vnd.wso2.governance-archive";
	private static final String MEDIATYPE_APPLICATION = "application/vnd.wso2-application+xml";
	private static final String sqlFindRegName = "SELECT REG_PATH_ID, REG_NAME FROM REG_RESOURCE "
			+ " WHERE REG_MEDIA_TYPE = ? AND REG_NAME LIKE ? " ;
	
	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * The user's settings.
	 * 
	 * @parameter expression="${settings}"
	 * @required
	 * @readonly
	 */
	private Settings settings;

	/**
	 * The base directory
	 * 
	 * @parameter expression="${basedir}"
	 * @required
	 * @readonly
	 */
	private File basedir;

	/**
	 * WSO2 Service URL.
	 * 
	 * @parameter
	 */
	private String url;

	/**
	 * The server id to use for username/password information inside your
	 * settings.xml.
	 * 
	 * @parameter
	 */
	private String serverId;

	/**
	 * The username (see serverId as well).
	 * 
	 * @parameter
	 */
	private String username;

	/**
	 * The username (see serverId as well).
	 * 
	 * @parameter
	 */
	private String password;

	/**
	 * Resources to publish to Galaxy
	 * 
	 * @parameter
	 */
	private String[] includes;

	/**
	 * Resources to exclude from publishing to Galaxy
	 * 
	 * @parameter
	 */
	private String[] excludes;

	/**
	 * Whether or not to publish the artifact produced by this build.
	 * 
	 * @parameter
	 */
	private boolean publishProjectArtifact = false;

	/**
	 * Whether or not to publish metadata such as the Maven artifact/group IDs
	 * and SCM information.
	 * 
	 * @parameter
	 */
	private boolean publishProjectMetadata = true;

	/**
	 * Whether or not to overwrite artifact versions.
	 * 
	 * @parameter
	 */
	private boolean overwrite = false;

	private Registry registry;

	/**
	 * Set this to true to skip execution. Or set the property
	 * galaxy.publish.skip to true to skip execution.
	 * 
	 * @parameter 
	 */
	private boolean skip;

	/**
	 * Set this to true if you only want to show what would be uploaded.
	 * 
	 * @parameter
	 */
	private boolean showOnly = false;

	/**
	 * Use the artifact's version when publishing. Otherwise use the project's
	 * version. Defaults to false;
	 * 
	 * @parameter
	 */
	private boolean useArtifactVersion;

	/**
	 * the basepath for web services in the registry is /custom/services
	 * (/_system/governance/ is abstracted by getting the SystemRegistry on
	 * connect
	 * 
	 * @parameter
	 */
	private String serviceBasePath = "/custom/services";

	/**
	 * the basepath for wsdls in the registry is /custom/services
	 * (/_system/governance/ is abstracted by getting the SystemRegistry on
	 * connect
	 * 
	 * @parameter
	 */
	private String wsdlBasePath = "/custom/wsdls";

	/**
	 * the application the service should be associated with if this is not set
	 * or the application does not exists in the registry, an error is thrown
	 * 
	 * @parameter
	 */
	private String applicationName;
	
	/**
	 * the name for the artifact in a published POM
	 * e.g. efw-client
	 * 
	 * @parameter
	 */
	private String pomArtifactId;

	/**
	 * do not try to assoc the artifact
	 * with an application.
	 * This can be because there is none defined
	 * yet.
	 * 
	 * @parameter
	 */
	private boolean skipApplicationAssoc = false;


	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip) {
			getLog().info("Skipping Registry publishing.");
			return;
		}

		if (!skipApplicationAssoc && (applicationName == null || applicationName.isEmpty())) {
			throw new MojoFailureException(
					"Pls. configure 'applicationName' to an existing application!");
		}

		if (showOnly) {
			getLog().info(
					"showOnly mode is on. No changes will be made to the repository.");
		}

		if (serverId != null) {
			Server server = settings.getServer(serverId);

			if (server == null) {
				throw new MojoFailureException("Could not find server: "
						+ serverId);
			}

			if (server.getUsername() == null || server.getPassword() == null
					|| server.getConfiguration()== null) {
				throw new MojoFailureException(
						"You must specify a username & password and serverURL in your settings.xml!");
			}
			Xpp3Dom serverConfig = (Xpp3Dom) server.getConfiguration();
			Xpp3Dom urlConfig = serverConfig.getChild(0);
			if (urlConfig != null) {
				String name = urlConfig.getName();
				if ("url".equals(name)) {
					this.url= urlConfig.getValue();
				}
				else {
					throw new MojoFailureException(
							"registry serverURL config in your settings.xml is wrong -> s. Wiki. only one child element 'url' is allowed!");									
				}
			}
			else {
				throw new MojoFailureException(
						"registry serverURL config in your settings.xml is wrong!");				
			}
			this.username = server.getUsername();
			this.password = server.getPassword();
		} else {
			if (username == null || password == null) {
				throw new MojoFailureException(
						"You must specify a username & password.");
			}
		}

		// needed in order that wso2 registry does not throw up..
		System.setProperty("carbon.repo.write.mode", "true");
		RemoteRegistryService rrs;
		try {
			rrs = new RemoteRegistryService(url, username, password);
			registry = rrs.getUserRegistry();
			GovernanceUtils.loadGovernanceArtifacts((UserRegistry) registry);

		} catch (RegistryException e) {
			throw new MojoExecutionException(e.getMessage());
		}

		publish();
	}

	private void publish() throws MojoFailureException, MojoExecutionException {

		Set<Object> artifacts = new HashSet<Object>();

		if (publishProjectArtifact) {
			artifacts.add(project.getArtifact());
		}

		if (artifacts.size() > 0) {
			for (Iterator<Object> itr = artifacts.iterator(); itr.hasNext();) {
				Artifact a = (Artifact) itr.next();

				publishArtifact(a);
			}
		}

		if (includes != null || excludes != null) {
			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setIncludes(includes);
			scanner.setExcludes(excludes);
			scanner.setBasedir(basedir);
			scanner.scan();

			String[] files = scanner.getIncludedFiles();
			if (files != null) {
				for (String file : files) {
					publishFile(new File(file), project.getArtifact()
							.getVersion(), project.getArtifact());
				}
			}
		}
	}

	private void publishArtifact(Artifact a) throws MojoExecutionException,
			MojoFailureException {
		String version;
		if (useArtifactVersion) {
			version = a.getVersion();
		} else {
			version = project.getVersion();
		}
		File file = a.getFile();
		if (file != null) {
			publishFile(file, version, a);
		}
	}

	/**
	 * @param file
	 * @param version
	 * @param a
	 * @throws MojoFailureException
	 * @throws MojoExecutionException
	 */
	private void publishFile(File file, String version, Artifact a)
			throws MojoFailureException, MojoExecutionException {
		if (version == null) {
			throw new NullPointerException("Version can not be null!");
		}

		if (!skipApplicationAssoc) {
			checkForAppplicationExistance();
		}
		
		String filename = file.getName();
		getLog().info(filename + " is gonna be published");

		try {
			List<InfoContainer> ics = UnzipUtil.unzip(file);
			Set<String> servicePaths = new HashSet<String>();
			if (!showOnly) {
				for (InfoContainer ic : ics) {
					// for now we assume there are only wsdls in there!
					String wsdlPath = wsdlBasePath + ic.getWsdlPath();
					servicePaths.add(ic.getMetadataPath());
					boolean existsWsdl = registry.resourceExists(wsdlPath);
					if (!overwrite && existsWsdl) {
						throw new RegistryException(
								wsdlPath
										+ " already exists. you need to specify 'overwrite=true'");
					}
					if (existsWsdl) {
						registry.delete(wsdlPath);
					}
				}
				
				// save existing metadata (lifecycle!)
				Map<String, Service> oldServices = retrieveMetadata(servicePaths);
				
				// put zip into target
				InputStream in = new FileInputStream(file);
				Resource newr = registry.newResource();
				newr.setVersionableChange(false);
				newr.setContentStream(in);
				newr.setMediaType(GOVERNANCE_ARCHIVE_MEDIATYPE);
				registry.put("/" + filename, newr);

				updateMetadata(a, version, servicePaths, oldServices);
				
				getLog().info(
						"Updated artifact " + filename + " (version " + version
								+ ")");

			} else {
				getLog().info("publishable wslds in the zip:");
				for (InfoContainer ic : ics) {
					getLog().info(
							"wsdl would be put to " + wsdlBasePath
									+ ic.getWsdlPath());
					getLog().info(
							"service definition would be put to "
									+ serviceBasePath + ic.getMetadataPath());
				}
			}

		} catch (IOException e) {
			throw new MojoExecutionException(
					"Could not upload artifact to Registry: " + filename, e);
		} catch (RegistryException e) {
			throw new MojoExecutionException(
					"Could not upload artifact to Registry: " + filename, e);
		} catch (FactoryConfigurationError e) {
			getLog().error(e.getMessage());
		}

	}


	public void setProject(MavenProject project) {
		this.project = project;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public void setBasedir(File basedir) {
		this.basedir = basedir;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	private String getPom(Artifact a, String version) {
		StringBuffer s = new StringBuffer("<dependency>");
		s.append("<groupId>");
		s.append(a.getGroupId());
		s.append("</groupId>");
		s.append("<artifactId>");
		if (pomArtifactId != null) {
			s.append(pomArtifactId);			
		}
		else {
			s.append(a.getArtifactId());
		}
		s.append("</artifactId>");
		s.append("<version>");
		s.append(version);
		s.append("</version>");
		s.append("</dependency>");

		return s.toString();
	}

	public void setShowOnly(boolean showOnly) {
		this.showOnly = showOnly;
	}

	public void setUseArtifactVersion(boolean useArtifactVersion) {
		this.useArtifactVersion = useArtifactVersion;
	}

	public void setOverwrite(boolean b) {
		this.overwrite = b;
	}

	public void setPublishProjectMetadata(boolean publishProjectMetadata) {
		this.publishProjectMetadata = publishProjectMetadata;
	}

	public void setServiceBasePath(String serviceBasePath) {
		this.serviceBasePath = serviceBasePath;
	}

	public void setWsdlBasePath(String wsdlBasePath) {
		this.wsdlBasePath = wsdlBasePath;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setPomArtifactId(String pomArtifactId) {
		this.pomArtifactId = pomArtifactId;
	}

	public void setSkipApplicationAssoc(boolean skipApplicationAssoc) {
		this.skipApplicationAssoc = skipApplicationAssoc;
	}

	private Set<String> getGenericArtifactsforPaths(Registry registry,
			String[] paths) {
		Set<String> retval = new TreeSet<String>();
		
		if (paths != null) {
			for (String path : paths) {
				try {
					Resource r = registry.get(path);	
					GovernanceArtifact ga = GovernanceUtils.retrieveGovernanceArtifactByPath(registry,r.getPath());
//					String content = new String((byte[]) r.getContent());
//					XMLStreamReader reader;
//					reader = XMLInputFactory.newInstance()
//							.createXMLStreamReader(new StringReader(content));
//					GovernanceArtifact ga = GovernanceArtifactImpl.create(registry, r.getUUID(), new StAXOMBuilder(reader).getDocumentElement());
					retval.add(ga.getAttribute("details_name"));
				} catch (GovernanceException e) {
				} catch (RegistryException e1) {
				}
			}
		}
		return retval;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void checkForAppplicationExistance() throws MojoFailureException {
		try {
			Map parameters = new HashMap();
			parameters.put("1", MEDIATYPE_APPLICATION); // mediaType
			parameters.put("2", applicationName +"%"); 
			parameters.put("query", sqlFindRegName);
			Resource result = registry.executeQuery(null,parameters);
			String[] paths = (String[]) result.getContent();
			Set<String> appNames = getGenericArtifactsforPaths(registry,paths);
			if (!appNames.contains(applicationName)) {
				throw new MojoFailureException(
						"there is no application called " + applicationName
								+ " Pls. check the setting 'applicationName'");
			}

		} catch (RegistryException e) {
			getLog().error(e.getMessage());
		}
	}

	private Map<String,Service> retrieveMetadata(Set<String> servicePaths) {
		Map<String,Service> retval = new HashMap<String,Service>();
		for (String path : servicePaths) {
			Resource r;
				try {
					String absPath = RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + 
							serviceBasePath + path;
					if (registry.resourceExists(absPath)) {
						r = registry.get(absPath);
						Service ga = (Service) GovernanceUtils.retrieveGovernanceArtifactById(registry,
							r.getUUID());
						retval.put(absPath, ga);
					}
				} catch (RegistryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return retval;
	}
	
	private boolean updateMetadata(Artifact a, String version, Set<String> servicePaths,
			Map<String, Service> oldServices) {
		boolean needsUpdate = false;
		// update metadata
		ServiceManager sm = new ServiceManager(registry);
		for (String path : servicePaths) {
			Resource r;
			try {
				String absPath = RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + 
						serviceBasePath + path;
				r = registry.get(absPath);
				Service ga = (Service) GovernanceUtils.retrieveGovernanceArtifactById(registry,
						r.getUUID());
				if (oldServices.containsKey(absPath)) {
					ga = oldServices.get(absPath);
				}
 		 		String applCode = ga.getAttribute("overview_application");
				if (!applicationName.equals(applCode)) {
					ga.setAttribute("overview_application", applicationName);
					needsUpdate = true;
				}
				if (publishProjectMetadata) {
					String pom = getPom(a, version);
					String pomExisting = ga.getAttribute("dependencyManagement_pom");
					if (!pom.equals(pomExisting)) {
						ga.setAttribute("dependencyManagement_pom", pom);
						needsUpdate = true;
					}
				}
				if (needsUpdate) {
					sm.updateService(ga);
				}
			} catch (RegistryException e) {
				getLog().error(e.getMessage());
			}
		}
		return needsUpdate;
	}
}