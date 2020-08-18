package beans.model;

import beans.model.enums.TypeOfUser;

//TODO Potentially reduntand
public class Administrator extends UserAccount {
	
	public Administrator() {
		type = TypeOfUser.ADMINISTRATOR;
	}
}