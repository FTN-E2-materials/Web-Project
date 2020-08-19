package services.nav;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import services.interfaces.NavigationResponseHandler;
import services.interfaces.SessionTracker;

/** Abstract class for page navigation services. Implements the session tracker methods for tracking
 *  user sessions, and the basic navigation HTTP response methods for forbidding and allowing access.
 *  Contains GET method for fetching website pages.
 *  @author Nikola
 */
public abstract class PageNavigationService implements SessionTracker, NavigationResponseHandler {

	public abstract Response get(@Context HttpServletRequest request);
}
