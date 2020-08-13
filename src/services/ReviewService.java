package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Amenity;
import beans.model.Apartment;
import beans.model.Review;
import dao.ApartmentDAO;
import dao.ReviewDAO;
import storage.Storage;
import util.Config;


@Path("/reviews")
public class ReviewService extends Service<Review, ReviewDAO> implements DatabaseServiceInterface{

	@Override
	@PostConstruct
	public void onCreate() {
		databaseAttributeString = Config.reviewDatabaseString;
		storageFileLocation = Config.reviewsDataLocation;
		
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<Review>(Review.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new ReviewDAO(
										(Storage<Review>)ctx.getAttribute(storageFileLocation)
									));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Review create(Review review) {
		// TODO Only guests can create reviews
		// Guest has to have a FINISHED or REJECTED reservation with the apartment in question
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return dao.create(review);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<Review> getAll() {
		// TODO Check if user is admin, if not, reject 
		return super.getAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("apartment/{id}")
	public Collection<Review> getByApartmentID(@PathParam("id") String id) {
		// TODO Check if user is Guest or unregistered
		// Then depending on the type of user, return filtered or not
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return dao.getByApartmentIDForGuest(id);
		// return dao.getByApartmentIDForHost`(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/visibility")
	/** Toggle visibility status of the particular review object. */
	public Review toggleVisibility(Review review) {
		// TODO Check if user is host
		
		// Check if the apartment exists, and if it does, whether it is this host's apartment.
		ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute("apartmentDatabase");
		Apartment apartment = apartmentDAO.getByKey(review.apartmentID);
		
		if (apartment == null)
			return null;
		//TODO Fetch host id from request
		//if (apartment.hostID != "fetch host ID from request")
		//	return null;
		
		review.visibleToGuests = !review.visibleToGuests;
		ReviewDAO reviewDAO = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return reviewDAO.update(review);
	}
}
