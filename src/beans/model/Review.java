package beans.model;

import beans.interfaces.*;

public class Review implements BeanInterface {
   public String id;
   public String text;
   public int rate;
   public String author;
   
   // TODO Apartment identification via ID? Unnecessary JSON send of the whole apartment object
   // Or, a Preview object with {name, id}
   public Apartment apartment;

	@Override
	public String getKey() {
		return id;
	}
}