package services.interfaces;

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
		} 
		catch (Exception ex) {
			return Response.status(Config.NOT_FOUND).build();
		}
		return Response.seeOther(url).build();
	}
}
