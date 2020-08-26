package beans.model;

import beans.interfaces.SessionToken;
import beans.model.enums.TypeOfUser;

public class UserAccount extends DatabaseEntity implements SessionToken {
   
	// Id from DatabaseEntity acts as a username
   public String password;
   public Person person;
   public String email;
   public TypeOfUser type;
   
   public UserAccount() {
	   isCountable = false;
   }
   
   public TypeOfUser getType() {
	   return type;
   }
   
   @Override
   public void validate() {
   	// TODO Auto-generated method stub
   	
   }
   
   @Override 
   public String getSessionID() {
	   return id;
   }
   
   @Override
   public boolean isGuest() {
	   return type == TypeOfUser.GUEST;
   }
   
   @Override
   public boolean isHost() {
	   return type == TypeOfUser.HOST;
   }
   
   @Override
   public boolean isAdmin() {
	   return type == TypeOfUser.ADMINISTRATOR;
   }
}