package services.interfaces.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.interfaces.SessionToken;
import util.Config;

/** Interface to provide basic session tracking for users. 
 *  Allows identification of the current user via the HttpServletRequest argument. */
public interface SessionTracker {
	
	/** Return the SessionToken object from the current user session */
	public default SessionToken getCurrentSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session == null)
			return null;
		
		return (SessionToken)session.getAttribute(Config.userSessionAttributeString);
	}
	
	/** Check if user is logged in 
	 * @param request
	 * @return True if the user is Admin/Host/Guest, or false if not.
	 */
	public default boolean isLoggedIn(HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return false;
		if (session.isGuest() || session.isHost() || session.isAdmin()) 
			return true;
		return false;
	}
}
