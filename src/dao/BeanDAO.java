package dao;

import java.util.ArrayList;
import beans.interfaces.*;
import java.util.Collection;
import java.util.HashMap;


/** Template DAO class for basic CRUD operations with BeanInterface classes. 
 *  Contains basic CRUD methods (add, getByID, getAll, delete)*/
public abstract class BeanDAO <T extends BeanInterface> {
	
	// HashMap to act as a database
	protected HashMap<String, T> database;
	
	public BeanDAO() {
		database = new HashMap<String, T>();
	}
	
	/** Should be used for adding objects to the database. */
	protected abstract void init();
	
	/** Add BeanObject to the database
	 * @param BeanObject
	 * @return BeanObject or null if they already exist.
	 */
	public T add(T object) {
		if (!database.containsKey(object.getKey())) {
			database.put(object.getKey(), object);
			
			return object;
		}
		else
			return null;
	}
	
	/** Get a bean object from the database with the specified key attached to it.
	 * @param insuranceNumber
	 * @return BeanObject or null if the key doesn't exist.
	 */
	public T getByKey(String key) {
		return database.get(key);
	}
	
	/** Returns a collection of all bean objects from the database */
	public Collection<T> getAll(){
		ArrayList<T> allEntries = new ArrayList<T>(); 
		
		for (T entry : database.values()) {
			allEntries.add(entry);
		}
		
		return allEntries;
	}
	
	/** Removes an object with the specified key from the database */
	public T delete(String key) {
		return database.remove(key);
	}
}