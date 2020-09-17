package dao;

import beans.model.entities.UserAccount;
import storage.Storage;
import util.Config;


public class UserDAO extends BeanDAO<UserAccount> {

	public UserDAO(Storage<UserAccount> storage) {
		super(storage);
		init();
	}

	@Override
	protected void init() {
		idHeader = Config.userIdheader;
	}	
}
