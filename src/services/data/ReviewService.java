package services.data;

import java.util.ArrayList;
import java.util.Collection;

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
import beans.model.Review;
import dao.ReviewDAO;
import services.interfaces.ResponseCRUDInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path(Config.REVIEWS_DATA_PATH)
public class ReviewService extends CRUDService<Review, ReviewDAO> implements ResponseCRUDInterface<Review> {

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
	public Response create(Review review, @Context HttpServletRequest request) {
		// TODO Only guests can create reviews
		// Guest has to have a FINISHED or REJECTED reservation with the apartment in question
		return OK(super.create(review));
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Review obj, @Context HttpServletRequest request) {
		return OK(super.update(obj));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session.isAdmin())
			return OK(super.getAll());
		return ForbiddenRequest();
	}

	public Response getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		throw new NotAcceptableException();
		// This is not allowed for reviews
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(Review review, @Context HttpServletRequest request) {
		return OK(super.delete(review));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getByApartmentID(@PathParam("id") String id) {
		// TODO Check if user is Guest or unregistered
		// Then depending on the type of user, return filtered or not
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return OK(dao.getByApartmentIDForGuest(id));
	}
}
