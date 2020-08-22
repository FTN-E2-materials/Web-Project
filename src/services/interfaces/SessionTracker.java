package services.interfaces;

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
}
