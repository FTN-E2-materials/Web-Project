package services;


import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.Reservation;
import beans.model.UserAccount;
import beans.model.enums.TypeOfUser;
import dao.ReservationDAO;
import services.interfaces.CRUDServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path("/reservations")
public class ReservationService extends CRUDService<Reservation, ReservationDAO> implements CRUDServiceInterface<Reservation>{

	@PostConstruct
	@Override
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}
	
	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.reservationDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.reservationsDataLocation;
	}

	@Override
	public void initAttributes() {
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<Reservation>(Reservation.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new ReservationDAO(
										(Storage<Reservation>)ctx.getAttribute(storageFileLocation)
									));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Reservation create(Reservation reservation, @Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		
		if (currentUser.getType() == TypeOfUser.GUEST)
			super.create(reservation);
			
		return null;					
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Reservation update(Reservation obj, @Context HttpServletRequest request) {
		return super.update(obj);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> getAll(@Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		
		if (currentUser == null)
			return new ArrayList<>();
		
		if (currentUser.isGuest())
			return dao.getByGuestID(currentUser.id);
		if (currentUser.isHost())
			return dao.getByHostID(currentUser.id);
		if (currentUser.isAdmin())
			return dao.getAll();
		else 
			return new ArrayList<>();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Reservation getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		return super.getByID(key);
	}

	public Reservation delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		return super.delete(requestWrapper);
	}


}
