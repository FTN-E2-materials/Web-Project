package util.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import util.Config;


/** Singleton class which servers as a caching mechanism for HTML pages in order to avoid fetching from files
 *  every time a request comes in. */
public class HTMLService {
	
	private static HTMLService instance = null;
	
	public static HTMLService getInstance() {
		if (instance == null)
			instance = new HTMLService();
		
		return instance;
	}
	
	private HTMLService() {
		init();
	}
	
	private String LANDING_PAGE;
	private String LOGIN_PAGE;
	private String REGISTRATION_PAGE;
	private String GUEST_HOME;
	private String HOST_HOME;
	private String ADMIN_HOME;	
	private String CREATE_APARTMENT_PAGE;
	private String SINGLE_APARTMENT_PAGE_HOST;
	private String SINGLE_APARTMENT_PAGE_GUEST;
	private String GUEST_ACCOUNT_PAGE;
	private String HOST_ACCOUNT_PAGE;
	private String EDIT_APARTMENT_PAGE;


	// Load all HTML files into memory as strings on startup
	private void init() {
		System.out.println("Starting the caching process");
		
		LANDING_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.LANDING_PAGE_LOCATION));
		LOGIN_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.LOGIN_PAGE_LOCATION));
		REGISTRATION_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.REGISTRATION_LOCATION));
		GUEST_HOME = streamToString(HTMLService.class.getResourceAsStream(Config.GUEST_HOME_LOCATION));
		HOST_HOME = streamToString(HTMLService.class.getResourceAsStream(Config.HOST_HOME_LOCATION));
		ADMIN_HOME = streamToString(HTMLService.class.getResourceAsStream(Config.ADMIN_HOME_LOCATION));
		CREATE_APARTMENT_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.CREATE_APARTMENT_LOCATION));
		SINGLE_APARTMENT_PAGE_HOST = streamToString(HTMLService.class.getResourceAsStream(Config.APARTMENT_DETAILS_HOST_LOCATION));
		SINGLE_APARTMENT_PAGE_GUEST = streamToString(HTMLService.class.getResourceAsStream(Config.APARTMENT_DETAILS_GUEST_LOCATION));
		GUEST_ACCOUNT_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.GUEST_ACCOUNT_LOCATION));
		HOST_ACCOUNT_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.HOST_ACCOUNT_LOCATION));
		EDIT_APARTMENT_PAGE = streamToString(HTMLService.class.getResourceAsStream(Config.EDIT_APARTMENT_LOCATION));

		System.out.println("Completed caching.");
	}
	
	/** Convert the given InputStream to a String. If an error occurrs, returns an empty String */
	private String streamToString(InputStream stream) {
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = stream.read(buffer)) != -1) 
			    result.write(buffer, 0, length);
			
			return result.toString();
		}
		catch (Exception e) {
			return "";
		}
	}
	
	public String getLandingPage() {
		return LANDING_PAGE;
	}

	public String getLoginPage() {
		return LOGIN_PAGE;
	}

	public String getRegistrationPage() {
		return REGISTRATION_PAGE;
	}

	public String getGuestHomePage() {
		return GUEST_HOME;
	}

	public String getHostHomePage() {
		return HOST_HOME;
	}

	public String getAdminHomePage() {
		return ADMIN_HOME;
	}

	public String getCreateApartmentPage() {
		return CREATE_APARTMENT_PAGE;
	}

	public String getSingleApartmentPageHost() {
		return SINGLE_APARTMENT_PAGE_HOST;
	}
	
	public String getSingleApartmentPageGuest() {
		return SINGLE_APARTMENT_PAGE_GUEST;
	}

	public String getGuestAccountPage() {
		return GUEST_ACCOUNT_PAGE;
	}

	public String getHostAccountPage() {
		return HOST_ACCOUNT_PAGE;
	}
	
	public String getEditApartmentPage() {
		return EDIT_APARTMENT_PAGE;
	}
	
}
