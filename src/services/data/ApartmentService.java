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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.entities.Apartment;
import beans.model.enums.ApartmentStatus;
import dao.ApartmentDAO;
import filters.ApartmentFilter;
import services.interfaces.rest.ApartmentServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.wrappers.ApartmentFilterWrapper;
import util.wrappers.RequestWrapper;


@Path(Config.APARTMENTS_DATA_PATH)
public class ApartmentService extends CRUDService<Apartment, ApartmentDAO> implements ApartmentServiceInterface{
	
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
	@Override
	public Response create(Apartment apartment, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()) {
			// Load the predefined values so the user cannot alter the rating, or create an account for another host 
			apartment.status = ApartmentStatus.INACTIVE;
			apartment.numberOfRatings = 0;
			apartment.rating = 0d;
			apartment.hostID = session.getUserID();
			
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
	@Override
	public Response delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest())
			return ForbiddenRequest();
		
		Apartment apartment = super.getByID(requestWrapper);
		
		if (apartment == null)
			return BadRequest();
		
		if (session.isHost()  &&  !session.getUserID().equals(apartment.hostID))
			return ForbiddenRequest();
		
		return OK(super.delete(requestWrapper));
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response update(Apartment apartment, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost()  &&  session.getUserID().equals(apartment.hostID))
			return OK(super.update(apartment));
		if (session.isAdmin())
			return OK(super.update(apartment));
		
		return ForbiddenRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		
		if (session == null)
			return OK(dao.getActive());
		if (session.isGuest())
			return OK(dao.getActive());
		if (session.isHost())
			return OK(dao.getByHost(session.getUserID()));	// TODO Return all host's apartments and filter them out using JS?
		if (session.isAdmin())
			return OK(dao.getAll());
		
		return BadRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Override
	public Response getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		Apartment apartment = super.getByID(key);
		SessionToken session = super.getCurrentSession(request);
		
		if (apartment == null)
			return BadRequest("Apartment not found.");
		if (apartment.status == ApartmentStatus.ACTIVE) {
			if (session == null)
				return OK(apartment);
			if (session.isHost()  &&  !session.getUserID().equals(apartment.hostID))
				return ForbiddenRequest();
			return OK(apartment);
		}
		if (apartment.status == ApartmentStatus.INACTIVE) {
			if (session == null)
				return ForbiddenRequest();
			if (session.isAdmin()  ||  session.getUserID().equals(apartment.hostID))
				return OK(apartment);
		}
		
		return ForbiddenRequest();
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response search(@QueryParam("name") String word, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		
		if (word == null)
			return BadRequest("Empty search query!");
		
		if (session == null)
			return OK(dao.searchAsVisitor(word));
			
		if (session.isAdmin())
			return OK(dao.searchAsAdmin(word));
		
		if (session.isHost())
			return OK(dao.searchAsHost(word, session.getUserID()));
		
		if (session.isGuest())
			return OK(dao.searchAsVisitor(word));
		
		else
			return BadRequest();
	}
	
	@PUT
	@Path("/activate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response activate(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest())
			return ForbiddenRequest();
		
		Apartment apartment = super.getByID(requestWrapper);
		
		if (apartment == null)
			return BadRequest();
		if (!apartment.hostID.equals(session.getUserID()))
			return ForbiddenRequest();
		
		apartment.status = ApartmentStatus.ACTIVE;
		return OK(super.update(apartment));
	}
	
	@PUT
	@Path("/deactivate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response deactivate(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isGuest())
			return ForbiddenRequest();
		
		if (requestWrapper == null)
			return BadRequest();
		if (requestWrapper.stringKey == null)
			return BadRequest();
		
		Apartment apartment = super.getByID(requestWrapper);
		
		if (apartment == null)
			return BadRequest();
		if (!apartment.hostID.equals(session.getUserID()))
			return ForbiddenRequest();
		
		apartment.status = ApartmentStatus.INACTIVE;
		return OK(super.update(apartment));
	}
	
	@POST 
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response filter(ApartmentFilterWrapper wrapper, @Context HttpServletRequest request) {
		ApartmentFilter filter;
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			filter = ApartmentFilter.instantiateWithData(dao.getActive());
		else if(session.isGuest())
			filter = ApartmentFilter.instantiateWithData(dao.getActive());
		else if (session.isHost())
			filter = ApartmentFilter.instantiateWithData(dao.getActiveByHost(session.getUserID()));
		else {
			return ForbiddenRequest();
		}

		if (wrapper.isCityValid()) 
			filter.filterByCity(wrapper.city);
		
		if (wrapper.numOfGuests != null) 
			filter.filterByNumberOfGuests(wrapper.numOfGuests);
		
		if (wrapper.arePricesValid())
			filter.filterByPriceRange(wrapper.minPrice, wrapper.maxPrice);
		
		if (wrapper.areRoomNumbersValid())
			filter.filterByNumberOfRooms(wrapper.minRooms, wrapper.maxRooms);
		// dates missing
		
		return OK(filter.getResults());
	}
}
