package services.data;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.entities.UserAccount;
import beans.model.enums.TypeOfUser;
import dao.UserDAO;
import services.interfaces.rest.UserServiceInterface;
import services.templates.BaseService;
import storage.Storage;
import util.Config;
import util.exceptions.EntityValidationException;


@Path(Config.USERS_SERVICE_PATH)
public class UserService extends BaseService implements UserServiceInterface {

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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createHost(UserAccount host, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isAdmin()) {
			try {
					host.type = TypeOfUser.HOST;
					host.validate();
					UserDAO dao = (UserDAO)ctx.getAttribute(databaseAttributeString);
	
					UserAccount createdAccount = dao.create(host);
					if (createdAccount == null)
						throw new EntityValidationException("Host with that username already exists!");
					return OK(createdAccount);
			}
			catch (EntityValidationException e) {
				return BadRequest(e.message);
			}
		}
		
		return ForbiddenRequest();
	}
	
	/** Returns a collection of all the users of the website. 
	 * Available only to administrators.
	 * @param request
	 * @return Response with JSON-packaged list of all users if the client is an admin.
	 * Forbidden request otherwise.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
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
	
	@GET
	@Path("/id/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByID(@PathParam("userID") String userID, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest())
			return ForbiddenRequest();
		
		UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
		return OK(dao.getByKey(userID));
	}
	
	@GET
	@Path("/self")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSelf(@Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()) {
			UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
			return OK(dao.getByKey(session.getUserID()));
		}
		if (session.isGuest()) {
			UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
			return OK(dao.getByKey(session.getUserID()));
		}
		return ForbiddenRequest();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(UserAccount updatedAccount, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		
		try { updatedAccount.validate(); }
		catch (IllegalArgumentException ex) {
			System.out.println("Attempt to update an account with invalid field values.");
			return BadRequest();
		}
		
		if (!session.getUserID().equals(updatedAccount.key)) {// Only the owner of the account can change the data 
			System.out.println("Attempt from " + session.getUserID() + " to update " + updatedAccount.key);
			return ForbiddenRequest();
		}
		
		if (session.isGuest()) updatedAccount.type = TypeOfUser.GUEST;
		else if (session.isHost()) updatedAccount.type = TypeOfUser.HOST;
		
		UserDAO dao = (UserDAO)ctx.getAttribute(Config.userDatabaseString);
		return OK(dao.update(updatedAccount));
	}
	
}
