package services.templates;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import beans.interfaces.SessionToken;
import beans.model.UserAccount;
import services.interfaces.DatabaseAccessInterface;
import services.interfaces.SessionTrackerInterface;
import util.Config;


/** Abstract template for a basic service class. Contains a ServletContext field,
 *  database access and storage access.
 * @author Nikola
 * @param <T>
 * @param <DAO>
 */
public abstract class BaseService implements DatabaseAccessInterface, SessionTrackerInterface {
	
	@Context
	protected ServletContext ctx;
	
	/** This string is used to identify unique database names accross the server. */
	protected String databaseAttributeString;
	/** Location of the data storage file where the data will be kept on the disk. */
	protected String storageFileLocation;
	
	/** Fetch the auth token which lets you identify the user that is currently using the service */
	@Override
	public SessionToken getCurrentSession(HttpServletRequest request) {
		return (SessionToken)request.getAttribute(Config.userSessionAttributeString);
	}
	
	/** Deletes the current user session related to this request */
	@Override
	public void deleteSession(HttpServletRequest request) {
		request.setAttribute(Config.userSessionAttributeString, null);
	}
	
	/** Attaches the given object to the given user session */
	@Override
	public void createSession(SessionToken token, HttpServletRequest request) {
		request.setAttribute(Config.userSessionAttributeString, token);
	}
}
