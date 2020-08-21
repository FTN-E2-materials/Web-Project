package services.interfaces;

import javax.servlet.http.HttpServletRequest;

import beans.interfaces.SessionToken;
import util.Config;

/** Interface to provide basic session tracking for users. 
 *  Allows identification of the current user via the HttpServletRequest argument. */
public interface SessionTracker {
	
	/** Return the SessionToken object from the current user session */
	public default SessionToken getCurrentSession(HttpServletRequest request) {
		return (SessionToken)request.getAttribute(Config.userSessionAttributeString);
	}
}
