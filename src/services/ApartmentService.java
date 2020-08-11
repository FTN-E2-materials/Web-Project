package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Amenity;
import beans.model.Apartment;
import dao.ApartmentDAO;
import util.Config;


@Path("/apartments")
public class ApartmentService extends Service<Apartment, ApartmentDAO> implements DatabaseServiceInterface {
	
	@Override
	@PostConstruct
	public void onCreate() {
		databaseAttributeString = Config.apartmentDatabaseString;
		
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, new ApartmentDAO());	
	}
	
	/** Test method. */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Amenity test(){
		Amenity amenity = new Amenity();
		amenity.id = "A1";
		amenity.name = "Amenity";
		
		return amenity;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	/** Check if user is eligible to create an apartment (if user is a host) */
	public Apartment create(Apartment apartment) {
		// TODO Check if user is a host 
		return super.create(apartment);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	/** Check if user is eligible to delete an apartment */
	public Apartment delete(String key) {
		// TODO Check is user a host/admin in order to delete
		// Maybe pass the whole Apartment? 
		
		return super.delete(key);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update")
	public Apartment update(Apartment apartment) {
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
	public Collection<Apartment> getAll() {
		// TODO Check if user is admin
		return super.getAll();
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
