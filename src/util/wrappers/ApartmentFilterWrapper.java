package util.wrappers;

import java.util.Calendar;

import beans.model.City;

/** Wrapper class for processing more complex requests with object datatypes in payload. 
 *  Intended use: filtering by city, location, time, etc. */
public class ApartmentFilterWrapper {
	public City city;
	public Integer numOfVisitors;
	public Integer minRooms;
	public Integer maxRooms;
	public Calendar startDate;
	public Calendar endDate;
	public Double minPrice;
	public Double maxPrice;
}
