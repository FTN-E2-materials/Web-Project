package filters;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import beans.model.Apartment;
import beans.model.City;
import dao.ApartmentDAO;


/** Database filtering class used for fetching specific entities based on their details */
public class ApartmentFilter extends BaseFilter<Apartment> {
	
	private static ApartmentFilter instance;
	
	/** Returns an instance which can be used to filter all the data from the given DAO object */
	public static ApartmentFilter instantiateWithDAO(ApartmentDAO dao) {
		if (instance == null)
			instance = new ApartmentFilter();
		
		instance.initiateFilterOperation(dao);
		return instance;
	}
	
	/** Returns an instance which can be used to filter the given apartments */
	public static ApartmentFilter instantiateWithData(Collection<Apartment> data) {
		if (instance == null)
			instance = new ApartmentFilter();
		
		instance.entities = data;
		return instance;
	}
	
	private ApartmentFilter() {
		
	}
	
	/** Return a collection of entities which are available on the given dates */
	public ApartmentFilter filterByDates(Calendar checkInDate, Calendar checkoutDate) {
		
		Iterator<Apartment> iterator = entities.iterator(); 
		while (iterator.hasNext()) { 
			Apartment ap = iterator.next();
			// TODO Figure out how available dates work 
		}
		
		return this;
	}
	
	/** Return a collection of entities which are located in the given city.
	 *  If city contains only some of its values, filter works only on the existing ones.
	 *  Eg. if the City parameter has a postal code, but no name, the filter works only with the postal code. */
	public ApartmentFilter filterByCity(City city) {
		Iterator<Apartment> iterator = entities.iterator(); 
		
		if (city.name == null  &&  city.postalCode == null)
			return this;
		else if (city.name == null) {
			while (iterator.hasNext()) 
			{ 
				Apartment ap = iterator.next();
				try {						// TODO Remove all apartments with NULL locations, then remove this try block
					if (!ap.location.address.city.postalCode.equals(city.postalCode))
						iterator.remove();
				}
				catch (Exception e) {
					iterator.remove();
				}
			}
		}
		else if (city.postalCode == null) {
			while (iterator.hasNext())
			{
				Apartment ap = iterator.next();
				try {					// TODO Remove all apartments with NULL locations, then remove this try block
					if (!ap.location.address.city.name.equals(city.name))
						iterator.remove();
					}
				catch(Exception e) {
					iterator.remove();
				}
			}
		}
		else {
			while (iterator.hasNext()) 
			{ 
				Apartment ap = iterator.next();
				try {
					if (!ap.location.address.city.equals(city))
						iterator.remove();
				}
				catch (Exception e) {
					iterator.remove();
				}
			}
		}
		return this;
	}
	
	/** Return a collection of entities which fit in the given price criteria */
	public ApartmentFilter filterByPriceRange(double lowLimitPrice, double topLimitPrice) {
		Iterator<Apartment> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Apartment ap = iterator.next();
			if (ap.pricePerNight < lowLimitPrice  ||  ap.pricePerNight > topLimitPrice)
				iterator.remove();
		}
		return this;
	}
	
	/** Returns a collection of entities which fit the given room number range */
	public ApartmentFilter filterByNumberOfRooms(int minNumber, int maxNumber) {
		Iterator<Apartment> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Apartment ap = iterator.next();
			if (ap.numberOfRooms < minNumber  ||  ap.numberOfRooms > maxNumber)
				iterator.remove();
		}
		return this;
	}
	
	/** Returns a collection of entities which fit the number of guests given */
	public ApartmentFilter filterByNumberOfGuests(int numberOfGuests) {
		
		Iterator<Apartment> iterator = entities.iterator(); 
		while (iterator.hasNext()) { 
			Apartment ap = iterator.next();
			if (ap.numberOfGuests != numberOfGuests)
				iterator.remove();
		}
		return this;
	}
}
