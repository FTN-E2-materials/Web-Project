package services;

import java.util.ArrayList;
import java.util.Collection;

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

import beans.model.Apartment;
import beans.model.UserAccount;
import beans.model.enums.ApartmentStatus;
import dao.ApartmentDAO;
import services.interfaces.CRUDServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path("/apartments")
public class ApartmentService extends CRUDService<Apartment, ApartmentDAO> implements CRUDServiceInterface<Apartment>{
	
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
	public Apartment create(Apartment apartment, @Context HttpServletRequest request) {
		// TODO Check if user is a host 
		return super.create(apartment);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/** Check if user is eligible to delete an apartment */
	public Apartment delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		// TODO Check is user a host/admin in order to delete
		// Maybe pass the whole Apartment? 
		
		return super.delete(requestWrapper);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment update(Apartment apartment, @Context HttpServletRequest request) {
		// TODO Fetch auth to see which host it is
		// Host can only change their own apartments
		// If it is an admin, allow it 
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		Apartment existingApartment = dao.getByKey(apartment.id);
		
		if (existingApartment == null)
			return null;
		if (!existingApartment.hostID.equals(apartment.hostID))
			return null;
		
		return dao.update(apartment);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAll(@Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		
		if (currentUser == null)
			return dao.getActive();
		if (currentUser.isGuest())
			return dao.getActive();
		if (currentUser.isHost())
			return dao.getActiveByHost(currentUser.id);
		if (currentUser.isAdmin())
			return dao.getAll();
		
		return new ArrayList<>();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Apartment getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		Apartment apartment = super.getByID(key);
		UserAccount currentUser = super.getCurrentUser(request);
		
		if (apartment == null)
			return null;
		if (apartment.status == ApartmentStatus.ACTIVE) {
			if (currentUser == null)
				return apartment;
			if (currentUser.isHost()  &&  !currentUser.id.equals(apartment.id))
				return null;
			return apartment;
		}
		if (apartment.status == ApartmentStatus.INACTIVE) {
			if (currentUser == null)
				return null;
			if (currentUser.isAdmin()  ||  currentUser.id.equals(apartment.id))
				return apartment;
		}
		
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/inactive")
	/** Returns a collection of inactive apartments.
	 *  Only available to hosts.
	 */
	public Collection<Apartment> getInactive(@Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		
		if (currentUser == null)
			return new ArrayList<>();
		if (currentUser.isHost())
			return dao.getInactiveByHost(currentUser.id);
		
		return new ArrayList<>();
	}
}
