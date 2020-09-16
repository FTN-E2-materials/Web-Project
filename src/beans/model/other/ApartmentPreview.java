package beans.model.other;

import beans.model.entities.Apartment;

public class ApartmentPreview {
	public String key;
	public String hostID;
	public String title;
	public int numberOfRooms;
    public int numberOfGuests;
    public String mainImage;
    public Double rating;
    public int numberOfRatings;
    
    public ApartmentPreview() {
    	
    }
    
	public ApartmentPreview(Apartment a) {
		this.key = a.key;
		this.hostID = a.hostID;
		this.title = a.title;
		this.numberOfGuests = a.numberOfGuests;
		this.numberOfRooms = a.numberOfRooms;
		this.mainImage = a.mainImage;
		this.numberOfRatings = a.numberOfRatings;
		this.rating = a.rating;
	}
}
