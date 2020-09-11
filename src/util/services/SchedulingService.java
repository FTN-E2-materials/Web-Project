package util.services;

import javax.servlet.ServletContext;

import beans.model.entities.Apartment;
import beans.model.entities.Reservation;
import beans.model.other.Date;
import dao.ApartmentDAO;
import javassist.NotFoundException;
import util.Config;

public class SchedulingService {
	
	private static SchedulingService instance;
	private ServletContext ctx;
	
	public static SchedulingService getInstance(ServletContext ctx) {
		if (instance == null)
			instance = new SchedulingService(ctx);
		
		return instance;
	}
	
	private SchedulingService(ServletContext ctx) {
		this.ctx = ctx;
	}
	
	
	/** Changes the working and available dates for the apartment of this reservation, 
	 * in order to properly display available dates for future clients.
	 * @param reservation
	 */
	public void applyDateChanges(Reservation reservation) throws NotFoundException {
		ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
		Apartment apartment = apartmentDAO.getByKey(reservation.apartment.key);
		
		if (apartment == null)
			throw new NotFoundException("Apartment not found!");
		
		int dayCounter = reservation.numberOfNights;
		Date date = reservation.startingDate;
		
		while (dayCounter > 0) {
			apartment.availableDates.remove(date);		// TODO Check if available date is not removed (missing), that means that it cannot be reserved!
			apartment.workingDates.add(new Date(date));	// Create a deep copy, to avoid placing the same reference every time
			date.addDays(1);
			dayCounter--;
		}
		
		apartmentDAO.forceUpdate();
	}

}
