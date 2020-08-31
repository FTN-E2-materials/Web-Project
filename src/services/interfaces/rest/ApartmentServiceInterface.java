package services.interfaces.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import beans.model.Apartment;
import services.interfaces.rest.ResponseCRUDInterface;
import util.wrappers.ApartmentFilterWrapper;
import util.wrappers.RequestWrapper;


public interface ApartmentServiceInterface extends ResponseCRUDInterface<Apartment>{

	public Response search(@QueryParam("name") String word, @Context HttpServletRequest request);
	public Response activate(RequestWrapper requestWrapper, @Context HttpServletRequest request);
	public Response deactivate(RequestWrapper requestWrapper, @Context HttpServletRequest request);
	public Response filter(ApartmentFilterWrapper wrapper, @Context HttpServletRequest request);
}
