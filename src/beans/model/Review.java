package beans.model;

import beans.interfaces.*;

public class Review extends DatabaseEntity {
	
   public String text;
   public int rate;
   public String authorID;
   public String apartmentID;
   public boolean visibleToGuests;
}