package services.templates;

import java.util.Collection;
import beans.model.DatabaseEntity;
import beans.model.UserAccount;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import dao.BeanDAO;
import util.Config;
import util.RequestWrapper;


/** Abstract template for a CRUD service class with predefined basic CRUD method
 * @author Nikola
 * @param <T>
 * @param <DAO>
 */
public abstract class CRUDService<T extends DatabaseEntity, DAO extends BeanDAO<T>> extends BaseService {
	
	/** POST to add received JSON BeanObject to the database.
	 * @param BeanObject
	 * @return JSON format BeanObject added, or null if failed.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public T create(T object, @Context HttpServletRequest request) {
		if (object == null) {
			return null;
		}
		else if (object.getKey() == null) {
			return null;
		}
		else {
			DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
			return objectDAO.create(object);
		}
	}
	
	/** Returns a JSON array of all BeanObjects in the database. */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<T> getAll(@Context HttpServletRequest request){
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		
		return objectDAO.getAll();	
	}
	
	/** Parses the given GET QueryParameter to get a filter string. Returns all BeanObjects
	 * which fit the query.
	 * @param key
	 * @return JSON formatted array of BeanObjects 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public T getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		System.out.println("Trying to fetch ID: " + key);
		return objectDAO.getByKey(key);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public T delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		
		return objectDAO.delete(requestWrapper.stringKey);
	}
	
	// TODO Update method?
	
	/** Fetch the auth token which lets you identify the user that is currently using the service */
	protected UserAccount getCurrentUser(HttpServletRequest request) {
		return (UserAccount) request.getAttribute(Config.userSessionAttributeString);
	}
}

