package services;


import javax.annotation.PostConstruct;
import javax.ws.rs.Path;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Reservation;
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
	}
}
