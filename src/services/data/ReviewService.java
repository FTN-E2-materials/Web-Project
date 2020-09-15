package services.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.entities.Apartment;
import beans.model.entities.Reservation;
import beans.model.entities.Review;
import beans.model.enums.ReservationStatus;
import beans.model.other.Date;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.ReviewDAO;
import services.interfaces.rest.ReviewServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.exceptions.EntityValidationException;
import util.wrappers.RequestWrapper;


@Path(Config.REVIEWS_SERVICE_PATH)
public class ReviewService extends CRUDService<Review, ReviewDAO> implements ReviewServiceInterface {

	@PostConstruct
	@Override
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}
	
	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.reviewDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.reviewsDataLocation;
	}

	@Override
	public void initAttributes() {
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
	public Response create(Review review, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest()) {
			String apartmentID = review.apartmentID;
			if (apartmentID == null) 
				return BadRequest("Please attach a key to the review object.");
			
			 ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
			 Apartment apartment = apartmentDAO.getByKey(apartmentID);
			 if (apartment == null)
				 return BadRequest("Apartment not found");
			
			if (hasPermission(apartmentID, session.getUserID())) {
				 review.apartmentID = apartment.key;
				 review.guestID = session.getUserID();
				 
				 try {
					 Review validatedReview = super.create(review);
					 apartmentDAO.updateRating(validatedReview);
					 return OK(validatedReview);
				 }
				 catch (EntityValidationException e) {
					 return BadRequest(e.message);
				 }
			}
		}
		
		return ForbiddenRequest();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response update(Review obj, @Context HttpServletRequest request) {
		return NotAllowed();	// Updating reviews is not allowed
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		
		if (session.isAdmin())
			return OK(super.getAll());
		return ForbiddenRequest();
	}

	@Override
	@Path("/{reviewID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByID(@PathParam("reviewID") String key, @Context HttpServletRequest request) {
		return ForbiddenRequest();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		return ForbiddenRequest();  // No deleting reviews?
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{apartmentID}")
	@Override
	public Response getByApartmentID(@PathParam("apartmentID") String apartmentID, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);

		if (session == null)
			return OK(dao.getByApartmentIDForGuest(apartmentID));
		if (session.isGuest())
			return OK(dao.getByApartmentIDForGuest(apartmentID));
		
		return OK(dao.getByApartmentIDForHost(apartmentID));
	}
	
	@Override
	@Path("/hide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response hideReview(RequestWrapper wrapper, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()) {
			if (wrapper == null)
				return BadRequest("Missing request wrapper.");
			if (wrapper.stringKey == null)
				return BadRequest("Missing review ID.");
			
			ReviewDAO reviewDAO = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
			Review review = reviewDAO.getByKey(wrapper.stringKey);
			if (review == null)
				return BadRequest("Review not found");
			
			ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
			Apartment reviewedApartment = apartmentDAO.getByKey(review.apartmentID);
			if (reviewedApartment == null)
				return BadRequest("Apartment for that review was not found.");
			
			if (!reviewedApartment.hostID.equals(session.getUserID()))
				return ForbiddenRequest();
			
			review.visibleToGuests = false;
			return OK(reviewDAO.update(review));
		}
		
		return ForbiddenRequest();
	}

	@Override
	@Path("/show")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response showReview(RequestWrapper wrapper, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()) {
			if (wrapper == null)
				return BadRequest("Missing request wrapper.");
			if (wrapper.stringKey == null)
				return BadRequest("Missing review ID.");
			
			ReviewDAO reviewDAO = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
			Review review = reviewDAO.getByKey(wrapper.stringKey);
			if (review == null)
				return BadRequest("Review not found");
			
			ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
			Apartment reviewedApartment = apartmentDAO.getByKey(review.apartmentID);
			if (reviewedApartment == null)
				return BadRequest("Apartment for that review was not found.");
			
			if (!reviewedApartment.hostID.equals(session.getUserID()))
				return ForbiddenRequest();
			
			review.visibleToGuests = true;
			return OK(reviewDAO.update(review));
		}
		
		return ForbiddenRequest();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/permission")
	public Response checkReviewPermission(RequestWrapper wrapper, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest()) {
			if (wrapper == null)
				return BadRequest();
			if (wrapper.stringKey == null)
				return BadRequest();
			
			String apartmentID = wrapper.stringKey;
			if (apartmentID.isEmpty()) 
				return BadRequest();
			
			if (hasPermission(apartmentID, session.getUserID()))
				return OK("You are fit to create a review.");
			
			return BadRequest("You do not have a fitting reservation for this apartment.");
		}
		
		return ForbiddenRequest();
	}
	
	/** Check whether the given user has a permission to create a review for the given apartment 
	 * @param apartmentID
	 * @param guestID
	 * @return True if the user can create a review, false if not.
	 */
	private boolean hasPermission(String apartmentID, String guestID) {
		ReservationDAO reservationDAO = (ReservationDAO)ctx.getAttribute(Config.reservationDatabaseString);
		for (Reservation reservation : reservationDAO.getByApartmentAndGuest(apartmentID, guestID)) {
			if (reservation.isFinished()  ||  reservation.isDenied()) {
				 return true;
			}
		}	// TODO Allow only 1 review per person? 
		return false;
	}
}
