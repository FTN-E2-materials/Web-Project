package services.interfaces;

import javax.ws.rs.core.Response;

import util.Config;

/** Interface with default data HTTP Response methods (Badrequest, Forbidden, OK) 
 *  Should be used in REST services where users interact with the server data. */
public interface DataResponseHandler extends NavigationResponseHandler{
	public default Response BadRequest() {
		return Response.status(Config.BAD_REQUEST).build();
	}
	
	public default Response BadRequest(Object object) {
		return Response.status(Config.BAD_REQUEST).entity(object).build();
	}
	
	public default Response ForbiddenRequest(Object object) {
		return Response.status(Config.FORBIDDEN).build();
	}
	
	public default Response AuthFailed(Object object) {
		return Response.status(Config.AUTH_FAILED).entity(object).build();
	}
	
	public default Response NotFound(Object object) {
		return Response.status(Config.NOT_FOUND).entity(object).build();
	}
}
