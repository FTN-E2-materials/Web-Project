package beans.model.entities;

import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;

public class Review extends DatabaseEntity {
	
   public String text;
   public int rating;
   public String guestID;
   public String apartmentID;
   public boolean visibleToGuests = true;
   
   @Override
   public void validate() throws EntityValidationException {
   		if (text == null)
   			throw new EntityValidationException("Please enter text.");
		if (text.isEmpty())
			throw new EntityValidationException("Please enter text.");
		if (rating < 1  ||  rating > 5)
			throw new EntityValidationException("Rating must be between 1 and 5");
		if (apartmentID == null)
			throw new EntityValidationException("No apartment id");
		if (apartmentID.isEmpty())
			throw new EntityValidationException("No apartment id");
		if (guestID == null)
			throw new EntityValidationException("No guest id");
		if (guestID.isEmpty())
			throw new EntityValidationException("No guest id");
   }
}