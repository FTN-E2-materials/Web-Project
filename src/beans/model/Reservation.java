package beans.model;

import java.util.Calendar;
import beans.interfaces.*;


public class Reservation extends DatabaseEntity {
	
   public Calendar startingDate;
   public int numberOfNights;
   public Double price;
   public String reservationMessage;
   public ReservationStatus status;
   
   public String guestID;
   public Apartment apartment;
}