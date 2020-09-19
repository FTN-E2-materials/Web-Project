package dao.interfaces;

import java.util.Collection;

import beans.model.entities.Review;

public interface ReviewDAOInterface {

	public Collection<Review> getByApartmentIDForHost(String apartmentID);
	public Collection<Review> getByApartmentIDForGuest(String apartmentID);
}
