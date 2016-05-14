/**
 * 
 */
package com.sto.rms.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Siva
 *
 */
public class MenuConfiguration 
{
	private static Map<String, String> MENU_URL_PATTERN_MAP = new HashMap<>();
	
	static
	{
		MENU_URL_PATTERN_MAP.put("/home", "Home");
		MENU_URL_PATTERN_MAP.put("/users", "Users");
		MENU_URL_PATTERN_MAP.put("/roles", "Roles");
		MENU_URL_PATTERN_MAP.put("/permissions", "Permissions");
		MENU_URL_PATTERN_MAP.put("/schedule", "Schedule");
		
	}
	
	public static Map<String, String> getMenuUrlPatternMap() {
		return Collections.unmodifiableMap(MENU_URL_PATTERN_MAP);
	}

	public static String getMatchingMenu(String uri) {
		Set<String> keySet = MENU_URL_PATTERN_MAP.keySet();
		for (String key : keySet) {
			if(uri.startsWith(key)){
				return MENU_URL_PATTERN_MAP.get(key);
			}
		}
		return "";
	}
}
