package at.rbg.registry.integration.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.grz.jp.datatypes_2.Key;
import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Person;
import at.rbg.registry.model.validation.KeyTypes;

public class ApplicationServiceAdapter {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ApplicationServiceAdapter.class);

	public static ModelComposite adapt(at.racon.eamp.eamp_types_1.ApplicationService as) {
		ModelComposite ac = new ModelComposite();

		ApplicationService res = new ApplicationService();
		res.setDescription(as.getDescription());
		res.setName(as.getName());
		res.setVersion(as.getVersion());
		res.setPath(as.getPath());
		if (as.getCreated()!=null) {
			res.setCreated(as.getCreated().toGregorianCalendar());
		}
		if (as.getUpdated()!=null) {
			res.setUpdated(as.getUpdated().toGregorianCalendar());
		}
		res.setIdentifier(as.getIdentifier());
	
		ac.setApplicationService(res);
		
		// unwrap the module into the composite
		if (as.getModule()!=null) {
			ModelComposite acAppl =  ModuleAdapter.adapt(as.getModule());
			ac.setModule(acAppl.getModule());
			if (acAppl.getOwner(acAppl.getModule())!=null) {
				ac.addOwner(acAppl.getModule(), acAppl.getOwner(acAppl.getModule()));
			}
			if (acAppl.getApplication()!=null) {
				ac.setApplication(acAppl.getApplication());
				if (acAppl.getOwner(acAppl.getApplication()) != null) {
					ac.addOwner(acAppl.getApplication(),
							acAppl.getOwner(acAppl.getApplication()));
				}
			}
		}

		if (as.getOwner()!=null) {
			Person owner = new Person(as.getOwner());
			ac.addOwner(res,owner);
		}
	
		return ac;
	}

	/**
	 * resolving EAMP002 is weird... we made an extra key in svzlist.properties for that!
	 */
	public static at.racon.eamp.eamp_types_1.ApplicationService adapt(ApplicationService as) {
		at.racon.eamp.eamp_types_1.ApplicationService retval = new at.racon.eamp.eamp_types_1.ApplicationService();
		if (as!=null) {
			retval.setDescription(as.getDescription());
			retval.setIdentifier(as.getIdentifier());
			retval.setVersion(as.getVersion());
			retval.setName(as.getName());
			retval.setPath(as.getPath());
			// we get the numeric codes first.
			Key keyIntermediate = KeyAdapter.adapt(KeyTypes.APPLICATIONSERVICE_KEYTYPE_REVERSE, as.getRegistryType());
			// we transform to registry key-model again
			at.rbg.registry.model.Key keyTemp = KeyAdapter.adapt(keyIntermediate);
			Key key = KeyAdapter.adapt(KeyTypes.APPLICATIONSERVICE_KEYTYPE,keyTemp.getName());
			retval.setType(key);
			
			if (as.getCreated()!=null) {
				retval.setCreated(CalendarAdapter.adapt(as.getCreated()));			
			}
			if (as.getUpdated()!=null) {
				retval.setUpdated(CalendarAdapter.adapt(as.getUpdated()));						
			}
		}
		return retval;
	}

}
