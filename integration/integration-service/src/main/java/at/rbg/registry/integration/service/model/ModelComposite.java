package at.rbg.registry.integration.service.model;

import java.util.HashMap;
import java.util.Map;

import at.rbg.registry.model.Application;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.Person;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.model.Table;

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
	private Table table;

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
	
    public Table getTable()
    {
        return table;
    }

    public void setTable(Table table)
    {
        this.table = table;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime
				* result
				+ ((applicationService == null) ? 0 : applicationService
						.hashCode());
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelComposite other = (ModelComposite) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (applicationService == null) {
			if (other.applicationService != null)
				return false;
		} else if (!applicationService.equals(other.applicationService))
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

}
