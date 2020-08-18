package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.UserAccount;
import dao.UserDAO;
import services.templates.BaseService;
import storage.Storage;
import util.Config;


@Path("/users")
public class UserService extends BaseService {

	@Override
	@PostConstruct
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
										(Storage<UserAccount>)ctx.getAttribute(storageFileLocation)));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/** Returns a collection of all the users of the website. 
	 * Available only to administrators.
	 * @param request
	 * @return
	 */
	public Collection<UserAccount> getAll(@Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		
		if (currentUser == null)
			return new ArrayList<>();
		if (currentUser.isHost()) {
			UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
			return dao.getAll();
		}
		
		return new ArrayList<>();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserAccount update(UserAccount updatedAccount, @Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		
		if (currentUser == null)
			return null;
		if (!currentUser.id.equals(updatedAccount.id)) // Only the owner of the account can change the data 
			return null;
		try { updatedAccount.validate(); }
			catch (IllegalArgumentException ex) {
				System.out.println("Trying to update an existing entity with invalid field values.");
				return null;
			}
		
		UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
		return dao.update(updatedAccount);
	}
	
}
