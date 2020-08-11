package services;

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


@Path("/reviews")
public class ReviewService extends Service<Review, ReviewDAO> implements DatabaseServiceInterface{

	private String attributeString = "reviewDatabase";
	
	@Override
	@PostConstruct
	public void onCreate() {
		databaseAttributeString = attributeString;
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, new ReviewDAO());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Review create(Review review) {
		// TODO Check if user is authorised to create a review
		System.out.println("Creating a review with id " + review.id);
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return dao.create(review);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<Review> getAll() {
		// TODO Check if user is admin 
		return super.getAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Collection<Review> getByApartmentID(@PathParam("id") String id) {
		// TODO Check if user is Guest or unregistered
		// Then depending on the type of user, return filtered or not
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return dao.getByApartmentIDForGuest(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hideVisibility")
	/** Hide the review from Guests. Only a host can call this method */
	public Review hideReview(Review review) {
		// TODO Check if user is host
		
		// Check if the apartment exists, and if it does, whether it is this host's apartment.
		ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute("apartmentDatabase");
		Apartment apartment = apartmentDAO.getByKey(review.apartmentID);
		
		if (apartment == null)
			return null;
		if (apartment.hostID != "fetch host ID from request")
			return null;
		
		review.visibleToGuests = false;
		ReviewDAO reviewDAO = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return reviewDAO.update(review);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/showVisibility")
	/** Show the review to Guests. Only a host can call this method */
	public Review showReview(Review review) {
		// TODO Check if user is host
		
		// Check if the apartment exists, and if it does, whether it is this host's apartment.
		ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute("apartmentDatabase");
		Apartment apartment = apartmentDAO.getByKey(review.apartmentID);
		
		if (apartment == null)
			return null;
		if (apartment.hostID != "fetch host ID from request")
			return null;
		
		review.visibleToGuests = true;
		ReviewDAO reviewDAO = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return reviewDAO.update(review);
	}
}
