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
	
	/** This string is used to identify unique database names accross the server. */
	protected String databaseAttributeString;
	
	
	/** POST to add received JSON BeanObject to the database.
	 * @param BeanObject
	 * @return JSON format BeanObject added, or null if failed.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public T create(T object) {
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
	@Path("/{id}")
	public T getByID(@PathParam("id") String key) {
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		System.out.println("Trying to fetch ID: " + key);
		return objectDAO.getByKey(key);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public T delete(String key) {
		DAO objectDAO = (DAO)ctx.getAttribute(databaseAttributeString);
		
		return objectDAO.delete(key);
	}
}

