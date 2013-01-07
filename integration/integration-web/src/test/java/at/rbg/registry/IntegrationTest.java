package at.rbg.registry;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.generic.GenericArtifactManager;
import org.wso2.carbon.governance.api.generic.dataobjects.GenericArtifact;
import org.wso2.carbon.governance.api.util.GovernanceArtifactConfiguration;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import at.rbg.registry.communication.RegistryFactory;
import at.rbg.registry.communication.RegistryInteraction;
import at.rbg.registry.model.HostProgram;
import at.rbg.registry.model.ModelConstants;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.model.relation.ContainsRelation;


/*
 * Attention: if the remote registry does not exist, the tests are ignored.
 */
public class IntegrationTest extends CamelSpringTestSupport {
	private static final Logger log = LoggerFactory.getLogger(IntegrationTest.class);
	private RegistryFactory rf;
	
	@Override
	protected AbstractApplicationContext createApplicationContext()
	{
		return new ClassPathXmlApplicationContext("spring-camel.xml");
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.rf = (RegistryFactory) this.applicationContext.getBean("registryFactory");
		rf.init();
	}
	
	/**
	 * checks if content based router goes to SOAP backend
	 */
	@Test
	public void testRrouting() throws Exception
	{
		if (rf.getRemoteRegistryInstance()!=null) {
			GenericArtifactManager gam = null;
			GenericArtifactManager mm = null;
			GenericArtifact mod = null;
			GenericArtifact fa = null;
			try {
				gam = rf.getManagerForArtifactType(ModelConstants.APPLICATION_ARTIFACT); 
				fa  = gam.newGovernanceArtifact(new QName("Test"));
				fa.setAttribute("details_version", "1.0.0");
	 		 	gam.addGenericArtifact(fa); 
	
				mm = rf.getManagerForArtifactType(ModelConstants.MODULE_ARTIFACT);
				mod  = mm.newGovernanceArtifact(new QName("Test"));
			 	mod.setAttribute("details_application", "DEMO"); 
				mod.setAttribute("details_version", "1.0.0");  
				mm.addGenericArtifact(mod);
				log.info("artifacts added");

				fa.addAttribute("contains_entry", "Modul:" +  getArtifactPath("Test","1.0.0", "DEMO", ModelConstants.MODULE_ARTIFACT));
				gam.updateGenericArtifact(fa);   
			} catch (Exception e) {
				log.info(e.getMessage());		 		
			} finally {
				String path = GovernanceUtils.getPathFromPathExpression(rf.getConfigForArtifactType(ModelConstants.APPLICATION_ARTIFACT).getPathExpression(), fa);
				rf.getRemoteRegistryInstance().delete(path);
				path = GovernanceUtils.getPathFromPathExpression(rf.getConfigForArtifactType(ModelConstants.MODULE_ARTIFACT).getPathExpression(), mod);
				rf.getRemoteRegistryInstance().delete(path);
			 	log.info("artifacts removed");							
			}
		}
	}

	@Test
	public void testMatchSting() {
		if (rf.getRemoteRegistryInstance()!=null) {
			HostProgram hp = new HostProgram();
			hp.setName("TEST");
			hp.setVersion("1.0.0");
			String s = getMatchString(hp);
			assertTrue("/_system/governance/custom/hostprograms/DEMO/TEST".equals(s));		
		}
	}
	
	@Test
	public void testQuery()  throws Exception
	{
		if (rf.getRemoteRegistryInstance()!=null) {
			GenericArtifactManager gam = rf.getManagerForArtifactType(ModelConstants.APPLICATION_ARTIFACT);
			GenericArtifactManager gam2 = rf.getManagerForArtifactType(ModelConstants.MODULE_ARTIFACT);
			GenericArtifact fa = null;
			GenericArtifact fa2 = null;
			try {
				fa = gam.newGovernanceArtifact(new QName("Org-App"));
				fa.setAttribute("details_version", "1.0.0");
				fa.setAttribute("details_longname", "DEMO");
				gam.addGenericArtifact(fa);
				
				fa2 = gam2.newGovernanceArtifact(new QName("Org-Modul"));
				fa2.setAttribute("details_application", "DEMO");
				fa2.setAttribute("details_version", "1.0.3");
				gam2.addGenericArtifact(fa2);
				
				String srcPath = getArtifactPath("Org-App", "1.0.0", "DEMO", ModelConstants.APPLICATION_ARTIFACT);
				String destPath = getArtifactPath("Org-Modul", "1.0.3", "DEMO", ModelConstants.MODULE_ARTIFACT);
				rf.getRemoteRegistryInstance().addAssociation( srcPath, destPath, ContainsRelation.RELATION_NAME);
				
				RegistryInteraction ri = (RegistryInteraction) this.applicationContext.getBean("registryInteraction");
				// reading is not chrooted, hnece the RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH
				List<String> paths = ri.getAssociationDestinationPaths(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + srcPath, ContainsRelation.RELATION_NAME);
				assertTrue("Pfad nichtgefunden, obwohl grad angelegt!",paths.size()==1);
			} catch (GovernanceException e) {
			 	log.info(e.getMessage());							
			} catch (RegistryException e) {
			 	log.info(e.getMessage());							
			} finally {
					String path = GovernanceUtils.getPathFromPathExpression(rf.getConfigForArtifactType(ModelConstants.APPLICATION_ARTIFACT).getPathExpression(), fa);
					rf.getRemoteRegistryInstance().delete(path);
					path = GovernanceUtils.getPathFromPathExpression(rf.getConfigForArtifactType(ModelConstants.MODULE_ARTIFACT).getPathExpression(), fa2);
					rf.getRemoteRegistryInstance().delete(path);
				 	log.info("artifacts removed");							
			}
		}
	}
	
	@Test
	public void testCustomQuery() throws RegistryException
	{
		if (rf.getRemoteRegistryInstance()!=null) {
//			String mediaType = "application/vnd.wso2-application+xml";
//			RegistryInteraction ri = (RegistryInteraction) this.applicationContext.getBean("registryInteraction");
//			List<String> paths = ri.findArtifactPaths(mediaType, "%", "%");
//			paths.size(); 
		}
	}
	
	 @Test
	 public void testQueryProcessor() throws Exception
	 {
//			RegistryFactory rf = (RegistryFactory) this.applicationContext.getBean("registryFactory");
//			context.startRoute("queryRegistryRoute");
//		 	DefaultExchange exch = new DefaultExchange(context);
//		 	Message in = new DefaultMessage();
//		 	QueryRequest body = new QueryRequest("SMS", "UNKNOWN", rf.getConfigForArtifactType(ModelConstants.APPLICATION_ARTIFACT).getMediaType());
//		 	in.setBody(body);
//		 	exch.setIn(in);
//			Exchange result = this.template.send("direct:queryRegistry", exch);
//		 	if (result.isFailed() || (result.getOut() == null)) {
//				throw new IllegalStateException("something went wrong: result is wrong!");
//			}
//		 	context.stopRoute("queryRegistryRoute");

	 }
	
	private String getMatchString(RBGGovernanceArtifact ga) {
		GovernanceArtifactConfiguration cf = rf.getConfigForArtifactType(ga.getRegistryType());
		String path =  RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + cf.getPathExpression();
		path = path.replace("@{details_name}", ga.getName());
		path = path.replace("@{details_version}", ga.getVersion());
		path = path.replace("@{details_application}", "DEMO");
		return path;
	}

	private String getArtifactPath(String name, String version, String appl, String regtype) {
		GovernanceArtifactConfiguration cf = rf.getConfigForArtifactType(regtype);
		String path =  cf.getPathExpression();
		path = path.replace("@{" + cf.getArtifactNameAttribute() + "}", name);
		path = path.replace("@{details_version}", version);
		path = path.replace("@{details_application}", appl);
		return path;
	}

}
