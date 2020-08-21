package services.nav;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import services.interfaces.NavigationResponseHandler;
import services.interfaces.SessionTracker;
import util.Config;
import util.ScriptService;

/** Abstract class for page navigation services. Implements the session tracker methods for tracking
 *  user sessions, and the basic navigation HTTP response methods for forbidding and allowing access to pages.
 *  Contains GET method for fetching website pages.
 *  @author Nikola
 */
@Path("/")
public class PageNavigationService implements SessionTracker, NavigationResponseHandler {

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
			default:
				return OK("There is nothing here, go back.");
		}
	}
	
// Scripts
	/** Used for fetching CSS/JS scripts that are stored locally 
	 *  Url is: 'localhost.webproject/scripts/scriptName'*/
	@GET
	@Path(Config.SCRIPTS_PATH + "/{scriptName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScript(@PathParam("scriptName") String scriptName) {
		return OK(ScriptService.getScript(scriptName));
	}
	
// Page navigation 
	
	private Response LandingPage(HttpServletRequest request) {
		InputStream stream = PageNavigationService.class.getResourceAsStream(Config.LANDING_PAGE_FILE_LOCATION);
		if (stream == null)
			return OK("The file was not found, sorry.");
		return OK(stream);
	}
	
	private Response LoginPage(HttpServletRequest request) {
		return OK("Hello this is the login page.");
	}
	
	private Response RegistrationPage(HttpServletRequest request) {
		return OK("Hello this is the registration page.");
	}
	
	private Response GuestHome(HttpServletRequest request) {
		return OK("Hello this is the guest home page.");
	}
	
	private Response HostHome(HttpServletRequest request) {
		return OK("Hello this is the registration page.");
	}
	
	private Response AdminHome(HttpServletRequest request) {
		return OK("Hello this is the registration page.");
	}
}
