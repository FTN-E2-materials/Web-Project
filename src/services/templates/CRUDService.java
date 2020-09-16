package services.templates;

import java.util.Collection;

import beans.interfaces.Cloneable;
import beans.interfaces.FieldWrapperInterface;
import beans.interfaces.SessionToken;
import beans.model.template.DatabaseEntity;
import dao.BeanDAO;
import util.exceptions.EntityValidationException;
import util.services.UpdateService;
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
	protected T create(T object) throws EntityValidationException {
		if (object == null) {
			throw new EntityValidationException("Object must not be null");
		}
		object.validate();
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
	
	/** Updates the first object with the values from the seconds one, while keeping
	    the FieldWrapper-defined field values constant. Constant field values are specified through the implementation of 
	    the FieldWrapperInterface in each specific class.
	 * @param <K>
	 * @param oldEntity object to be updated 
	 * @param updatedEntity object whose values are used for updating the old one
	 * @return
	 */
	protected <K extends DatabaseEntity & 
							Cloneable<K> & 
							FieldWrapperInterface> 
		T update(K oldEntity, K updatedEntity) 
	{
		try { 
			updatedEntity.validate(); 
		}
		catch (EntityValidationException e) {
			System.out.println("Trying to update entity with invalid field values.");
			return null;
		}
		UpdateService.update(oldEntity, updatedEntity);
		
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		return dao.update((T)oldEntity);
	}
	
	/** Update method which directly passes the given object to the appropriate DAO class.
	 *  Warning: Does not do validation, only a null check. Use carefully.
	 * @param obj
	 * @return updated object or null, if the object does not exist
	 */
	protected T update(T obj) {
		if (obj == null)
			return null;
		
		DAO dao = (DAO)ctx.getAttribute(databaseAttributeString);
		return dao.update(obj);
	}
}

