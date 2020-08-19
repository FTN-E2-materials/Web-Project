package services.interfaces;

import javax.servlet.http.HttpServletRequest;

import beans.interfaces.SessionToken;
import util.Config;

/** Interface to provide basic session tracking for users. */
public interface SessionTracker {
	
	public default SessionToken getCurrentSession(HttpServletRequest request) {
		return (SessionToken)request.getAttribute(Config.userSessionAttributeString);
	}
}
