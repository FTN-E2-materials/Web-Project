package util.wrappers;

import java.util.List;

import beans.model.other.Date;

public class ApartmentFieldWrapper extends FieldWrapper {
	public String key;
	public String hostID;
	public Double rating;
	public Integer numberOfRatings;
	public List<Date> workingDates;
	
	public ApartmentFieldWrapper(String key, String hostID, Double rating, Integer numberOfRatings, List<Date> workingDates) {
		super();
		this.key = key;
		this.hostID = hostID;
		this.rating = rating;
		this.numberOfRatings = numberOfRatings;
		this.workingDates = workingDates;
	}
}
