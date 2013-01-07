package at.rbg.registry.persistance.dao.internal;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import at.rbg.registry.persistance.dao.KeyCodeDao;

/**
 * the structure of the used property file is keygroupname.keycode=keyvalue e.g.
 * gender.male=maennlich
 */
public class KeyCodePropertyDao implements KeyCodeDao {
	private Properties properties;
	private Map<String, HashMap<String,String>> keyManager = new HashMap<String, HashMap<String,String>>();

	@SuppressWarnings("rawtypes")
	public void setProperties(Properties properties) {
		this.properties = properties;

		for (Enumeration en = this.properties.keys(); en.hasMoreElements();) {
			String propertyName = (String) en.nextElement();
			int pos = propertyName.indexOf(".");
			String keyName = propertyName.substring(0, pos);
			HashMap<String,String> keyGroup = new HashMap<String,String>();
			if (keyManager.containsKey(keyName)) {
				keyGroup = keyManager.get(keyName);
			}
			else {
				keyManager.put(keyName,keyGroup);
			}
			String keyCode = propertyName.substring(pos+1);

			if (!keyGroup.containsKey(keyCode)) {
				keyGroup.put(keyCode, this.properties.getProperty(propertyName));
			}
		}
	}

	@Override
	public Boolean isKeyCodeValid(String keyType, String keyCode) {
		if (keyManager.containsKey(keyType)) {
			Map<String,String> keyGroup = keyManager.get(keyType);
			if (keyGroup.containsKey(keyCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String resolveKeyCode(String keyType, String keyCode) {
		if (keyManager.containsKey(keyType)) {
			Map<String,String> keyGroup = keyManager.get(keyType);
			if (keyGroup.containsKey(keyCode)) {
				return keyGroup.get(keyCode);
			}
		}
		return null;
	}
	
	@Override
	public String resolveKeyValue(String keyType, String keyValue) {
		if (keyManager.containsKey(keyType)) {
			Map<String,String> keyGroup = keyManager.get(keyType);
			if (keyGroup.containsValue(keyValue)) {
				for (String key : keyGroup.keySet()) {
					String value = keyGroup.get(key);
					if (keyValue==null && value == null) {
						return key;
					}
					else if (value.equals(keyValue)) {
						return key;
					}
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SimpleEntry> getKeyCodesForKeyName(String keyType) {
		List<SimpleEntry> retval = new ArrayList<SimpleEntry>();
		if (keyManager.containsKey(keyType)) {
			Map<String,String> keyGroup = keyManager.get(keyType);
			Set<String> keys = keyGroup.keySet();
			for (String key : keys) {
				SimpleEntry en = new SimpleEntry(key, keyGroup.get(key));
				retval.add(en);
			}
		}
		return retval;
	}


}
