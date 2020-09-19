package util;

/** Keeps static constants */
public class Config {
	
	// For storing DAO objects under attribute names in ServletContext objects
	public static final String apartmentDatabaseString = "apartmentDatabase";
	public static final String reviewDatabaseString = "reviewDatabase";
	public static final String reservationDatabaseString = "reservationDatabase";
	public static final String amenityDatabaseString = "amenityDatabase";
	public static final String userDatabaseString = "userDatabase";
	public static final String imageDatabaseString = "imageDatabase";

	// For labeling objects in the database (eg. apartment104)
	public static final String apartmentIdHeader = "apartment";
	public static final String reviewIdHeader = "review";
	public static final String reservationIdHeader = "reservation";
	public static final String amenityIdHeader = "amenity";
	public static final String userIdheader = "user";
	public static final String imageIdHeader = "image";

	
	// For storing data in .txt files
	public static final String dataRootLocation = "C:\\Users\\Nikola\\Desktop\\Faks\\WEB\\Web-Project\\data\\";
		
	public static final String reviewsDataLocation = dataRootLocation + "reviews.txt";
	public static final String reservationsDataLocation = dataRootLocation + "reservations.txt";
	public static final String apartmentsDataLocation = dataRootLocation + "apartments.txt";
	public static final String amenitiesDataLocation = dataRootLocation + "amenities.txt";
	public static final String usersDataLocation = dataRootLocation + "users.txt";
	public static final String imagesRootLocation = dataRootLocation + "images.txt";

	
	public static final int minimalIdNumber = 100;
	
	// For fetching user sessions from the incoming HttpServletRequests
	public static final String userSessionAttributeString = "currentUser";
	
	//ERROR CODES
	public static final int BAD_REQUEST = 400;
	public static final int FORBIDDEN = 403;
	public static final int AUTH_FAILED = 412; // Precondition failed status
	public static final int NOT_FOUND = 404;
	public static final int NOT_ALLOWED = 405;
	
	// Data annotation paths
	public static final String dataAnnotationRoot = "/data/";
	
	public static final String APARTMENTS_SERVICE_PATH = dataAnnotationRoot + "apartments";
	public static final String AMENITIES_SERVICE_PATH = dataAnnotationRoot + "amenities";
	public static final String RESERVATIONS_SERVICE_PATH = dataAnnotationRoot + "reservations";
	public static final String USERS_SERVICE_PATH = dataAnnotationRoot + "users";
	public static final String REVIEWS_SERVICE_PATH = dataAnnotationRoot + "reviews";
	public static final String AUTH_SERVICE_PATH = dataAnnotationRoot + "auth";
	public static final String IMAGES_SERVICE_PATH = dataAnnotationRoot + "images";
	
	
	// Scripts annotation path
	public static final String SCRIPTS_PATH = "/scripts";
	
	//WEB FILES
	public static final String WEB_FILE_ROOT = "/web-files/";
	
	// HTML file locations 
	public static String HTML_FILE_ROOT = WEB_FILE_ROOT + "html-files/";

	// JS Script root location
	public static String SCRIPT_FILE_ROOT = WEB_FILE_ROOT + "scripts/";
}
