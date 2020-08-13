package services;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Reservation;
import beans.model.Review;
import dao.ReservationDAO;
import dao.ReviewDAO;
import services.Service;
import storage.Storage;
import util.Config;


@Path("/reservations")
public class ReservationService extends Service<Reservation, ReservationDAO> implements DatabaseServiceInterface{

	@Override
	@PostConstruct
	public void onCreate() {
		databaseAttributeString = Config.reservationDatabaseString;
		storageFileLocation = Config.reservationsDataLocation;
		
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
	public Reservation create(Reservation reservation) {
		// TODO Only guests can create reviews
		// Guest has to have a FINISHED or REJECTED reservation with the apartment in question
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		return dao.create(reservation);
	}
}
