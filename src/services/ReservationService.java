package services;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Apartment;
import beans.model.Reservation;
import beans.model.ReservationStatus;
import dao.ReservationDAO;
import services.Service;
import util.Config;


@Path("/reservations")
public class ReservationService extends Service<Reservation, ReservationDAO> implements DatabaseServiceInterface{

	@Override
	@PostConstruct
	public void onCreate() {
		databaseAttributeString = Config.reservationDatabaseString;
		if (ctx.getAttribute(databaseAttributeString) == null) 
			ctx.setAttribute(databaseAttributeString, new ReservationDAO());
		
		// Test object
		Reservation res = new Reservation();
		res.apartment = new Apartment();
		res.guestID = "guest1";
		res.id = "reservation123";
		res.numberOfNights = 2;
		res.price = 20d;
		res.reservationMessage = "None";
		res.startingDate = Calendar.getInstance();
		res.status = ReservationStatus.APPROVED;
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		dao.create(res);
	}
}
