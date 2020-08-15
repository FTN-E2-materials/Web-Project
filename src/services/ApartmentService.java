package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.Amenity;
import beans.model.Apartment;
import beans.model.Reservation;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import services.interfaces.DatabaseAccessInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path("/apartments")
public class ApartmentService extends CRUDService<Apartment, ApartmentDAO> {
	
	@Override
	@PostConstruct
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
	/** Check if user is eligible to create an apartment (if user is a host) */
	public Apartment create(Apartment apartment, @Context HttpServletRequest request) {
		// TODO Check if user is a host 
		System.out.println("Creating apartment...");
		return super.create(apartment, request);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	/** Check if user is eligible to delete an apartment */
	public Apartment delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		// TODO Check is user a host/admin in order to delete
		// Maybe pass the whole Apartment? 
		
		return super.delete(requestWrapper, request);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update")
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
	@Override
	public Collection<Apartment> getAll(@Context HttpServletRequest request) {
		// TODO Check if user is admin
		return super.getAll(request);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/active")
	/** Returns a collecton of all active apartments 
	 *  Available to guests, admin and unregistered users. */
	public Collection<Apartment> getActive() {
		// TODO Check if user is a guest, admin or unregistered
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		return dao.getActive();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/inactive")
	/** Returns a collection of inactive apartments.
	 *  Available only to admin.
	 */
	public Collection<Apartment> getInactive() {
		// TODO Check if user is admin
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		return dao.getInactive();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/host_active")
	/** Return a collection of active apartments for the given host */
	public Collection<Apartment> getActiveByHost(String hostID) {
		// TODO Can a guest see apartments for a host
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		return dao.getActiveByHost(hostID);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/host_inactive")
	/** Return a collection of inactive apartments for the given host */
	public Collection<Apartment> getInactiveByHost(String hostID) {
		// TODO Check if user is host
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		return dao.getInactiveByHost(hostID);
	}
}
