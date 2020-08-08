package beans.model;

import beans.interfaces.*;

public class Review implements BeanInterface {
   public String id;
   public String text;
   public int rate;
   public String authorID;
   public String apartmentID;

	@Override
	public String getKey() {
		return id;
	}
}