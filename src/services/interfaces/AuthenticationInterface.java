package services.interfaces;

import javax.servlet.http.HttpServletRequest;

import beans.model.DatabaseEntity;
import beans.model.UserAccount;
import util.RequestWrapper;

public interface AuthenticationInterface<T extends DatabaseEntity> {
	public UserAccount login(RequestWrapper loginInfo, HttpServletRequest request);
	public UserAccount register(UserAccount account, HttpServletRequest request);
	public void logOut(HttpServletRequest request);
}
