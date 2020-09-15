package beans.model.entities;

import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;

public class Image extends DatabaseEntity {

	public String base64_string;
	
	@Override
	public void validate() throws EntityValidationException {
		// TODO Auto-generated method stub
	}

}
