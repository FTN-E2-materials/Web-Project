package util.services;

import java.util.HashMap;
import java.util.Map;

import util.Config;

/** Singleton class which servers as a caching mechanism for HTML pages in order to avoid fetching from files
 *  every time a request comes in. */
public class HtmlService {
	
	private static HtmlService instance = null;
	private Map<String, String> pages;
	private String relativePathToFiles;
	
	
	public static HtmlService getInstance() {
		if (instance == null)
			instance = new HtmlService();
		
		return instance;
	}
	
	private HtmlService() {
		pages = new HashMap<String, String>();
		relativePathToFiles = Config.HTML_FILE_ROOT;
	}
	
	public String getPage(String htmlPageName) {
		String page = pages.get(htmlPageName);
		if (page == null) { // Page has not yet been loaded
			page = IOService.readSourceFile(relativePathToFiles + htmlPageName);
			pages.put(htmlPageName, page);
		}
		
		if (page.isEmpty()) 
			System.out.println("Couldn't find file: " + htmlPageName);
		
		return page;
	}
}
