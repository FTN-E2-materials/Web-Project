package beans.model;


public class UserAccount extends DatabaseEntity {
   
	// Id from DatabaseEntity acts as a username
   public String password;
   public Person person;
   protected TypeOfUser type;
   
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
   
   public boolean isGuest() {
	   return type == TypeOfUser.GUEST;
   }
   
   public boolean isHost() {
	   return type == TypeOfUser.HOST;
   }
   
   public boolean isAdmin() {
	   return type == TypeOfUser.ADMINISTRATOR;
   }
}