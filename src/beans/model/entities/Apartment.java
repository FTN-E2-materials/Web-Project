package beans.model.entities;

import java.util.*;

import beans.model.DatabaseEntity;
import beans.model.enums.ApartmentStatus;
import beans.model.enums.ApartmentType;
import beans.model.other.Date;
import beans.model.other.Location;
import beans.model.other.Time;

public class Apartment extends DatabaseEntity {
   public String title; 
   public ApartmentType type;
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
   
   public void setAmenities(ArrayList<Amenity> newAmenities) {
      amenities = newAmenities;
   }
   
   public void addAmenities(Amenity newAmenity) {
      if (newAmenity == null)
         return;
      if (this.amenities == null)
         this.amenities = new ArrayList<Amenity>();
      if (!this.amenities.contains(newAmenity))
         this.amenities.add(newAmenity);
   }
   
   public void removeAmenities(Amenity oldAmenity) {
      if (oldAmenity == null)
         return;
      if (this.amenities != null)
         if (this.amenities.contains(oldAmenity))
            this.amenities.remove(oldAmenity);
   }
   
   public void removeAllAmenities() {
      if (amenities != null)
         amenities.clear();
   }
}