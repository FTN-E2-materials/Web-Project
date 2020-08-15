package services.interfaces;

/** Interface to provide PostConstruct methods for RESTful service classes */
public interface DatabaseServiceInterface {
	public void onCreate();
	
	public void setDatabaseString();
	public void setStorageLocation();
	public void initAttributes();
}
