package services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import beans.model.UserAccount;
import util.wrappers.RequestWrapper;

public interface AuthenticationInterface {
	public Response login(RequestWrapper loginInfo, HttpServletRequest request);
	public Response register(UserAccount account, HttpServletRequest request);
	public Response logOut(HttpServletRequest request);
} 
