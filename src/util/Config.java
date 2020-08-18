package util;

import javax.ws.rs.core.Response.Status;

/** Keeps static constants */
public class Config {
	public static final String apartmentDatabaseString = "apartmentDatabase";
	public static final String reviewDatabaseString = "reviewDatabase";
	public static final String reservationDatabaseString = "reservationDatabase";
	public static final String amenityDatabaseString = "amenityDatabase";
	public static final String userDatabaseString = "userDatabase";

	
	public static final String apartmentIdHeader = "apartment";
	public static final String reviewIdHeader = "review";
	public static final String reservationIdHeader = "reservation";
	public static final String amenityIdHeader = "amenity";
	public static final String userIdheader = "user";

	
	public static final String dataRootLocation = "C:\\Users\\Nikola\\Desktop\\Faks\\WEB\\Web-Project\\data\\";
	
	public static final String reviewsDataLocation = dataRootLocation + "reviews.txt";
	public static final String reservationsDataLocation = dataRootLocation + "reservations.txt";
	public static final String apartmentsDataLocation = dataRootLocation + "apartments.txt";
	public static final String amenitiesDataLocation = dataRootLocation + "amenities.txt";
	public static final String usersDataLocation = dataRootLocation + "users.txt";
	
	public static final int minimalIdNumber = 100;
	
	public static final String userSessionAttributeString = "currentUser";
	
	//ERROR CODES
	public static final int BAD_REQUEST = 400;
	public static final int FORBIDDEN = 403;
	public static final int AUTH_FAILED = 412; // Precondition failed status
}
