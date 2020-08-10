package dao;

import java.util.ArrayList;
import java.util.Collection;

import beans.model.Review;


public class ReviewDAO extends BeanDAO<Review> {

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
	/** Returns a collection of reviews for the given apartment. Requested by host. */
	public Collection<Review> getByApartmentIDForHost(String apartmentID) {
		Collection<Review> reviews = new ArrayList<Review>();
		
		for (Review rev : database.values()) {
			if (rev.apartmentID.contentEquals(apartmentID))
				reviews.add(rev);
		}
		
		return reviews;
	}
	
	/** Returns a collection of reviews for the given apartment. Requested by guests.
	 *  Guests can only see those reviews which the host allowed. */
	public Collection<Review> getByApartmentIDForGuest(String apartmentID) {
		Collection<Review> reviews = new ArrayList<Review>();
		
		for (Review rev : database.values()) {
			if (rev.apartmentID.contentEquals(apartmentID)  &&  rev.visibleToGuests)
				reviews.add(rev);
		}
		
		return reviews;
	}
}
