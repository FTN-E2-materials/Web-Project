package dao.interfaces;

import java.util.Collection;

import beans.model.entities.Reservation;

public interface ReservationDAOInterface {
	
	public Collection<Reservation> getByGuestID(String id); 
	public Collection<Reservation> getByHostID(String id);
}
