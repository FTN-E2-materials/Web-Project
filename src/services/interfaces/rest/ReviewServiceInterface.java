package services.interfaces.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import beans.model.entities.Review;
import util.wrappers.RequestWrapper;

public interface ReviewServiceInterface extends ResponseCRUDInterface<Review> {

	public Response getByApartmentID(@PathParam("id") String id, @Context HttpServletRequest request);
	public Response checkReviewPermission(RequestWrapper wrapper, @Context HttpServletRequest request);
	public Response hideReview(RequestWrapper wrapper, @Context HttpServletRequest request);
	public Response showReview(RequestWrapper wrapper, @Context HttpServletRequest request);
}
