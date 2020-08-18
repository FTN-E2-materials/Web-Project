package services.templates;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.UserAccount;
import services.interfaces.DatabaseAccessInterface;
import services.interfaces.HttpResponseHandlerInterface;
import services.interfaces.SessionTrackerInterface;
import util.Config;


/** Abstract template for a basic service class. Contains a ServletContext field,
 *  database access and storage access.
 * @author Nikola
 * @param <T>
 * @param <DAO>
 */
public abstract class BaseService implements DatabaseAccessInterface, SessionTrackerInterface, HttpResponseHandlerInterface {
	
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
	
	/** Returns a bad request response with code 400 */
	@Override
	public Response BadRequest() {
		return Response.status(Config.BAD_REQUEST).build();
	}
	
	/** Returns a forbidden request response with code 403 */
	@Override
	public Response ForbiddenRequest() {
		return Response.status(Config.FORBIDDEN).build();
	}
	
	/** Returns an OK request response, with an optional object as payload */
	@Override
	public Response OK(Object object) {
		return Response.ok(object).build();
	}
	
	/** Returns a response which means that user has failed to authorize themselves */
	public Response AuthFailed(Object object) {
		return Response.status(Config.AUTH_FAILED).entity(object).build();
	}
	
	// TODO Potentially redundant, could be handled without string arg, due to the specific nature of BadRequest context (bad input, please fix etc)
	/** Returns a BadRequest response, with an additional message explaining why the error occurred. */
	public Response BadRequest(Object object) {
		return Response.status(Config.BAD_REQUEST).entity(object).build();
	}

}
