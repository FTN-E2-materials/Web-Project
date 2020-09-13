package util.services;

import javax.servlet.ServletContext;

import beans.model.entities.Apartment;
import beans.model.entities.Reservation;
import beans.model.other.Date;
import dao.ApartmentDAO;
import javassist.NotFoundException;
import util.Config;
import util.exceptions.ApartmentNotFoundException;
import util.exceptions.DateValidationException;

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
	 * Intended use for creating reservations.
	 * @param reservation
	 */
	public void applyDateChanges(Reservation reservation) throws ApartmentNotFoundException, DateValidationException {
		ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
		Apartment apartment = apartmentDAO.getByKey(reservation.apartment.key);
		
		if (apartment == null)
			throw new ApartmentNotFoundException();
		
		int dayCounter = reservation.numberOfNights;
		Date date = reservation.startingDate;
		
		while (dayCounter > 0) {
			if (!apartment.availableDates.remove(date))		// If remove returns false, it means that the given date was not found, thus the apartment is not available at that time
				throw new DateValidationException("The selected dates are currently unavailable.");
			apartment.workingDates.add(new Date(date));	// Creates a deep copy, to avoid placing the same reference every time
			date.addDays(1);
			dayCounter--;
		}
		
		apartmentDAO.forceUpdate();
	}
	
	/** Changes the working and available dates for the apartment of this reservation, 
	 * in order to properly display available dates for future clients.
	 * Intended use for cancelling/denying reservations.
	 * @param reservation
	 */
	public void reverseDateChanges(Reservation reservation) throws ApartmentNotFoundException, DateValidationException {
		ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
		Apartment apartment = apartmentDAO.getByKey(reservation.apartment.key);
		
		if (apartment == null)
			throw new ApartmentNotFoundException();
		
		int dayCounter = reservation.numberOfNights;
		Date date = reservation.startingDate;
		
		while (dayCounter > 0) {
			if (!apartment.workingDates.remove(date))		
				throw new DateValidationException("No reservation available for this date range.");
			apartment.availableDates.add(new Date(date));	// Creates a deep copy, to avoid placing the same reference every time
			date.addDays(1);
			dayCounter--;
		}
		
		apartmentDAO.forceUpdate();
	}

}
