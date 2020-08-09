package beans.model;

import beans.interfaces.BeanInterface;


public class Amenity implements BeanInterface{
	
   public String id;
   public String name;
   
	@Override
	public String getKey() {
		return id;
	}
}