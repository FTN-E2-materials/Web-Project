package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import beans.model.entities.Reservation;
import dao.interfaces.ReservationDAOInterface;
import storage.Storage;
import util.Config;


public class ReservationDAO extends BeanDAO<Reservation> implements ReservationDAOInterface {

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
			if (res.guestID.contentEquals(guestID))
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
	
	/** Returns a collection of reservations which are made for the given apartment, by the given guest
	 * @param apartmentID
	 * @param guestID
	 * @return Collection of reservations which fit the query.
	 */
	public Collection<Reservation> getByApartmentAndGuest(String apartmentID, String guestID) {
		Collection<Reservation> reservations = new ArrayList<Reservation>();
		
		for (Reservation res : database.values()) {
			if (res.guestID.contentEquals(guestID)  &&  res.apartment.key.contentEquals(apartmentID))
				reservations.add(res);
		}
		
		return reservations;
	}
}
