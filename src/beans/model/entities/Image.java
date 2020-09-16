package beans.model.entities;

import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;

public class Image extends DatabaseEntity {

	public String base64_string;
	
	@Override
	public void validate() throws EntityValidationException {
		if (base64_string == null)
			throw new EntityValidationException("Image must not be null");
		if (base64_string.isEmpty())
			throw new EntityValidationException("Image nonexistent.");
	}
}
