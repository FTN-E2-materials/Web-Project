package util;


import java.io.InputStream;

import services.interfaces.NavigationResponseHandler;

/** Small utility for fetching script files */
public class ScriptService implements NavigationResponseHandler {
	
	/** Returns the given script as InputStream. 
	 * @param scriptName
	 * @return script file as InputStream, or null if it doesn't exist */
	public static InputStream getScript(String scriptName) {
		InputStream stream = ScriptService.class.getResourceAsStream(Config.SCRIPT_FILE_ROOT + scriptName);
		return stream;
	}
}
