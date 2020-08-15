package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.Reservation;
import beans.model.TypeOfUser;
import beans.model.UserAccount;
import dao.ReservationDAO;
import services.interfaces.DatabaseServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;


@Path("/reservations")
public class ReservationService extends CRUDService<Reservation, ReservationDAO> implements DatabaseServiceInterface{

	@Override
	@PostConstruct
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
	@Override
	public Reservation create(Reservation reservation, @Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		
		if (currentUser.getType() == TypeOfUser.GUEST)
			super.create(reservation, request);
			
		return null;					
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<Reservation> getAll(@Context HttpServletRequest request) {
		UserAccount currentUser = super.getCurrentUser(request);
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		
		if (currentUser == null)
			return new ArrayList<>();		
		
		if (currentUser.getType() == TypeOfUser.GUEST)
			return dao.getByGuestID(currentUser.id);
		if (currentUser.getType() == TypeOfUser.HOST)
			return dao.getByHostID(currentUser.id);
		if (currentUser.getType() == TypeOfUser.ADMINISTRATOR)
			return super.getAll(request);
		else 
			return null;
	}
}
