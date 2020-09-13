package util.services;


import java.util.HashMap;
import java.util.Map;

import util.Config;


/** Small utility for fetching script files */
public class ScriptService {
	
	private static ScriptService instance;
	
	public static ScriptService getInstance() {
		if (instance == null)
			instance = new ScriptService();
		return instance;
	}
	
	private Map<String, String> cachedScripts;
	
	private ScriptService() {
		cachedScripts = new HashMap<String, String>();
	}
	
	/** Returns the given script as InputStream. 
	 * @param scriptName
	 * @return script file as InputStream, or null if it doesn't exist */
	public String getScript(String scriptName) {
		String script = cachedScripts.getOrDefault(scriptName, null);
		
		if (script == null) {
			script = IOService.readSourceFile(Config.SCRIPT_FILE_ROOT + scriptName);
			cachedScripts.put(scriptName, script);
		}
		
		return script;
	}
}
