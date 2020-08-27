package services.data;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.Apartment;
import beans.model.enums.ApartmentStatus;
import dao.ApartmentDAO;
import services.interfaces.ResponseCRUDInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path(Config.APARTMENTS_DATA_PATH)
public class ApartmentService extends CRUDService<Apartment, ApartmentDAO> implements ResponseCRUDInterface<Apartment>{
	
	@PostConstruct
	@Override
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}
	
	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.apartmentDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.apartmentsDataLocation;
	}

	@Override
	public void initAttributes() {
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<Apartment>(Apartment.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new ApartmentDAO(
										(Storage<Apartment>)ctx.getAttribute(storageFileLocation)
									));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/** Check if user is eligible to create an apartment (if user is a host) */
	public Response create(Apartment apartment, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()) {
			// Load the predefined values so the user cannot alter the rating, or create an account for another host 
			apartment.status = ApartmentStatus.INACTIVE;
			apartment.numberOfRatings = 0;
			apartment.rating = 0d;
			apartment.hostID = session.getSessionID();
			
			Apartment validatedApartment = super.create(apartment);
			if (validatedApartment == null)
				return BadRequest("Please fill out all the fields correctly");
			
			System.out.println("Creating apartment: " + validatedApartment.title);
			return OK(validatedApartment);
		}

		return ForbiddenRequest();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/** Check if user is eligible to delete an apartment */
	public Response delete(Apartment apartment, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		// TODO Does it need to be this complicated?
		if (session.isAdmin()) {
			Apartment deletedApartment = super.delete(apartment);
			if (deletedApartment == null)
				return BadRequest();
			return OK(deletedApartment);
		}
		if (session.isHost()) {
			if (apartment == null)
				return BadRequest();
			if (!apartment.hostID.equals(session.getSessionID()))
				return ForbiddenRequest();
			
			// Check if there is an apartment which such ID to be deleted
			Apartment deletedApartment = super.delete(apartment);
			if (deletedApartment == null)
				return BadRequest();
			return OK(deletedApartment);
		}
		
		return ForbiddenRequest();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Apartment apartment, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()  &&  session.getSessionID().equals(apartment.hostID))
			return OK(super.update(apartment));
		if (session.isAdmin())
			return OK(super.update(apartment));
		
		return ForbiddenRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		
		if (session == null)
			return OK(dao.getActive());
		if (session.isGuest())
			return OK(dao.getActive());
		if (session.isHost())
			return OK(dao.getByHost(session.getSessionID()));	// TODO Return all host's apartments and filter them out using JS?
		if (session.isAdmin())
			return OK(dao.getAll());
		
		return BadRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		Apartment apartment = super.getByID(key);
		SessionToken session = super.getCurrentSession(request);
		
		if (apartment == null)
			return OK(null);
		if (apartment.status == ApartmentStatus.ACTIVE) {
			if (session == null)
				return OK(apartment);
			if (session.isHost()  &&  !session.getSessionID().equals(apartment.hostID))
				return OK(null);
			return OK(apartment);
		}
		if (apartment.status == ApartmentStatus.INACTIVE) {
			if (session == null)
				return OK(null);
			if (session.isAdmin()  ||  session.getSessionID().equals(apartment.hostID))
				return OK(apartment);
		}
		
		return OK(null);
	}
/* Could be solved with JS filter functions on getAll for hosts? 	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/inactive")
	public Collection<Apartment> getInactive(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		
		if (session == null)
			return new ArrayList<>();
		if (session.isHost())
			return dao.getInactiveByHost(session.getSessionID());
		
		return new ArrayList<>();
	} */
}
