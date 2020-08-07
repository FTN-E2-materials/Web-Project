package services;

import java.util.Collection;
import beans.interfaces.*;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import dao.BeanDAO;


/** Abstract template for a REST service class. 
 * Instantiates with a bean model and DAO class related to it */
public abstract class Service<T extends BeanInterface, DAO extends BeanDAO<T>> {
	
	@Context
	ServletContext ctx;
	
	// If using multiple child classes of this, load this String separately in each of those classes to avoid collision.
	protected String databaseAttributeString = "database";
	
	
	/** POST to add received JSON BeanObject to the database.
	 * @param BeanObject
	 * @return JSON format BeanObject added, or null if failed.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public T add(T object) {
		if (object == null) {
			return null;
		}
		else if (object.getKey() == null) {
			return null;
		}
		else {
			DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
			return objectDAO.add(object);
		}
	}
	
	@PostConstruct
	protected abstract void init();
	
	/** Returns a JSON array of all BeanObjects in the database. */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Collection<T> getAll(){
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
	@Path("/search")
	public T get(@QueryParam("key") String key) {
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		
		return objectDAO.getByKey(key);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{ime}")
	public T delete(@PathParam("ime") String key) {
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		
		return objectDAO.delete(key);
	}
}

