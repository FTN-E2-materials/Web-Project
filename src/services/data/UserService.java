package services.data;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.UserAccount;
import dao.UserDAO;
import services.templates.BaseService;
import storage.Storage;
import util.Config;


@Path(Config.USERS_DATA_PATH)
public class UserService extends BaseService {

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
										(Storage<UserAccount>)ctx.getAttribute(storageFileLocation)));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/** Returns a collection of all the users of the website. 
	 * Available only to administrators.
	 * @param request
	 * @return
	 */
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isAdmin()) {
			UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
			return OK(dao.getAll());
		}
		if (session.isHost()) {
			UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
			return OK(dao.getAll()); // TODO Add DAO method to search users 
		}
		// TODO Where can the host see his guests?
		return ForbiddenRequest();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(UserAccount updatedAccount, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (!session.getSessionID().equals(updatedAccount.key)) // Only the owner of the account can change the data 
			return ForbiddenRequest();
		try { updatedAccount.validate(); }
			catch (IllegalArgumentException ex) {
				System.out.println("Attempt to update an account with invalid field values.");
				return BadRequest();
			}
		
		UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
		return OK(dao.update(updatedAccount));
	}
	
}
