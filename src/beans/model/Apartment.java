package beans.model;

import java.util.*;
import beans.interfaces.*;

public class Apartment implements BeanInterface {
	
   public String id;
   public ApartmentType type;
   public int numberOfRooms;
   public int numberOfGuests;
   public List<Date> availableDates;
   public List<Date> workingDates;
   public Double pricePerNight;
   public List<String> pictures;
   public Time checkInTime;
   public Time checkOutTime;
   public ApartmentStatus status;
   
   public Location location;
   // TODO Host identification via Username?
   public Host host;
   public ArrayList<Amenity> amenities;
   
	@Override
	public String getKey() {
		return id;
	}
   
   public ArrayList<Amenity> getAmenities() {
      if (amenities == null)
         amenities = new ArrayList<Amenity>();
      return amenities;
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