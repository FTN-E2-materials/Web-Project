package beans.model;
import beans.interfaces.BeanInterface;


public abstract class UserAccount extends DatabaseEntity {
   
	// Id from DatabaseEntity acts as a username
   public String password;
   public Person person;
   protected TypeOfUser type;
   
   public TypeOfUser getType() {
	   return type;
   }
}