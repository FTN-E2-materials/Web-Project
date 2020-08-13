package dao;

import java.util.ArrayList;
import beans.model.DatabaseEntity;
import javafx.collections.MapChangeListener;
import storage.Storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sun.javafx.collections.ObservableMapWrapper;


/** Template DAO class for basic CRUD operations with DatabaseEntity classes. 
 *  Contains basic CRUD methods (add, getByID, getAll, delete)*/
public abstract class BeanDAO <T extends DatabaseEntity> {
	
	// HashMap to act as a database
	protected ObservableMapWrapper<String, T> database;
	protected String idHeader;
	protected long entityCounter;
	
	public BeanDAO(Storage<T> storage) {
		// If file is empty, GSON returns null
		Map<String, T> storedData = storage.readAll();
		if (storedData == null)
			storedData = new HashMap<String, T>();
		database = new ObservableMapWrapper<String, T>(storedData);
		// Every time map is changed (put/delete), write those changes to the file
		database.addListener(new MapChangeListener<String, T>() {

			@Override
			public void onChanged(Change<? extends String, ? extends T> change) {
				storage.writeAll(database);
			}
		});
		
		// Entity labeling begins at 101 
		entityCounter = 100 + database.size();
	}
	
	/** Should be used for adding objects to the database. */
	protected abstract void init();
	
	/** Add object to the database
	 * @param 
	 * @return Object or null if they already exist.
	 */
	public T create(T object) {
		if (!database.containsKey(object.getKey())) {
			object.setKey(idHeader + ++entityCounter);
			database.put(object.getKey(), object);
			
			return object;
		}
		else
			return null;
	}
	
	/** Get an object from the database with the specified key attached to it.
	 * @param 
	 * @return Object or null if the key doesn't exist.
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
	
	/** Update an existing object */
	public T update(T obj) {
		if (database.get(obj.getKey()) == null)
			return null;
		
		database.put(obj.getKey(), obj);
		return obj;
	}
	
	/** Removes an object with the specified key from the database */
	public T delete(String key) {
		return database.remove(key);
	}
}
