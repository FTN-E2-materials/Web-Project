package services.interfaces.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import beans.model.entities.Reservation;
import util.wrappers.RequestWrapper;

public interface ReservationServiceInterface extends ResponseCRUDInterface<Reservation> {
	
	public Response cancel(RequestWrapper requestWrapper, @Context HttpServletRequest request);
	public Response approve(RequestWrapper requestWrapper, @Context HttpServletRequest request);
	public Response finish(RequestWrapper requestWrapper, @Context HttpServletRequest request);
}
