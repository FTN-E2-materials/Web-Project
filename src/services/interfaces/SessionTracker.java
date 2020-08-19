package services.interfaces;

import javax.servlet.http.HttpServletRequest;

import beans.interfaces.SessionToken;
import util.Config;

/** Interface with basic methods for tracking and editing the user sessions */
public interface SessionTracker {
	
	public default SessionToken getCurrentSession(HttpServletRequest request) {
		return (SessionToken)request.getAttribute(Config.userSessionAttributeString);
	}
	
	public default void deleteSession(HttpServletRequest request) {
		request.setAttribute(Config.userSessionAttributeString, null);
	}
	
	public default void createSession(SessionToken obj, HttpServletRequest request) {
		request.setAttribute(Config.userSessionAttributeString, obj);
	}
}
