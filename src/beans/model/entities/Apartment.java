package beans.model.entities;

import java.util.*;

import beans.model.DatabaseEntity;
import beans.model.enums.ApartmentStatus;
import beans.model.enums.AccommodationType;
import beans.model.other.Date;
import beans.model.other.Location;
import beans.model.other.Time;

public class Apartment extends DatabaseEntity<Apartment> {
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
	public void updateAllowedFields(Apartment newEntity) {
		this.title = newEntity.title;
		this.checkInTime = newEntity.checkInTime;
		this.checkOutTime = newEntity.checkOutTime;
	}
}