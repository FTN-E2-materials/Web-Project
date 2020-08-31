package services.templates;

import java.util.Collection;
import beans.model.DatabaseEntity;

import dao.BeanDAO;
import util.wrappers.RequestWrapper;


/** Abstract template for a CRUD service class with predefined basic CRUD methods
 *  Handles object validity (not null, valid fields).
 * @author Nikola
 * @param <T>
 * @param <DAO>
 */
public abstract class CRUDService<T extends DatabaseEntity, DAO extends BeanDAO<T>> extends BaseService {
	
	/** Checks if an object is valid, and if yes, adds it to the database.
	 * @param DatabaseEntity
	 * @return JSON format Entity if added, or null if failed.
	 */
	protected T create(T object) {
		if (object == null) {
			return null;
		}
		try { object.validate(); }
			catch (IllegalArgumentException ex) {
				System.out.println("Attempt to create invalid object.");
				return null;
			}
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		return dao.create(object);
	}
	
	/** Returns a JSON array of all entities in the database. */
	protected Collection<T> getAll(){
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		
		return dao.getAll();	
	}
	
	/** Fetches an object with the key matching value from 'stringKey' attribute of the wrapper argument.
	 *  If the passed wrapper is null, or 'stringKeys' is null, null is returned.
	 *  If the appropriate entity is not found, null is returned.
	 * @param key
	 * @return DB entity with the given key, or null if it does not exist.
	 */
	protected T getByID(RequestWrapper requestWrapper) {
		if (requestWrapper == null)
			return null;
		if (requestWrapper.stringKey == null) 
			return null;
		
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		System.out.println("Trying to fetch ID: " + requestWrapper.stringKey);
		
		return dao.getByKey(requestWrapper.stringKey);
	}
	
	/** Fetches an object with the matching ID key.
	 * @param key
	 * @return DB entity with the given key, or null if it does not exist.
	 */
	protected T getByID(String id) {
		if (id == null)
			return null;
		
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		System.out.println("Trying to fetch ID: " + id);
		
		return dao.getByKey(id);
	}
	
	protected T delete(RequestWrapper requestWrapper) {
		if (requestWrapper == null)
			return null;
		if (requestWrapper.stringKey == null)
			return null;
					
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		T obj = dao.getByKey(requestWrapper.stringKey);
		
		if (obj == null)
			return null;
		if (obj.key == null)
			return null;
		
		return dao.delete(obj);
	}
	
	/** Updates the object from the database with the same key as the given object.
	 *  Given argument object must be valid.
	 * @param obj
	 * @return modified object on success, or null if failed.
	 */
	protected T update(T obj) {
		if (obj == null)
			return null;
		try { obj.validate(); }
			catch (IllegalArgumentException ex) {
				System.out.println("Trying to update entity with invalid field values.");
				return null;
			}
		
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		return dao.update(obj);
	}
}

