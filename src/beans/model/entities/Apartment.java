package beans.model.entities;

import java.util.*;

import beans.interfaces.Cloneable;
import beans.interfaces.FieldWrapperInterface;
import beans.model.enums.ApartmentStatus;
import beans.model.enums.AccommodationType;
import beans.model.other.Date;
import beans.model.other.Location;
import beans.model.other.Time;
import beans.model.template.DatabaseEntity;
import util.wrappers.ApartmentFieldWrapper;
import util.wrappers.FieldWrapper;

public class Apartment extends DatabaseEntity implements Cloneable<Apartment>, FieldWrapperInterface{
   public String title; 
   public AccommodationType type;
   public int numberOfRooms;
   public int numberOfGuests;
   public List<Date> availableDates;
   public List<Date> workingDates;
   public Double pricePerNight;
   public List<String> pictures;
   public Time checkInTime;
   public Time checkOutTime;
   public ApartmentStatus status = ApartmentStatus.INACTIVE;
   public String hostID;   
   public Location location;
   public ArrayList<Amenity> amenities;
   public String imageLink;
   
   public Double rating;
   public int numberOfRatings;
   
   public ArrayList<Amenity> getAmenities() {
      if (amenities == null)
         amenities = new ArrayList<Amenity>();
      return amenities;
   }
   
   @Override
   public void validate() {
   	// TODO Auto-generated method stub
   	
   }

	@Override
	public FieldWrapper createFieldWrapper() {
		return new ApartmentFieldWrapper(key, hostID, rating, numberOfRatings, workingDates);
	}
	
	@Override
	public void applyFieldWrapper(FieldWrapper fieldWrapper) {
		ApartmentFieldWrapper wrapper = (ApartmentFieldWrapper)fieldWrapper;
		
		this.key = wrapper.key;
		this.hostID = wrapper.hostID;
		this.rating = wrapper.rating;
		this.numberOfRatings = wrapper.numberOfRatings;
		this.workingDates = wrapper.workingDates;
	}
	
	@Override
	public void clone(Apartment obj) {
		this.title = obj.title;
		this.type = obj.type;
		this.numberOfRooms = obj.numberOfRooms;
		this.numberOfGuests = obj.numberOfGuests;
		this.availableDates = obj.availableDates;
		this.pricePerNight = obj.pricePerNight;
		this.pictures = obj.pictures;
		this.checkInTime = obj.checkInTime;
		this.checkOutTime = obj.checkOutTime;
		this.status = obj.status;
		this.location = obj.location;
		this.amenities = obj.amenities;
		this.imageLink = obj.imageLink;
	}
}