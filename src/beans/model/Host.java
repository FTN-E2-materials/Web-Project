package beans.model;

import beans.model.enums.TypeOfUser;

//TODO Potentially reduntand
public class Host extends UserAccount {
   public int rating;
   
   public Host() {
	   type = TypeOfUser.HOST;
	   rating = 0;	// If rating is 0, means the host does not have any reviews yet.
   }
}