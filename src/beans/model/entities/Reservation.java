package beans.model.entities;

import beans.model.enums.ReservationStatus;
import beans.model.other.ApartmentPreview;
import beans.model.other.Date;
import beans.model.template.DatabaseEntity;
import util.exceptions.EntityValidationException;


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
   public void validate() throws EntityValidationException {
	   if (numberOfNights < 1)
		   throw new EntityValidationException("Number of nights must be greater than 0");
	   if (price < 1)
		   throw new EntityValidationException("Price of the reservation must be greater than 0");
   }
   
   public boolean isDenied() {
	   return status == ReservationStatus.DENIED;
   }
   
   public boolean isFinished() {
	   return status == ReservationStatus.FINISHED;
   }
}