package services;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.UserAccount;
import dao.UserDAO;
import services.interfaces.AuthenticationInterface;
import services.templates.BaseService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;

@Path("/auth")
public class AuthService extends BaseService implements AuthenticationInterface {

	@PostConstruct
	@Override
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}
	
	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.userDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.usersDataLocation;
	}

	@Override
	public void initAttributes() {
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<UserAccount>(UserAccount.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new UserDAO(
										(Storage<UserAccount>)ctx.getAttribute(storageFileLocation)
									));
	}
	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public void login(RequestWrapper loginInfo, @Context HttpServletRequest request) {
		// If already logged in, deny
		if (request.getAttribute(Config.userSessionAttributeString) != null)
			return;
		
		// Failsafing
		if (loginInfo == null)
			return;
		if (loginInfo.stringArgs == null)
			return;
		if (loginInfo.stringArgs.size() != 2)
			return;
		
		String username = loginInfo.stringArgs.get(0);
		String password = loginInfo.stringArgs.get(1);
		
		UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
		UserAccount account = dao.getByKey(username);
		if (account == null)
			return;
		if (!account.password.contentEquals(password))
			return;
		
		System.out.println("Login successful for account: " + username);
		request.setAttribute(Config.userSessionAttributeString, account);
	}

	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public void register(UserAccount account, @Context HttpServletRequest request) {
		// If already logged in, deny
		if (request.getAttribute(Config.userSessionAttributeString) != null) {
			System.out.println("This session is already logged in.");
			return;	
		}
				
		if (account == null)
			return;
		account.validate();
		
		UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
		UserAccount result = dao.create(account);
		if (result == null)
			return;
		
		System.out.println("Creating account with the username: " + account.id);
		request.setAttribute(Config.userSessionAttributeString, result);
	}

	@Path("/logout")
	@POST
	@Override
	public void logOut(@Context HttpServletRequest request) {
		request.setAttribute(Config.userSessionAttributeString, null);
		System.out.println("User logged out successfully.");
	}
}
