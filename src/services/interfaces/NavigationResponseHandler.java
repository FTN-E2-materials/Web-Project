package services.interfaces;

import javax.ws.rs.core.Response;

import util.Config;

/** Interface to provide basic page navigation HTTP Response methods (Forbidden and OK) */
public interface NavigationResponseHandler {
	public default Response ForbiddenRequest() { 
		return Response.status(Config.FORBIDDEN).build();
	}
	
	public default Response OK(Object object) {
		return Response.ok(object).build();
	}
}
