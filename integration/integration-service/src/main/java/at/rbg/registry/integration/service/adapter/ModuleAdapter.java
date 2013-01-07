package at.rbg.registry.integration.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.grz.jp.datatypes_2.Key;
import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.Person;
import at.rbg.registry.model.validation.KeyTypes;

public class ModuleAdapter {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ModuleAdapter.class);

	public static ModelComposite adapt(at.racon.eamp.eamp_types_1.Module mod) {
		ModelComposite ac = new ModelComposite();

		Module res = new Module();
		res.setDescription(mod.getDescription());
		res.setName(mod.getName());
		res.setVersion(mod.getVersion());
		res.setPath(mod.getPath());
		if (mod.getTier()!=null) {
			res.setTier(mod.getTier().getCode());
		}
		if (mod.getCreated()!=null) {
			res.setCreated(mod.getCreated().toGregorianCalendar());
		}
		if (mod.getUpdated()!=null) {
			res.setUpdated(mod.getUpdated().toGregorianCalendar());
		}
		res.setIdentifier(mod.getIdentifier());
		ac.setModule(res);
		
		// unwrap the application into the composite
		if (mod.getApplication()!=null) {
			ModelComposite acAppl =  ApplicationAdapter.adapt(mod.getApplication());
			ac.setApplication(acAppl.getApplication());
			if (acAppl.getOwner(acAppl.getApplication())!=null) {
				ac.addOwner(acAppl.getApplication(), acAppl.getOwner(acAppl.getApplication()));
			}
		}

		if (mod.getOwner()!=null) {
			Person owner = new Person(mod.getOwner());
			ac.addOwner(res,owner);
		}

		return ac;
	}

	public static at.racon.eamp.eamp_types_1.Module adapt(Module mod) {
		at.racon.eamp.eamp_types_1.Module retval = new at.racon.eamp.eamp_types_1.Module();
		if (mod != null) {
			retval.setDescription(mod.getDescription());
			retval.setIdentifier(mod.getIdentifier());
			retval.setName(mod.getName());
			retval.setPath(mod.getPath());
			retval.setVersion(mod.getVersion());
			Key key = KeyAdapter.adapt(KeyTypes.TIER_KEYTYPE, mod.getTier());
			retval.setTier(key);
				
			if (mod.getCreated()!=null) {
				retval.setCreated(CalendarAdapter.adapt(mod.getCreated()));			
			}
			if (mod.getUpdated()!=null) { 
				retval.setUpdated(CalendarAdapter.adapt(mod.getUpdated()));						
			}
		}
		return retval;
	}
}
