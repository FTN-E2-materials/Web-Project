package beans.model.entities;

import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;

public class Amenity extends DatabaseEntity {
	
   public String name;

	@Override
	public void validate() throws EntityValidationException {
		// TODO Auto-generated method stub
		
	}
}