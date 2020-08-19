package services.nav;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import util.Config;


@Path(Config.LANDING_PAGE_PATH)
public class LandingPageService extends PageNavigationService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response get(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return OK("You are not logged in. This is the landing page.");
		if (session.isGuest())
			return OK("You are a guest");
		if (session.isAdmin())
			return OK("You are an admin.");
		return OK("You are a host.");
	}

}
