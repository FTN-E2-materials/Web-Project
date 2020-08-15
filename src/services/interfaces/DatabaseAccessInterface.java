package services.interfaces;

/** Interface to provide PostConstruct methods for RESTful service classes */
public interface DatabaseAccessInterface {
	public void onCreate();
	
	public void setDatabaseString();
	public void setStorageLocation();
	public void initAttributes();
}
