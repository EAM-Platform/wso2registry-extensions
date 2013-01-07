package at.rbg.registry.integration.service.adapter;

import java.util.HashMap;
import java.util.Map;

import at.rbg.registry.model.Application;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.Person;
import at.rbg.registry.model.RBGGovernanceArtifact;

/** 
 * this container is needed in order to adapt from the DependencyQuery web
 * service to the internal model. As we use a nodes and edges model internally,
 * the composite is necessary. Also the problem that arises from multiple
 * versions of artifacts is, that there is NO 1:n relation but potentially all
 * relations are n:m !
 * 
 */ 
public class ModelComposite {

	private Map<RBGGovernanceArtifact,Person> ownerships = new HashMap<RBGGovernanceArtifact,Person>();
	private Application application;
	private Module module;
	private ApplicationService applicationService;

	public Person getOwner(RBGGovernanceArtifact appl) {
		if (ownerships.containsKey(appl)) {
			return ownerships.get(appl);
		}
		return null;
	}

	public void addOwner(RBGGovernanceArtifact artifact, Person owner) {
		ownerships.put(artifact, owner);
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
