package util;

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
	
	// Data annotation paths
	public static final String dataAnnotationRoot = "/d/";
	public static final String APARTMENTS_DATA_PATH = dataAnnotationRoot + "apartments";
	public static final String AMENITIES_DATA_PATH = dataAnnotationRoot + "amenities";
	public static final String RESERVATIONS_DATA_PATH = dataAnnotationRoot + "reservations";
	public static final String USERS_DATA_PATH = dataAnnotationRoot + "users";
	public static final String REVIEWS_DATA_PATH = dataAnnotationRoot + "reviews";
	public static final String AUTH_DATA_PATH = dataAnnotationRoot + "auth";
	
	// Page annotation paths
	public static final String pageAnnotationRoot = "/";
	public static final String LANDING_PAGE_PATH = pageAnnotationRoot;
	public static final String LOGIN_PAGE_PATH = pageAnnotationRoot + "login";
	public static final String REGISTRATION_PAGE_PATH = pageAnnotationRoot + "registration";
	
}
