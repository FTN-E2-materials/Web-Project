package services.templates;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import beans.interfaces.SessionToken;
import services.interfaces.DatabaseAccessInterface;
import services.interfaces.DataResponseHandler;
import services.interfaces.SessionTracker;
import util.Config;


/** Abstract template for a basic service class. Contains a ServletContext field,
 *  database access and storage access.
 * @author Nikola
 * @param <T>
 * @param <DAO>
 */
public abstract class BaseService implements DatabaseAccessInterface, SessionTracker, DataResponseHandler {
	
	@Context
	protected ServletContext ctx;
	
	/** This string is used to identify unique database names accross the server. */
	protected String databaseAttributeString;
	/** Location of the data storage file where the data will be kept on the disk. */
	protected String storageFileLocation;
}
