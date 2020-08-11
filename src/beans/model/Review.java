package beans.model;

import beans.interfaces.*;

public class Review implements BeanInterface {
   public String id;
   public String text;
   public int rate;
   public String authorID;
   public String apartmentID;
   public boolean visibleToGuests;

	@Override
	public String getKey() {
		return id;
	}
	
	@Override
	public void setKey(String key) {
		this.id = key;
	}
}