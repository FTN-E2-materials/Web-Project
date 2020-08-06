package beans.model;
import beans.interfaces.BeanInterface;


public abstract class UserAccount implements BeanInterface {
   
   public String username;
   public String password;
   public Person person;
   
   @Override
	public String getKey() {
		return username;
	}
}