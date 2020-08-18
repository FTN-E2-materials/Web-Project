package services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import beans.model.DatabaseEntity;
import beans.model.UserAccount;
import util.RequestWrapper;

public interface AuthenticationInterface<T extends DatabaseEntity> {
	public Response login(RequestWrapper loginInfo, HttpServletRequest request);
	public Response register(UserAccount account, HttpServletRequest request);
	public Response logOut(HttpServletRequest request);
} 
