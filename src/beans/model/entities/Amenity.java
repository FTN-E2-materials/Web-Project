package beans.model.entities;

import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;

public class Amenity extends DatabaseEntity {
	
   public String name;

	@Override
	public void validate() throws EntityValidationException {
		if (name == null)
			throw new EntityValidationException("Amenity name cannot be empty");
		if (name.isEmpty())
			throw new EntityValidationException("Amenity name cannot be null");
	}
}