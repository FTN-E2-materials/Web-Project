package services;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.UserAccount;
import dao.UserDAO;
import storage.Storage;
import util.Config;


@Path("/users")
public class UserService extends Service<UserAccount, UserDAO> implements DatabaseServiceInterface {

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

}
