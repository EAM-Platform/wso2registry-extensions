package at.rbg.registry.integration.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.grz.jp.datatypes_2.Key;
import at.rbg.registry.model.Table;
import at.rbg.registry.model.validation.KeyTypes;

public class TableAdapter {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TableAdapter.class);

	public static at.racon.eamp.eamp_types_1.ApplicationService adapt(Table tab) {
		at.racon.eamp.eamp_types_1.ApplicationService retval = new at.racon.eamp.eamp_types_1.ApplicationService();
		if (tab!=null) {
			retval.setDescription(tab.getDescription());
			retval.setIdentifier(tab.getIdentifier());
			retval.setVersion(tab.getVersion());
			retval.setName(tab.getName());
			retval.setPath(tab.getPath());	
			// we get the numeric codes first.
			Key keyIntermediate = KeyAdapter.adapt(KeyTypes.APPLICATIONSERVICE_KEYTYPE_REVERSE, tab.getRegistryType());
			// we transform to registry key-model again
			at.rbg.registry.model.Key keyTemp = KeyAdapter.adapt(keyIntermediate);
			Key key = KeyAdapter.adapt(KeyTypes.APPLICATIONSERVICE_KEYTYPE,keyTemp.getName());
			retval.setType(key);

			
			if (tab.getCreated()!=null) {
				retval.setCreated(CalendarAdapter.adapt(tab.getCreated()));			
			}
			if (tab.getUpdated()!=null) {
				retval.setUpdated(CalendarAdapter.adapt(tab.getUpdated()));						
			}		
		}
		return retval;
	}
}
