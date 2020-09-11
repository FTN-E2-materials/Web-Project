package services.nav;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import services.interfaces.util.HttpResponseHandler;
import services.interfaces.util.SessionTracker;
import util.Config;
import util.services.HTMLService;
import util.services.ScriptService;

/** Abstract class for page navigation services. Implements the session tracker methods for tracking
 *  user sessions, and the basic navigation HTTP response methods for forbidding and allowing access to pages.
 *  Contains GET method for fetching website pages.
 *  @author Nikola
 */
@Path("/")
public class PageNavigationService implements SessionTracker, HttpResponseHandler {

// Default path lands on Landing page, or Home if user is logged in
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getLanding(@Context HttpServletRequest request) {
		return LandingPage(request);
	}
	
// PathParams for navigation using URL
	@GET
	@Path("/{pagePath}")
	@Produces(MediaType.TEXT_HTML)
	public Response getSubpage(@PathParam("pagePath") String pagePath, @Context HttpServletRequest request) {
		switch (pagePath) {
			case Config.LOGIN_PAGE_PATH:
				return LoginPage(request);
			case Config.REGISTRATION_PAGE_PATH:
				return RegistrationPage(request);
			case Config.CREATE_APARTMENT_PAGE_PATH:
				return CreateApartmentPage(request);
			case Config.ACCOUNT_PAGE_PATH:
				return AccountPage(request);
				
			default:
				return OK("There is nothing here, go back.");
		}
	}
	
// Scripts
	/** Used for fetching JS scripts that are stored locally 
	 *  Url is: 'localhost.webproject/scripts/scriptName'*/
	@GET
	@Path(Config.SCRIPTS_PATH + "/{scriptName}")
	@Produces(MediaType.TEXT_HTML)
	public Response getScript(@PathParam("scriptName") String scriptName) {
		return OK(ScriptService.getScript(scriptName));
	}
	
	@GET
	@Path(Config.APARTMENT_PAGE_PATH + "{apartmentID}")
	@Produces(MediaType.TEXT_HTML)
	public Response ApartmentPage(@PathParam("apartmentID") String apartmentID, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return OK(HTMLService.getInstance().getSingleApartmentPageGuest());
		if (session.isGuest())
			return OK(HTMLService.getInstance().getSingleApartmentPageGuest());
		
		return OK(HTMLService.getInstance().getSingleApartmentPageHost());
	}
	
	@GET 
	@Path(Config.EDIT_APARTMENT_PAGE_PATH + "{apartmentID}")
	@Produces(MediaType.TEXT_HTML)
	public Response EditApartment(@PathParam("apartmentID") String apartmentID, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest())
			return ForbiddenRequest();
		
		return OK(HTMLService.getInstance().getEditApartmentPage());
	}
	
	@GET
	@Path("reviews/{apartmentID}")
	@Produces(MediaType.TEXT_HTML)
	public Response Reviews(@PathParam("apartmentID") String apartmentID, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		if (session == null)
			return OK(HTMLService.getInstance().getReviewsForGuestPage());
		if (session.isGuest())
			return OK(HTMLService.getInstance().getReviewsForGuestPage());
		
		return OK(HTMLService.getInstance().getReviewsForHostPage());
	}
	
	
// Page navigation 
//______________________
	private Response LandingPage(HttpServletRequest request) {
		if (!isLoggedIn(request)) 
			return OK(HTMLService.getInstance().getLandingPage());
		
		return HomePage(request);
	}
	
	private Response HomePage(HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session.isGuest()) {
			return OK(HTMLService.getInstance().getGuestHomePage());
		}
		if (session.isHost()) {
			return OK(HTMLService.getInstance().getHostHomePage());
		}
		if (session.isAdmin()) {
			return OK(HTMLService.getInstance().getAdminHomePage());
		}
		
		return ForbiddenRequest();
	}
	
	private Response LoginPage(HttpServletRequest request) {
		if (!isLoggedIn(request))
			return OK(HTMLService.getInstance().getLoginPage());

		return Redirect("http://localhost:8080/WebProject");
	}
	
	private Response RegistrationPage(HttpServletRequest request) {
		if (!isLoggedIn(request)) 
			return OK(HTMLService.getInstance().getRegistrationPage());
		
		return Redirect("http://localhost:8080/WebProject");
	}
	
	private Response CreateApartmentPage(HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost())
			return OK(HTMLService.getInstance().getCreateApartmentPage());
		
		return ForbiddenRequest();
	}
	
	private Response AccountPage(HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest())
			return OK(HTMLService.getInstance().getGuestAccountPage());
		if (session.isHost())
			return OK(HTMLService.getInstance().getHostAccountPage());
		
		return ForbiddenRequest();
	}
}
