package beans.model.entities;

import beans.model.enums.ReservationStatus;
import beans.model.other.ApartmentPreview;
import beans.model.other.Date;
import beans.model.template.DatabaseEntity;


public class Reservation extends DatabaseEntity {
	
   public Date startingDate;
   public int numberOfNights;
   public Double price;
   public String reservationMessage;
   public ReservationStatus status;
   
   public String guestID;
   public ApartmentPreview apartment;
   
   
   public Reservation() {
	   status = ReservationStatus.CREATED;
   }
   
   @Override
   public void validate() {
   	// TODO Auto-generated method stub
   }
   
   public boolean isDenied() {
	   return status == ReservationStatus.DENIED;
   }
   
   public boolean isFinished() {
	   return status == ReservationStatus.FINISHED;
   }
}