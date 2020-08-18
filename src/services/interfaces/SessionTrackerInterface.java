package services.interfaces;

import javax.servlet.http.HttpServletRequest;

import beans.interfaces.SessionToken;

public interface SessionTrackerInterface {
	public SessionToken getCurrentSession(HttpServletRequest request);
	public void deleteSession(HttpServletRequest request);
	public void createSession(SessionToken obj, HttpServletRequest request);
}
