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

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Reservation;
import dao.ReservationDAO;
import services.Service;
import storage.Storage;
import util.Config;


@Path("/reservations")
public class ReservationService extends Service<Reservation, ReservationDAO> implements DatabaseServiceInterface{

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
		// TODO Only guests can create reviews
		// Guest has to have a FINISHED or REJECTED reservation with the apartment in question
		return super.create(reservation, request);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<Reservation> getAll(@Context HttpServletRequest request) {
		// TODO See what privileges does the user have 
		// 1 - Unregistered - Deny
		// 2 - Guest - see their own reservations
		// 3 - Host - see reservations on their apartments
		// 4 - Admin - see all the reservations 
		// For now let's just return all 
		Object user = request.getAttribute("user");
		if (user == null)
			System.out.println("Atribut je null");
		
		return super.getAll(request);
	}
}
