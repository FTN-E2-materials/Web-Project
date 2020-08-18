package services;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.UserAccount;
import dao.UserDAO;
import services.interfaces.AuthenticationInterface;
import services.templates.BaseService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;

@Path("/")
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
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public UserAccount login(RequestWrapper loginInfo, @Context HttpServletRequest request) {
		// If already logged in, deny
		if (super.getCurrentSession(request) != null)
			return null;
		
		// Failsafing
		if (loginInfo == null)
			return null;
		if (loginInfo.stringArgs == null)
			return null;
		if (loginInfo.stringArgs.size() != 2)
			return null;
		
		String username = loginInfo.stringArgs.get(0);
		String password = loginInfo.stringArgs.get(1);
		
		UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
		UserAccount account = dao.getByKey(username);
		if (account == null)
			return null;
		if (!account.password.contentEquals(password))
			return null;
		
		System.out.println("Login successful for account: " + username);
		super.createSession(account, request);
		return account;
	}

	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public UserAccount register(UserAccount account, @Context HttpServletRequest request) {
		// If already logged in, deny
		if (super.getCurrentSession(request) != null) {
			System.out.println("This session is already logged in.");
			return null;	
		}
				
		if (account == null)
			return null;
		account.validate();
		
		UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
		UserAccount result = dao.create(account);
		if (result == null)
			return null;
		
		System.out.println("Creating account with the username: " + account.id);
		super.createSession(result, request);
		return account;
	}

	@Path("/logout")
	@POST
	@Override
	public void logOut(@Context HttpServletRequest request) {
		super.deleteSession(request);
		System.out.println("User logged out successfully.");
	}
}
