package beans.model.entities;

import beans.model.DatabaseEntity;

public class Review extends DatabaseEntity<Review> {
	
   public String text;
   public int rate;
   public String authorID;
   public String apartmentID;
   public boolean visibleToGuests;
   
   @Override
   public void validate() {
   	// TODO Auto-generated method stub
   	
   }

	@Override
	public void updateAllowedFields(Review newEntity) {
		// TODO Auto-generated method stub
		
	}
}