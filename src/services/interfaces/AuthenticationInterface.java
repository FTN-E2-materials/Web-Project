package services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import beans.model.UserAccount;
import util.RequestWrapper;

public interface AuthenticationInterface {
	public UserAccount login(RequestWrapper loginInfo, @Context HttpServletRequest request);
	public UserAccount register(UserAccount account, @Context HttpServletRequest request);
	public void logOut(@Context HttpServletRequest request);
}
