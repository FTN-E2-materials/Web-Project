package services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import beans.model.UserAccount;

public interface AuthServiceInterface {
	public void login(String username, String password, @Context HttpServletRequest request);
	public void register(UserAccount account, @Context HttpServletRequest request);
	public void logOut(@Context HttpServletRequest request);
}
