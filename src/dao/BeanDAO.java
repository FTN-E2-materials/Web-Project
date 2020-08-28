package dao;

import java.util.ArrayList;
import beans.model.DatabaseEntity;
import javafx.collections.MapChangeListener;
import storage.Storage;
import util.Config;

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
	private Storage<T> storage;
	
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
		entityCounter = Config.minimalIdNumber + database.size();
		
		this.storage = storage;
	}
	
	/** Postconstruct initialization, such as header definitions etc. */
	protected abstract void init();
	
	/** Add object to the database
	 * @param 
	 * @return Object or null if they already exist.
	 */
	public T create(T object) {
		if (object.isCountable()) {		// Apartments, reservations and such 
			object.key = idHeader + ++entityCounter;
			database.put(object.key, object);
			
			return object;
		}
		if (!database.containsKey(object.key)) {		// Users
			database.put(object.key, object);
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
		T obj = database.getOrDefault(key, null);
		if (obj == null)
			return null;
		if (obj.isDeleted())
			return null;
		
		return obj;
	}
	
	/** Returns a collection of all bean objects from the database */
	public Collection<T> getAll(){
		ArrayList<T> allEntries = new ArrayList<T>(); 
		
		for (T entry : database.values()) {
			if (!entry.isDeleted())
				allEntries.add(entry);
		}
		
		return allEntries;
	}
	
	/** Update an existing object */
	public T update(T obj) {
		if (database.getOrDefault(obj.key, null) == null)
			return null;
		
		database.put(obj.key, obj);

		return obj;
	}
	
	/** Removes an object with the specified key from the database */
	public T deleteByID(String key) {
		T entity = database.getOrDefault(key, null);
		
		if (entity == null)
			return null;
		
		entity.delete();
		storage.writeAll(database);
		
		return entity;
	}
	
	public T delete(T obj) {
		T entity = database.getOrDefault(obj.key, null);
		
		if (entity == null)
			return null;
		
		entity.delete();
		
		return entity;
	}
}
