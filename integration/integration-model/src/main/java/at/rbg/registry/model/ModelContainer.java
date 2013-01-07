package at.rbg.registry.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.rbg.registry.model.relation.CallRelation;
import at.rbg.registry.model.relation.ContainsRelation;
import at.rbg.registry.model.relation.Stakeholder;
import at.rbg.registry.model.relation.UseRelation;


public class ModelContainer {
	private Set<Application> applications = new HashSet<Application>();
	private Set<Module> modules = new HashSet<Module>();
	private Set<ApplicationService> applicationServices = new HashSet<ApplicationService>(); 	
	private Set<Table> tables = new HashSet<Table>(); 
	private Set<Person> persons = new HashSet<Person>(); 
	private Set<Infrastructure> infrastructure = new HashSet<Infrastructure>();

	private List<UseRelation> uses = new ArrayList<UseRelation>(); 
	private List<CallRelation> calls = new ArrayList<CallRelation>(); 
	private List<ContainsRelation> contains = new ArrayList<ContainsRelation>(); 
	private List<Stakeholder> responsibles = new ArrayList<Stakeholder>(); 
	
	  public Application addApplication(Application application)
	    {
	        applications.add(application);
	        return application;
	    }

	    public List<Application> getApplications()
	    {
	        return new ArrayList<Application>(applications);
	    }

	    public Module addModule(Module module)
	    {
	        modules.add(module);
	        return module;
	    }

	    public List<Module> getModules()
	    {
	        return new ArrayList<Module>(modules);
	    }

	    public ApplicationService addApplicationService(ApplicationService applicationService)
	    {
	        applicationServices.add(applicationService);
	        return applicationService;
	    }

	    public List<ApplicationService> getApplicationServices()
	    {
	        return new ArrayList<ApplicationService>(applicationServices);
	    }

	    public Table addTable(Table table)
	    {
	        tables.add(table);
	        return table;
	    }

	    public List<Table> getTables()
	    {
	        return new ArrayList<Table>(tables);
	    }

	    public Person addPerson(Person person)
	    {
	        persons.add(person);
	        return person;
	    }

	    public List<Person> getPersons()
	    {
	        return new ArrayList<Person>(persons);
	    }

	    public Infrastructure addInfrastructure(Infrastructure infra)
	    {
	    	infrastructure.add(infra);
	        return infra;
	    }

	    public List<Infrastructure> getInfrastructure()
	    {
	        return new ArrayList<Infrastructure>(infrastructure);
	    }

	    
	    public UseRelation addUseRelation(UseRelation useRelation)
	    {
	        uses.add(useRelation);
	        return useRelation;
	    }

	    public List<UseRelation> getUseRelations()
	    {
	        return new ArrayList<UseRelation>(uses);
	    }

	    public CallRelation addCallRelation(CallRelation callRelation)
	    {
	        calls.add(callRelation);
	        return callRelation;
	    }

	    public List<CallRelation> getCallRelations()
	    {
	        return new ArrayList<CallRelation>(calls);
	    }

	    public ContainsRelation addContainsRelation(ContainsRelation containsRelation)
	    {
	        contains.add(containsRelation);
	        return containsRelation;
	    }

	    public List<ContainsRelation> getContainsRelations()
	    {
	        return new ArrayList<ContainsRelation>(contains);
	    }

	    public Stakeholder addStakeholder(Stakeholder responsible)
	    {
	        responsibles.add(responsible);
	        return responsible;
	    }

	    public List<Stakeholder> getStakeholders()
	    {
	        return new ArrayList<Stakeholder>(responsibles);
	    }

}
