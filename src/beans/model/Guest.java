package beans.model;

import beans.model.enums.TypeOfUser;

// TODO Potentially reduntand
public class Guest extends UserAccount {
   
   public Guest() {
	   type = TypeOfUser.GUEST;
   }
}