package at.rbg.registry.integration.service.adapter;

import java.util.ArrayList;
import java.util.List;

import at.rbg.registry.model.Key;
import at.rbg.registry.persistance.dao.KeyCodeDao;



public class KeyAdapter {
	private static KeyCodeDao keyCodeDao;

	public static void setKeyCodeDao(KeyCodeDao keyCodeDao) {
		KeyAdapter.keyCodeDao = keyCodeDao;
	}

	public static Key adapt(at.grz.jp.datatypes_2.Key key) {	
		Key k = new Key();
		k.setCode(key.getCode());
		k.setDescription(key.getDescription());
		k.setName(key.getName());
		k.setType(key.getType());
		return k;
	}

	public static List<Key> adapt( at.grz.jp.datatypes_2.KeyList domains) {
		List<Key> retval = new ArrayList<Key>();
		
		for (at.grz.jp.datatypes_2.Key domain: domains.getKey()) {
			Key k = new Key();
			k.setCode(domain.getCode());
			k.setDescription(domain.getDescription());
			k.setName(domain.getName());
			k.setType(domain.getType());
			retval.add(k);
		}		
		return retval;
	}
	
	public static at.grz.jp.datatypes_2.Key adapt (String keyName, String code) {
		String value = keyCodeDao.resolveKeyCode(keyName, code);
		 at.grz.jp.datatypes_2.Key key = new  at.grz.jp.datatypes_2.Key();
		 key.setCode(code);
		 key.setName(value);
		 
		 return key;
	}
}
