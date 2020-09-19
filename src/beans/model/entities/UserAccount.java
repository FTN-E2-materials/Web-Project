package beans.model.entities;

import beans.interfaces.SessionToken;
import beans.model.enums.TypeOfUser;
import beans.model.other.Person;
import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;

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
   public void validate() throws EntityValidationException {
	   if (password == null)
		   throw new EntityValidationException("Password cannot be empty");
	   if (password.length() < 6)
		   throw new EntityValidationException("Password must be longer than 5 characters");
	   if (key == null)
		   throw new EntityValidationException("Username cannot be empty");
	   if (key.length() < 4)
		   throw new EntityValidationException("Username must be longer than 3 characters");
	   if (email == null)
		   throw new EntityValidationException("Email cannot be empty");
	   if (email.length() < 7)	// Regex check here instead of this
		   throw new EntityValidationException("Please enter a valid email");
	   if (person.firstName == null)
		   throw new EntityValidationException("Name cannot be empty");
	   if (person.firstName.length() < 3)
		   throw new EntityValidationException("Name must be at least 3 characters long");
	   if (person.lastName == null)
		   throw new EntityValidationException("Last name cannot be empty");
	   if (person.lastName.length() < 3)
		   throw new EntityValidationException("Last name must be at least 3 characters long");
	   if (person.gender == null)
		   throw new EntityValidationException("Please select a gender");
   }
}