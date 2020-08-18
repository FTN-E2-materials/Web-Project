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
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.Apartment;
import beans.model.UserAccount;
import beans.model.enums.ApartmentStatus;
import dao.ApartmentDAO;
import services.interfaces.CRUDServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import sun.security.provider.certpath.OCSPResponse.ResponseStatus;
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
	public Response create(Apartment apartment, @Context HttpServletRequest request) {
		try {
			apartment.validate();
		}
		catch (IllegalArgumentException ex) {
			System.out.println("Attempt to create apartment with invalid field values.");
			return BadRequest();
		}
		SessionToken session = super.getCurrentSession(request);
		if (session.isHost())
			return OK(super.create(apartment));
		
		return BadRequest();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/** Check if user is eligible to delete an apartment */
	public Response delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		// TODO Check is user a host/admin in order to delete
		// Maybe pass the whole Apartment? 
		
		return OK(super.delete(requestWrapper));
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Apartment apartment, @Context HttpServletRequest request) {
		try {
			apartment.validate();
		}
		catch (IllegalArgumentException ex) {
			System.out.println("Attempt to modify apartment with invalid field values.");
			return BadRequest();
		}
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
			return OK(dao.getActiveByHost(session.getSessionID()));
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
