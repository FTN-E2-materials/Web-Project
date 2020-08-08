package util;

import java.util.Calendar;
import beans.model.Apartment;
import beans.model.City;
import beans.model.Country;


/** Database filtering class used for fetching specific entities based on their details */
public class ApartmentFilter extends AbstractFilter<Apartment> {
	
	/** Return a collection of entities for the given host */
	public ApartmentFilter filterByHost(String hostUsername) {
		
		for (Apartment ap : entities) {
			if (!ap.hostID.equals(hostUsername))
				entities.remove(ap);
		}
		
		return this;
	}
	
	/** Return a collection of entities which are available on the given dates */
	public ApartmentFilter filterByDates(Calendar checkInDate, Calendar checkoutDate) {
		
		for (Apartment ap : entities) {
			// TODO Figure out how available dates work 
		}
		
		return this;
	}
	
	/** Return a collection of entities which are located in the given city */
	public ApartmentFilter filterByCity(City city) {
		
		for (Apartment ap : entities) {
			if (!ap.location.address.city.equals(city))
				entities.remove(ap);
		}
		
		return this;
	}
	
	/** Return a collection of entities which are located in the given country */
	public ApartmentFilter filterByCountry(Country country) {
		
		for (Apartment ap : entities) {
			if (!ap.location.address.country.equals(country))
				entities.remove(ap);
		}
		
		return this;
	}
	
	/** Return a collection of entities which fit in the given price criteria */
	public ApartmentFilter filterByPriceRange(double lowLimitPrice, double topLimitPrice) {
		
		for (Apartment ap : entities) {
			if (ap.pricePerNight < lowLimitPrice  ||  ap.pricePerNight > topLimitPrice)
				entities.remove(ap);
		}
		
		return this;
	}
	
	/** Returns a collection of entities which fit the given room number range */
	public ApartmentFilter filterByNumberOfRooms(int minNumber, int maxNumber) {
		
		for (Apartment ap : entities) {
			if (ap.numberOfRooms < minNumber  ||  ap.numberOfRooms > maxNumber)
				entities.remove(ap);
		}
		
		return this;
	}
	
	/** Returns a collection of entities which fit the number of guests given */
	public ApartmentFilter filterByNumberOfGuests(int numberOfGuests) {
		
		for (Apartment ap : entities) {
			if (ap.numberOfGuests != numberOfGuests)
				entities.remove(ap);
		}
		
		return this;
	}
}
