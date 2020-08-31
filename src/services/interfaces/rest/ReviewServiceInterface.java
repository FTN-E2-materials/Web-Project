package services.interfaces.rest;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import beans.model.Review;

public interface ReviewServiceInterface extends ResponseCRUDInterface<Review> {

	public Response getByApartmentID(@PathParam("id") String id);
}
