package services.interfaces.util;

import java.net.URI;

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
	
	/** Redirects user to the given link */
	public default Response Redirect(String link) {
		URI url;
		try {
			url = new URI(link);
			return Response.seeOther(url).build();
		} 
		catch (Exception ex) {
			return Response.status(Config.NOT_FOUND).build();
		}
	}
}