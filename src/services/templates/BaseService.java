package services.templates;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import beans.model.DatabaseEntity;
import beans.model.UserAccount;
import dao.BeanDAO;
import services.interfaces.DatabaseAccessInterface;
import util.Config;


/** Abstract template for a basic service class. Contains a ServletContext field,
 *  database access and storage access.
 * @author Nikola
 *
 * @param <T>
 * @param <DAO>
 */
public abstract class BaseService implements DatabaseAccessInterface {
	
	@Context
	protected ServletContext ctx;
	
	/** This string is used to identify unique database names accross the server. */
	protected String databaseAttributeString;
	/** Location of the data storage file where the data will be kept on the disk. */
	protected String storageFileLocation;
	
	/** Fetch the auth token which lets you identify the user that is currently using the service */
	protected UserAccount getCurrentUser(HttpServletRequest request) {
		return (UserAccount) request.getAttribute(Config.userSessionAttributeString);
	}
}
