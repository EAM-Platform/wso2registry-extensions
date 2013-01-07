package at.rbg.registry.communication.query;

import java.util.List;

import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.model.Key;

/**
 * this class contains the request for registry.
 * 
 */ 
public class QueryRequest { 
	private ModelComposite appl;
	private ModelComposite module;
	private ModelComposite appService;
	private List<Key> domains;
	private Key role;
	private Key tier;	
	
	public Key getRole() {
		return role;
	}
	public void setRole(Key role) {
		this.role = role;
	}
	public ModelComposite getAppl() {
		return appl;
	}
	public void setAppl(ModelComposite appl) {
		this.appl = appl;
	}
	public ModelComposite getModule() {
		return module;
	}
	public void setModule(ModelComposite module) {
		this.module = module;
	}
	public ModelComposite getAppService() {
		return appService;
	}
	public void setAppService(ModelComposite appService) {
		this.appService = appService;
	}
	public List<Key> getDomains() {
		return domains;
	}
	public void setDomains(List<Key> domains) {
		this.domains = domains;
	}
	public Key getTier() {
		return tier;
	}
	public void setTier(Key tier) {
		this.tier = tier;
	}
	
	
	
}
