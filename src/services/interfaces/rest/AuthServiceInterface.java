package services.interfaces.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import beans.model.entities.UserAccount;
import util.wrappers.RequestWrapper;

public interface AuthServiceInterface {
	/** Provides a way for clients to log in. RequestWrapper object should be populated in the following way: 
	 *  [stringKey[0]] = username, [stringKey[1] = password]. Other fields are redundant.
	 * @param loginInfo wrapper which holds the neccessary login information.
	 * @param request client request body
	 * @return
	 */
	public Response login(RequestWrapper loginInfo, HttpServletRequest request);
	/** Provides a way to register a new user account. Client should send a valid UserAccount object */
	public Response register(UserAccount account, HttpServletRequest request);
	/** Deletes the curretn client session */
	public Response logOut(HttpServletRequest request);
} 
