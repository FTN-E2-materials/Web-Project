package services.data;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.model.entities.UserAccount;
import beans.model.enums.TypeOfUser;
import dao.UserDAO;
import services.interfaces.rest.AuthServiceInterface;
import services.templates.BaseService;
import storage.Storage;
import util.Config;
import util.wrappers.RequestWrapper;

@Path(Config.AUTH_SERVICE_PATH)
public class AuthService extends BaseService implements AuthServiceInterface {

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
	
	@Path(Config.LOGIN_PATH)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response login(RequestWrapper loginInfo, @Context HttpServletRequest request) {
		// If already logged in, deny
		if (getCurrentSession(request) != null)
			return ForbiddenRequest();
		
		// Failsafing
		if (loginInfo == null)
			return BadRequest();
		if (loginInfo.stringArgs == null)
			return BadRequest();
		if (loginInfo.stringArgs.size() != 2)
			return BadRequest();
		
		String username = loginInfo.stringArgs.get(0);
		String password = loginInfo.stringArgs.get(1);
		
		if (username.length() < 4)
			return BadRequest("Username too short.");
		if (password.length() < 4)
			return BadRequest("Password too short.");
		
		UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
		UserAccount account = dao.getByKey(username);
		if (account == null)
			return AuthFailed("Account " + username + " does not exist!");
		if (!account.password.contentEquals(password))
			return AuthFailed("Incorrect password!");
		
		System.out.println("Login successful for account: " + username);
		super.createSession(account, request);
		return OK(account);
	}

	@Path(Config.REGISTRATION_PATH)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response register(UserAccount account, @Context HttpServletRequest request) {
		// If already logged in, deny
		if (super.getCurrentSession(request) != null) {
			System.out.println("This session is already logged in.");
			return ForbiddenRequest();	
		}
		
		if (account == null)
			return BadRequest();
		try {
			account.validate();
		}
		catch(IllegalArgumentException ex) {
			System.out.println("Attempt to create an account with bad field values.");
			return BadRequest();
		}
		
		UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
		// Only allow Guest creation through registration. 
		account.type = TypeOfUser.GUEST;
		UserAccount result = dao.create(account);
		// DAO will return null if an object with such a key already exists (same username)
		if (result == null)
			return AuthFailed("Account with this username already exists!");
		
		System.out.println("Creating account with the username: " + account.key);
		super.createSession(result, request);
		return OK(result);
	}

	@Path(Config.LOGOUT_PATH)
	@POST
	@Override
	public Response logOut(@Context HttpServletRequest request) {
		super.deleteSession(request);
		return OK("Logged out successfully.");
	}
}
