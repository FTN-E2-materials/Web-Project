package beans.model.entities;

import beans.model.template.DatabaseEntity;

public class Review extends DatabaseEntity {
	
   public String text;
   public int rating;
   public String guestID;
   public String apartmentID;
   public boolean visibleToGuests = true;
   
   @Override
   public void validate() {
   	// TODO Auto-generated method stub
   	
   }
}