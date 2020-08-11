package services;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Amenity;
import beans.model.Review;
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
		
		Review r = new Review();
		r.apartmentID = "Apartment118203";
		r.id = "review12390";
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		dao.create(r);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@Path("/")
	public Review create(Review review) {
		// TODO Check if user is authorised to create a review
		System.out.println("Creating a review with id " + review.id);
		ReviewDAO dao = (ReviewDAO)ctx.getAttribute(databaseAttributeString);
		return dao.create(review);
	}
	
}
