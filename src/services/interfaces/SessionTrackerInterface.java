package services.interfaces;

import javax.servlet.http.HttpServletRequest;

import beans.interfaces.SessionToken;
import beans.model.UserAccount;

public interface SessionTrackerInterface {
	public UserAccount getCurrentUser(HttpServletRequest request);
	public void deleteSession(HttpServletRequest request);
	public void createSession(SessionToken obj, HttpServletRequest request);
}
