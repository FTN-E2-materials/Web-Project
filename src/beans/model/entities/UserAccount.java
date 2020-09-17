package beans.model.entities;

import beans.interfaces.SessionToken;
import beans.model.enums.TypeOfUser;
import beans.model.other.Person;
import beans.model.template.DatabaseEntity;

public class UserAccount extends DatabaseEntity implements SessionToken {
   
	// Id from DatabaseEntity acts as a username
   public String password;
   public Person person;
   public String email;
   public TypeOfUser type = TypeOfUser.GUEST;
   public Boolean isBlocked;
   
   public UserAccount() {
	   isCountable = false;
	   isBlocked = false;
   }
   
   @Override 
   public String getUserID() {
	   return key;
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
   
   @Override
   public void validate() {
   	// TODO Auto-generated method stub
   	
   }
}