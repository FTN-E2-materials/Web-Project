package beans.model;

import java.util.ArrayList;

public class Guest extends UserAccount {
   public ArrayList<Review> reviews;
   
   public Guest() {
	   reviews = new ArrayList<Review>();
   }
}