package dao;

import java.util.ArrayList;

import javafx.collections.MapChangeListener;
import storage.Storage;
import util.Config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sun.javafx.collections.ObservableMapWrapper;

import beans.model.template.DatabaseEntity;


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
				forceUpdate();
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
	
	/** Get an object from the database with the specified key attached to it. If key is null or object is deleted, null is returned.
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
		forceUpdate();
		
		return obj;
	}
	
	/** Removes an object with the specified key from the database */
	public T deleteByID(String key) {
		T entity = database.getOrDefault(key, null);
		
		if (entity == null)
			return null;
		
		entity.delete();
		forceUpdate();
		
		return entity;
	}
	
	public T delete(T obj) {
		T entity = database.getOrDefault(obj.key, null);
		
		if (entity == null)
			return null;
		
		entity.delete();
		
		return entity;
	}
	
	/** Forces a database update, and writes the whole database to the file */
	public void forceUpdate() {
		storage.writeAll(database);
	}
	
	public void normalizeIndexes(String IDHeader) {
		int cntr = Config.minimalIdNumber;
		Map<String, T> newDatabase = new HashMap<String, T>();
		for (T obj : database.values()) {
			obj.key = IDHeader + ++cntr;
			newDatabase.put(obj.key, obj);
		}
		
		this.database = new ObservableMapWrapper<String, T>(newDatabase);
		forceUpdate();
	}
}
