package at.rbg.registry.processors;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.generic.GenericArtifactManager;
import org.wso2.carbon.governance.api.generic.dataobjects.GenericArtifact;
import org.wso2.carbon.governance.api.util.GovernanceArtifactConfiguration;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.governance.custom.lifecycles.checklist.stub.CustomLifecyclesChecklistAdminServiceExceptionException;
import org.wso2.carbon.governance.custom.lifecycles.checklist.stub.CustomLifecyclesChecklistAdminServiceStub;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import at.rbg.registry.communication.RegistryFactory;
import at.rbg.registry.model.Application;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.HostProgram;
import at.rbg.registry.model.HostTraco;
import at.rbg.registry.model.Infrastructure;
import at.rbg.registry.model.LifecycleState;
import at.rbg.registry.model.ModelContainer;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.model.Table;
import at.rbg.registry.model.relation.BaseRelation;
import at.rbg.registry.model.relation.CallRelation;
import at.rbg.registry.model.relation.UseRelation;

public class RegistryWritingProcessor implements Processor {
	private static final Logger log = LoggerFactory.getLogger(RegistryWritingProcessor.class);
	private RegistryFactory registryFactory;
	private static final String DATETIME_FORMAT_HOST = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public void setRegistryFactory(RegistryFactory registryFactory) {
		this.registryFactory = registryFactory;
	}

	@Override
	public void process(Exchange ex) throws Exception {
		registryFactory.checkAlive();
		
		ModelContainer mc = (ModelContainer) ex.getIn().getBody();
		processModelContainer(mc);
		
		log.info("ModelContainer was processed.");
	}
	
	private void processModelContainer(ModelContainer mc) {
		Map<RBGGovernanceArtifact, GovernanceArtifact> rbgToGen = new HashMap<RBGGovernanceArtifact, GovernanceArtifact>();
		Set<RBGGovernanceArtifact> toBeUpated = new HashSet<RBGGovernanceArtifact>();
		
		// create Artifacts: apps, modules, appservices
		for (Application app : mc.getApplications()) {
			GovernanceArtifact ga = addArtifact(app);
			rbgToGen.put(app, ga);
		}		
		for (Module module : mc.getModules()) {
			GovernanceArtifact ga = addArtifact(module);
			rbgToGen.put(module, ga);
		}	
		for (ApplicationService as : mc.getApplicationServices()) {
			if (as instanceof HostProgram) {
				HostProgram hp = (HostProgram) as;
				GovernanceArtifact ga = addArtifact(hp);
				rbgToGen.put(as, ga);
			}
		}
		for (Table table : mc.getTables()) {
			GovernanceArtifact ga = addArtifact(table);
			rbgToGen.put(table, ga);
		}	

		for (Infrastructure infra : mc.getInfrastructure()) {
			GovernanceArtifact ga = addArtifact(infra);
			rbgToGen.put(infra, ga);
		}	

		// create Links
		for (UseRelation ur : mc.getUseRelations()) {
			GovernanceArtifact ga = rbgToGen.get(ur.getSource());
			boolean needsUpdate = createLink(ur, ga);
			if (needsUpdate) {
				toBeUpated.add(ur.getSource());
			} // CHECK FOR CHANGED VRSION!!
		}	
		for (CallRelation cr : mc.getCallRelations()) {
			GovernanceArtifact ga = rbgToGen.get(cr.getSource());
			boolean needsUpdate = createLink(cr, ga);
			if (needsUpdate) {
				toBeUpated.add(cr.getSource());
			}
		}

		// update those, which really need it...
		List<RBGGovernanceArtifact> upds = new ArrayList<RBGGovernanceArtifact>(toBeUpated);
		for (RBGGovernanceArtifact ra : upds) {
			GenericArtifact ga = (GenericArtifact) rbgToGen.get(ra);
			GenericArtifactManager gam = registryFactory.getManagerForArtifactType(ra.getRegistryType());
			try {
				gam.updateGenericArtifact(ga);
			} catch (GovernanceException e) {
				log.error("link creation problem addGenericArtifact " + e.getMessage());
			}
		}
	}
	
	private boolean createLink(BaseRelation br, GovernanceArtifact src) {
		boolean needsUpdate = false;
		String linkPath = "";
		String uiRelationName = br.getUIRelationName();

		if (br.getDestination() instanceof HostProgram) {
			 linkPath =  HostProgram.UI_NAME + ":" +  getArtifactPath(br.getDestination());		
		}
		if (br.getDestination() instanceof Table) {
			 linkPath =  Table.UI_NAME + ":" +  getArtifactPath(br.getDestination());		
		}
		if (br.getDestination() instanceof Infrastructure) {
			 linkPath =  Infrastructure.UI_NAME + ":" +  getArtifactPath(br.getDestination());		
		}
		if (!checkLinkPathExists(linkPath, uiRelationName, src)) {
			needsUpdate = true;
			String[] links = updateLinkPath(linkPath, uiRelationName , src);
			try {
				src.setAttributes(uiRelationName, links);
			} catch (GovernanceException e) {
				log.error("link creation problem setAttribute " + e.getMessage());
			}
		}
		return needsUpdate;
	}
	
	private boolean checkLinkPathExists(String linkPath, String linkAttributeName, GovernanceArtifact ga) {
		boolean found = false;
		try {
			String[] links = ga.getAttributes(linkAttributeName);
			if (links !=null) {
				for (String link : links) {
					if (link.equals(linkPath)) {
						found = true;
					}
				}
			}
		} catch (GovernanceException e) {
			log.error("checkAndUpdateLinkPath " + e.getMessage());
		}
		return found;
	}
	
	private String[] updateLinkPath(String linkPath, String linkAttributeName, GovernanceArtifact ga) {
		String[] links = {};
		List<String> result = new ArrayList<String>();
		try {
			links = ga.getAttributes(linkAttributeName);
			if (links!=null) {
				for (String link : links) {
					result.add(link);
				}
			}
			result.add(linkPath);			
		} catch (GovernanceException e) {
			log.error("checkAndUpdateLinkPath " + e.getMessage());
		}
		return result.toArray(new String[0]);
	}
	
	/**
	 * Artifact is either retrieved or prepared for later addition to registry
	 * @param ga
	 * @return
	 */
	private GovernanceArtifact addArtifact(RBGGovernanceArtifact ga) {
		boolean checkChanged = false;
		boolean isNewer = false;
		GenericArtifactManager gam = registryFactory.getManagerForArtifactType(ga.getRegistryType());
		GenericArtifact fa = (GenericArtifact) findMatchingArtifact(ga);
		log.info("working on: " + ga.getName());
		try {
			if (fa != null) {
				isNewer  = isVersionNewer(ga.getVersion(), fa.getAttribute("details_version"));
				checkChanged = updateAttributes(ga,fa);
			}
			else {
				fa = gam.newGovernanceArtifact(new QName(ga.getName())); // artifactname
				fa.setAttribute("details_version", ga.getVersion());
				updateAttributes(ga,fa);		
				gam.addGenericArtifact(fa);
				handleLifeCycle(ga, fa);
			}
			if (isNewer) {
				if (checkChanged) {
					gam.updateGenericArtifact(fa);
					log.info("updated: " + ga.getName());
				}
				handleLifeCycle(ga, fa);
			}
		} catch (GovernanceException e) {
			log.error(e.getMessage());
		} catch (RegistryException e) {
			log.error(e.getMessage());
		} 
		
		return fa;
	}
	
	/**
	 * set lifecycle according to handed over state
	 * TODO: rework this, when changing the lifecycle because this depends on the transition NAMES!
	 * @param ga
	 * @param fa
	 * @throws RegistryException
	 */
	private void handleLifeCycle(RBGGovernanceArtifact ga, GenericArtifact fa) throws RegistryException {
		String path = getArtifactPath(ga);			
		
		String lName = ga.getLifecycleName();
		String oldLName = fa.getLifecycleName();
		
		String lState = null;
		if (ga.getLifecycleState()!=null) {
			lState = ga.getLifecycleState().toString();
		}
		String oldLState =  fa.getLifecycleState();
		
		boolean freshLifecycle = false;
		// check, if lifecycleNames are different
		// attach lifecycle, if different
		if ((lName != null && !lName.equals(oldLName) || (lName == null && oldLName!=null))) {
			// this puts us to DESIGN (initial) state
			fa.attachLifecycle(ga.getLifecycleName());
			freshLifecycle = true;
		}
		
		// state is possibly different at this point
		if (lName != null) {
			// if state is null and lifecyclename is set, we ignore that!
			if (lState!=null && !lState.equals(oldLState)) {
				// we have to reset lifecycle, to start from a clean state.
				if (!freshLifecycle) {
					fa.attachLifecycle(null);
					fa.attachLifecycle(lName);
				}
				try {
					CustomLifecyclesChecklistAdminServiceStub clc = registryFactory.getCustomLifecyclesChecklistAdminServiceStub();
					if (LifecycleState.PRODUCTION.equals(ga.getLifecycleState())) {
						// {2.item=false, 0.item=false, 1.item=true} => it looks like this in debugger EmbeddedRegistry.invokeAspect()!
						// these are representing the check boxes in the UI
						String[] params = { "false", "true", "false" };			
						clc.invokeAspect(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + path, ga.getLifecycleName(), "zum Produktionsstatus", params);
					}
					else if (LifecycleState.DEVELOPMENT.equals(ga.getLifecycleState())) {
						String[] params = { "true", "false", "false" };			
						clc.invokeAspect(RegistryConstants.GOVERNANCE_REGISTRY_BASE_PATH + path, ga.getLifecycleName(), "zur Entwicklung", params);
					}
					
				} catch (RemoteException e) {
					log.error(e.getMessage());
				} catch (CustomLifecyclesChecklistAdminServiceExceptionException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	
	
	/**
	 * updates attributes which are not part of the unique key
	 * 
	 * @param ga
	 * @param fa
	 * @return
	 */
	private boolean updateAttributes(RBGGovernanceArtifact ga, GovernanceArtifact fa) {
		boolean isChanged = false;
		// registry does not like umlauts
		isChanged |= checkChanged(fa, "details_version",ga.getVersion());
		isChanged |= checkChanged(fa, "details_description",ga.getDescription());
		isChanged |= checkChanged(fa, "details_application",ga.getApplCode());
		
		if (HostProgram.class.isInstance(ga) ) {
			isChanged |= checkChanged(fa, "details_accesstype",((HostProgram) ga).getAccesstype());
			isChanged |= checkChanged(fa, "details_technology",((HostProgram) ga).getTechnology());
			isChanged |= checkChanged(fa, "details_interfaceType",((HostProgram) ga).getInterfaceType());
			isChanged |= checkChanged(fa, "details_release",((HostProgram) ga).getRelease());
		}
		else if (HostTraco.class.isInstance(ga) ) {
			isChanged |= checkChanged(fa, "details_dataformat",((HostTraco) ga).getDataformat());			
		}
		else if (Infrastructure.class.isInstance(ga) ) {
			isChanged |= checkChanged(fa, "details_componentnumber",((Infrastructure) ga).getComponentnumber());			
			isChanged |= checkChanged(fa, "details_category",((Infrastructure) ga).getCategory());			
			isChanged |= checkChanged(fa, "details_subcategory",((Infrastructure) ga).getSubcategory());			
			isChanged |= checkChanged(fa, "details_status",((Infrastructure) ga).getStatus());			
		}
		return isChanged;
	}
	
	private boolean checkChanged(GovernanceArtifact fa, String attrName, String attrValue) {
		boolean isChanged = true;
		try {
			String curval = fa.getAttribute(attrName);
			if ((curval == null || curval.isEmpty()) && (attrValue==null || attrValue.isEmpty())) {
				isChanged = false;
			}
			else if (attrValue!=null && curval !=null && curval.equals(attrValue)) {
				isChanged = false;				
			}
			else {
				fa.setAttribute(attrName,attrValue);
			}
		} catch (GovernanceException e) {
			log.error("updateAttributes " + e.getMessage());
		}	
		return isChanged;
	}
	
	private GovernanceArtifact findMatchingArtifact(RBGGovernanceArtifact ga) {
		GovernanceArtifact ret = null;
		String matcher = getArtifactPath(ga);
		try {
			if (registryFactory.getRemoteRegistryInstance().resourceExists(matcher)) { 
				ret = GovernanceUtils.retrieveGovernanceArtifactByPath(registryFactory.getRemoteRegistryInstance(), matcher);
			}
		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
		return ret;
	}
	
	private String getArtifactPath(RBGGovernanceArtifact ga) {
		GovernanceArtifactConfiguration cf = registryFactory.getConfigForArtifactType(ga.getRegistryType());
		String path =  cf.getPathExpression();
		path = path.replace("@{" + cf.getArtifactNameAttribute() + "}", ga.getName());
		// TODO: extract hardcoded details_version attribute-name
		path = path.replace("@{details_version}", ga.getVersion());
		if (ga.getApplCode()!=null) {
			path = path.replace("@{details_application}", ga.getApplCode());
		}
		if (ga instanceof Infrastructure && ((Infrastructure)ga).getComponentnumber() !=null) {
			path = path.replace("@{details_componentnumber}", ((Infrastructure)ga).getComponentnumber());
		}
		return path;
	}
	
	private DateTime tryDateFormat(String dateTime) {
		if (dateTime!=null) {
			DateTimeFormatter fmt = DateTimeFormat.forPattern(DATETIME_FORMAT_HOST);
			try {
				return fmt.parseDateTime(dateTime);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	private boolean isVersionNewer(String version, String oldVersion) {
		// if we cannot estimate the relation between versions, we accept new records over the old ones.
		boolean retval = true;
		DateTime dt= tryDateFormat(version);
		DateTime dtOld= tryDateFormat(oldVersion);
		if (dt!=null && dtOld !=null) {
			if (dtOld.isAfter(dt.getMillis()) || dtOld.isEqual(dt.getMillis())) {
				retval = false;
			}
		}
		
		return retval;
	}

}
