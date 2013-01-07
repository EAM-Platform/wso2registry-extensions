package at.rbg.registry.persistance.dao;

import java.util.AbstractMap;
import java.util.List;


/**
	access to keycodes
 */
public interface KeyCodeDao
{
	/**
	 * Prueft, ob SVZ-Code fuer den Schluessel erlaubt ist.
	 * 
	 * @param keyNameCode
	 * @param keyCodeCode
	 * @return true|false
	 */
	Boolean isKeyCodeValid(String keyType, String keyCode);
	
	public String resolveKeyCode(String keyType, String keyCode);
	
	/**
	 * get code for value (i.e. reverse lookup!)
	 * @param keyName
	 * @param keyValue
	 * @return
	 */
	public String resolveKeyValue(String keyType, String keyValue);
	
	@SuppressWarnings("rawtypes")
	public List<AbstractMap.SimpleEntry> getKeyCodesForKeyName(String keyType);

}
