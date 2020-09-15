package util.wrappers;

import java.util.Calendar;

import beans.model.other.City;
import beans.model.other.Date;

/** Wrapper class for processing more complex requests with object datatypes in payload. 
 *  Intended use: filtering by city, location, time, etc. */
public class ApartmentFilterWrapper {
	public City city;
	public Integer numOfGuests;
	public Integer minRooms;
	public Integer maxRooms;
	public Calendar startDate;
	public Calendar endDate;
	public Double minPrice;
	public Double maxPrice;
	public Date startingDate;
	public Date endingDate;
	
	public boolean isCityValid() {
		if (city == null)
			return false;
		if (city.name.contentEquals("")  &&  city.postalCode.contentEquals(""))
			return false;
		
		return true;
	}
	
	public boolean arePricesValid() {
		if (minPrice == null  &&  maxPrice == null)
			return false;
		
		return true;
	}
	
	public boolean areRoomNumbersValid() {
		if (minRooms == null  &&  maxRooms == null)
			return false;
		
		return true;
	}
	
	public boolean areDatesValid() {
		if (startingDate == null  ||  endingDate == null)
			return false;
		if (startingDate.greaterThan(endingDate))
			return false;
		if (!startingDate.isFuture()  ||  !endingDate.isFuture())
			return false;
		
		return true;
	}
}
