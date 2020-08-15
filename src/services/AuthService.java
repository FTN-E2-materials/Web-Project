package services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import beans.model.Apartment;
import beans.model.UserAccount;
import dao.ApartmentDAO;
import dao.UserDAO;
import services.interfaces.AuthServiceInterface;
import services.templates.BaseService;
import storage.Storage;
import util.Config;

@Path("/auth")
public class AuthService extends BaseService<UserAccount, UserDAO>implements AuthServiceInterface {

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
	
	@Override
	public void login(String username, String password, @Context HttpServletRequest request) {
		
	}

	@Override
	public void register(UserAccount account, @Context HttpServletRequest request) {
		
	}

	@Override
	public void logOut(@Context HttpServletRequest request) {
		request.setAttribute(Config.userSessionAttributeString, null);
	}
}
