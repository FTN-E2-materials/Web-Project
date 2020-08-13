package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import beans.model.Reservation;
import beans.model.Review;
import storage.Storage;
import util.Config;


public class ReservationDAO extends BeanDAO<Reservation> {

	@Override
	protected void init() {
		idHeader = Config.reservationIdHeader;
	}
	
	public ReservationDAO(Storage<Reservation> storage) {
		super(storage);
		init();
	}

	/** Returns a list of reservations for the given guest ID */
	public Collection<Reservation> getByGuestID(String guestID) {
		Collection<Reservation> reservations = new ArrayList<Reservation>();
		
		for (Reservation res : database.values()) {
			if (res.guestID.contentEquals(guestID)  &&  res.startingDate.after(Calendar.getInstance()))
				reservations.add(res);
		}
		
		return reservations;
	}
	
	/** Returns a list of reservations for the given host ID */
	public Collection<Reservation> getByHostID(String hostID) {
		Collection<Reservation> reservations = new ArrayList<Reservation>();
		
		for (Reservation res : database.values()) {
			if (res.apartment.hostID.contentEquals(hostID))
				reservations.add(res);
		}
		
		return reservations;
	}
}
