package services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import beans.model.UserAccount;
import util.RequestWrapper;

public interface AuthServiceInterface {
	public void login(RequestWrapper loginInfo, @Context HttpServletRequest request);
	public void register(UserAccount account, @Context HttpServletRequest request);
	public void logOut(@Context HttpServletRequest request);
}
