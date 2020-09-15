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
	private String REVIEWS_GUEST_PAGE;
	private String REVIEWS_HOST_PAGE;
	private String RESERVATIONS_GUEST_PAGE;
	private String RESERVATIONS_HOST_PAGE;
	
	private String TEST_PAGE;


	// Load all HTML files into memory as strings on startup
	private void init() {
		System.out.println("Starting the caching process");
		
		LANDING_PAGE = IOService.readSourceFile(Config.LANDING_PAGE_LOCATION);
		LOGIN_PAGE = IOService.readSourceFile(Config.LOGIN_PAGE_LOCATION);
		REGISTRATION_PAGE = IOService.readSourceFile(Config.REGISTRATION_LOCATION);
		GUEST_HOME = IOService.readSourceFile(Config.GUEST_HOME_LOCATION);
		HOST_HOME = IOService.readSourceFile(Config.HOST_HOME_LOCATION);
		ADMIN_HOME = IOService.readSourceFile(Config.ADMIN_HOME_LOCATION);
		CREATE_APARTMENT_PAGE = IOService.readSourceFile(Config.CREATE_APARTMENT_LOCATION);
		SINGLE_APARTMENT_PAGE_HOST = IOService.readSourceFile(Config.APARTMENT_DETAILS_HOST_LOCATION);
		SINGLE_APARTMENT_PAGE_GUEST = IOService.readSourceFile(Config.APARTMENT_DETAILS_GUEST_LOCATION);
		GUEST_ACCOUNT_PAGE = IOService.readSourceFile((Config.GUEST_ACCOUNT_LOCATION));
		HOST_ACCOUNT_PAGE = IOService.readSourceFile(Config.HOST_ACCOUNT_LOCATION);
		EDIT_APARTMENT_PAGE = IOService.readSourceFile(Config.EDIT_APARTMENT_LOCATION);
		REVIEWS_GUEST_PAGE = IOService.readSourceFile(Config.REVIEWS_GUEST_LOCATION);
		REVIEWS_HOST_PAGE = IOService.readSourceFile(Config.REVIEWS_HOST_LOCATION);
		RESERVATIONS_GUEST_PAGE = IOService.readSourceFile(Config.RESERVATIONS_GUEST_LOCATION);
		RESERVATIONS_HOST_PAGE = IOService.readSourceFile(Config.RESERVATIONS_HOST_LOCATION);

		TEST_PAGE = IOService.readSourceFile("/web-files/html-files/text.html");
		
		System.out.println("Completed caching.");
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
	
	public String getReviewsForGuestPage() {
		return REVIEWS_GUEST_PAGE;
	}
	
	public String getReviewsForHostPage() {
		return REVIEWS_HOST_PAGE;
	}

	public String getReservationsPageGuest() {
		return RESERVATIONS_GUEST_PAGE;
	}
	
	public String getReservationsPageHost() {
		return RESERVATIONS_HOST_PAGE;
	}
	
	public String getReservationsPageAdmin() {
		return "Coming soon";
	}
	
	public String getTestPage() {
		return TEST_PAGE;
	}
}
