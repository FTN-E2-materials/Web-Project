package util;

import util.services.HTMLService;

/** Keeps static constants */
public class Config {
	
	// For storing DAO objects under attribute names in ServletContext objects
	public static final String apartmentDatabaseString = "apartmentDatabase";
	public static final String reviewDatabaseString = "reviewDatabase";
	public static final String reservationDatabaseString = "reservationDatabase";
	public static final String amenityDatabaseString = "amenityDatabase";
	public static final String userDatabaseString = "userDatabase";

	// For labeling objects in the database (eg. apartment104)
	public static final String apartmentIdHeader = "apartment";
	public static final String reviewIdHeader = "review";
	public static final String reservationIdHeader = "reservation";
	public static final String amenityIdHeader = "amenity";
	public static final String userIdheader = "user";

	
	// For storing data in .txt files
	public static final String dataRootLocation = "C:\\Users\\Nikola\\Desktop\\Faks\\WEB\\Web-Project\\data\\";
	
	public static final String reviewsDataLocation = dataRootLocation + "reviews.txt";
	public static final String reservationsDataLocation = dataRootLocation + "reservations.txt";
	public static final String apartmentsDataLocation = dataRootLocation + "apartments.txt";
	public static final String amenitiesDataLocation = dataRootLocation + "amenities.txt";
	public static final String usersDataLocation = dataRootLocation + "users.txt";
	
	public static final int minimalIdNumber = 100;
	
	// For fetching user sessions from the incoming HttpServletRequests
	public static final String userSessionAttributeString = "currentUser";
	
	//ERROR CODES
	public static final int BAD_REQUEST = 400;
	public static final int FORBIDDEN = 403;
	public static final int AUTH_FAILED = 412; // Precondition failed status
	public static final int NOT_FOUND = 404;
	
	// Data annotation paths
	public static final String dataAnnotationRoot = "/data/";
	
	public static final String APARTMENTS_DATA_PATH = dataAnnotationRoot + "apartments";
	public static final String AMENITIES_DATA_PATH = dataAnnotationRoot + "amenities";
	public static final String RESERVATIONS_DATA_PATH = dataAnnotationRoot + "reservations";
	public static final String USERS_DATA_PATH = dataAnnotationRoot + "users";
	public static final String REVIEWS_DATA_PATH = dataAnnotationRoot + "reviews";
	public static final String AUTH_DATA_PATH = dataAnnotationRoot + "auth";
	
	
	// Web address paths (localhost/project/{path})
	public static final String LOGIN_PATH = "/login";
	public static final String REGISTRATION_PATH = "/registration";
	public static final String LOGOUT_PATH = "/logout";
	
	// Page annotation paths
	public static final String LANDING_PAGE_PATH = "";
	public static final String LOGIN_PAGE_PATH = "login";
	public static final String REGISTRATION_PAGE_PATH = "registration";
	public static final String CREATE_APARTMENT_PAGE_PATH = "createApartment";
	public static final String APARTMENT_PAGE_PATH = "apartments/";
	public static final String EDIT_APARTMENT_PAGE_PATH = "apartments/edit/";
	public static final String ACCOUNT_PAGE_PATH = "account";

	
	// Scripts annotation path
	public static final String SCRIPTS_PATH = "/scripts";
	
	//WEB FILES
	public static final String WEB_FILE_ROOT = "/web-files/";
	
	// HTML file locations 
	public static String HTML_FILE_ROOT = WEB_FILE_ROOT + "html-files/";
	public static final String LANDING_PAGE_LOCATION = HTML_FILE_ROOT + "index.html";	
	public static final String LOGIN_PAGE_LOCATION = HTML_FILE_ROOT + "login.html";
	public static final String GUEST_HOME_LOCATION = HTML_FILE_ROOT + "guest_home.html";
	public static final String HOST_HOME_LOCATION = HTML_FILE_ROOT + "host_home.html";
	public static final String ADMIN_HOME_LOCATION = HTML_FILE_ROOT + "admin_home.html";
	public static final String REGISTRATION_LOCATION = HTML_FILE_ROOT + "registration.html";
	public static final String CREATE_APARTMENT_LOCATION = HTML_FILE_ROOT + "create_apartment.html";
	public static final String APARTMENT_DETAILS_HOST_LOCATION = HTML_FILE_ROOT + "apartment_host.html";
	public static final String APARTMENT_DETAILS_GUEST_LOCATION = HTML_FILE_ROOT + "apartment_guest.html";
	public static final String EDIT_APARTMENT_LOCATION = HTML_FILE_ROOT + "edit_apartment.html";
	public static final String GUEST_ACCOUNT_LOCATION = HTML_FILE_ROOT + "guest_account.html";
	public static final String HOST_ACCOUNT_LOCATION = HTML_FILE_ROOT + "host_account.html";

	// JS Script root location
	public static String SCRIPT_FILE_ROOT = WEB_FILE_ROOT + "scripts/";
}
