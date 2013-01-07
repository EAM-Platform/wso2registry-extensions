package at.rbg.registry.integration.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.grz.jp.datatypes_2.Key;
import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.model.Application;
import at.rbg.registry.model.Person;
import at.rbg.registry.model.validation.KeyTypes;

public class ApplicationAdapter {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ApplicationAdapter.class);
	
	public static ModelComposite adapt(at.racon.eamp.eamp_types_1.Application app) {
		ModelComposite ac = new ModelComposite();
		
		Application res = new Application();
		res.setDescription(app.getDescription());
		res.setName(app.getName());
		res.setVersion(app.getVersion());
		res.setPath(app.getPath());
		if (app.getCreated()!=null) {
			res.setCreated(app.getCreated().toGregorianCalendar());
		}
		if (app.getUpdated()!=null) {
			res.setUpdated(app.getUpdated().toGregorianCalendar());
		}
		res.setIdentifier(app.getIdentifier());
		
		if (app.getDomain()!=null) {
			res.setDomain(app.getDomain().getCode());
		}
		ac.setApplication(res);
		
		if (app.getOwner()!=null) {
			Person owner = new Person(app.getOwner());
			ac.addOwner(res,owner);
		}		
		return ac;
	}
	
	public static at.racon.eamp.eamp_types_1.Application adapt(Application app) {
		at.racon.eamp.eamp_types_1.Application retval = new at.racon.eamp.eamp_types_1.Application();
		if (app!=null) {
			retval.setDescription(app.getDescription());
			retval.setIdentifier(app.getIdentifier());
			retval.setName(app.getName());
			retval.setPath(app.getPath());
			retval.setVersion(app.getVersion());
			Key key = KeyAdapter.adapt(KeyTypes.DOMAIN_KEYTYPE, app.getDomain());
			retval.setDomain(key);
			
			if (app.getCreated()!=null) {
				retval.setCreated(CalendarAdapter.adapt(app.getCreated()));			
			}
			if (app.getUpdated()!=null) {
				retval.setUpdated(CalendarAdapter.adapt(app.getUpdated()));						
			}
		// TODO: handle owner stuff
		}
		return retval;
	}
	

}
