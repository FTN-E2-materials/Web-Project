package dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import beans.model.Review;


public class ReviewDAO extends BeanDAO<Review> {

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
	/** Returns a collection of reviews for the given apartment. Requested by host.
	 *  All reviews will be visible, whether hidden or not. */
	public Collection<Review> getByApartmentIDForHost(String apartmentID) {
		Collection<Review> reviews = new ArrayList<Review>();
		
		for (Review rev : database.values()) {
			if (rev.apartmentID.equals(apartmentID))
				reviews.add(rev);
		}
		
		return reviews;
	}
	
	/** Returns a collection of reviews for the given apartment. Requested by guests.
	 *  Guests can only see those reviews which the host allowed. */
	public Collection<Review> getByApartmentIDForGuest(String apartmentID) {
		Collection<Review> reviews = new ArrayList<Review>();
		
		for (Review rev : database.values()) {
			if (rev.apartmentID.equals(apartmentID)  &&  rev.visibleToGuests)
				reviews.add(rev);
		}
		
		return reviews;
	}
}
