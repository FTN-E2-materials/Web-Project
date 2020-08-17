package services.interfaces;

/** Interface to provide PostConstruct methods for RESTful service classes, through which the services
 *  will be able to communicate with the database and storage. */
public interface DatabaseAccessInterface {
	/** Add PostConstruct to this method when overriding in services */
	public void onCreate();
	
	/** Set the string attribute which will be used to call the database through REST Context reference */
	public void setDatabaseString();
	/** Define string which points to the absolute location of the file where the database files are located */
	public void setStorageLocation();
	/** Initialize neccessary attributes for functioning of REST services (dao, storage, etc)*/
	public void initAttributes();
}
