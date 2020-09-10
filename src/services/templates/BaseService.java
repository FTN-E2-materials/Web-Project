package services.templates;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import services.interfaces.rest.DatabaseAccessInterface;
import services.interfaces.util.HttpResponseHandler;
import services.interfaces.util.SessionHandler;


/** Abstract template for a basic service class. Contains a ServletContext field,
 *  database access string and storage access string fields.
 * @author Nikola
 * @param <T>
 * @param <DAO>
 */
public abstract class BaseService implements DatabaseAccessInterface, SessionHandler, HttpResponseHandler {
	
	@Context
	protected ServletContext ctx;
	
	/** This string is used to identify unique database names accross the server. */
	protected String databaseAttributeString;
	/** Location of the data storage file where the data will be kept on the disk. */
	protected String storageFileLocation;
}
