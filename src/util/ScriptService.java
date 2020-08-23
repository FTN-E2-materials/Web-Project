package util;


import java.io.InputStream;


/** Small utility for fetching script files */
public class ScriptService {
	
	/** Returns the given script as InputStream. 
	 * @param scriptName
	 * @return script file as InputStream, or null if it doesn't exist */
	public static InputStream getScript(String scriptName) {
		InputStream stream = ScriptService.class.getResourceAsStream(Config.SCRIPT_FILE_ROOT + scriptName);
		return stream;
	}
}
