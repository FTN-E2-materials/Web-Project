package services.data;

import java.util.Calendar;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.entities.Apartment;
import beans.model.entities.Reservation;
import beans.model.enums.ReservationStatus;
import beans.model.other.ApartmentPreview;
import beans.model.other.Date;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import services.interfaces.rest.ReservationServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.exceptions.BaseException;
import util.services.SchedulingService;
import util.wrappers.RequestWrapper;


@Path(Config.RESERVATIONS_SERVICE_PATH)
public class ReservationService extends CRUDService<Reservation, ReservationDAO> implements ReservationServiceInterface {

	@PostConstruct
	@Override
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}
	
	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.reservationDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.reservationsDataLocation;
	}

	@Override
	public void initAttributes() {
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<Reservation>(Reservation.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new ReservationDAO(
										(Storage<Reservation>)ctx.getAttribute(storageFileLocation)
									));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response create(Reservation reservation, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		
		if (session.isGuest()) {
			reservation.guestID = session.getUserID();
			reservation.status = ReservationStatus.CREATED;
			ApartmentDAO apartmentDAO = (ApartmentDAO)ctx.getAttribute(Config.apartmentDatabaseString);
			Apartment apartment = apartmentDAO.getByKey(reservation.apartment.key);
			if (apartment == null)
				return BadRequest("Apartment doesn't exist");
			reservation.apartment = new ApartmentPreview(apartment);

			try {
				reservation.validate();
				SchedulingService.getInstance(ctx).applyDateChanges(reservation);
				
				Reservation createdRes = super.create(reservation);
				if (createdRes == null)
					return BadRequest();
				
				return OK(createdRes);
			}
			catch (BaseException e) {
				System.out.println("Throwing creation exception: " + e.message);
				return BadRequest(e.message);
			}
		}
			
		return ForbiddenRequest();					
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response update(Reservation obj, @Context HttpServletRequest request) {
		return NotAllowed(); // Reservations cannot be updated.
								   // Only status changes are allowed via separate PUT methods
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		
		if (session == null)
			return ForbiddenRequest();
		
		if (session.isGuest())
			return OK(dao.getByGuestID(session.getUserID()));
		if (session.isHost())
			return OK(dao.getByHostID(session.getUserID()));
		if (session.isAdmin())
			return OK(dao.getAll());

		return ForbiddenRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Override
	public Response getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		return ForbiddenRequest(); // TODO Reservations should not be fetched like this?
	}

	
	public Response delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		//TODO Is deleting like this allowed? 
		return OK(super.delete(requestWrapper));
	}
	
	@PUT 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancel")
	@Override
	public Response cancel(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		if (requestWrapper == null)
			return BadRequest();
		if (requestWrapper.stringKey == null)
			return BadRequest();
		
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		Reservation reservation = dao.getByKey(requestWrapper.stringKey);
		if (reservation == null)
			return BadRequest("Reservation with the given ID was not found.");
		
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		// If guest wants to change their own reservation
		if (session.isGuest()  &&  session.getUserID().equals(reservation.guestID)) {
			if (reservation.status == ReservationStatus.CREATED  ||
					reservation.status == ReservationStatus.APPROVED) {
				try {
					SchedulingService.getInstance(ctx).reverseDateChanges(reservation);
					reservation.status = ReservationStatus.CANCELLED;
					return OK(super.update(reservation));
				}
				catch (BaseException e) {
					return BadRequest(e.message);
				}
			}
			else {
				return BadRequest("This reservation cannot be cancelled");
			}
		}
		// If host wants to change their own reservation
		if (session.isHost() &&  session.getUserID().equals(reservation.apartment.hostID)) {
			if (reservation.status == ReservationStatus.CREATED  ||
					reservation.status == ReservationStatus.APPROVED) {
				try {
					SchedulingService.getInstance(ctx).reverseDateChanges(reservation);
					reservation.status = ReservationStatus.DENIED;
					return OK(super.update(reservation));
				}
				catch (BaseException e) {
					return BadRequest(e.message);
				}
			}
			else {
				return BadRequest("This reservation cannot be cancelled");
			}
		}
		
		return ForbiddenRequest();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/approve")
	@Override
	public Response approve(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		if (requestWrapper == null)
			return BadRequest();
		if (requestWrapper.stringKey == null)
			return BadRequest();
		
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		Reservation reservation = dao.getByKey(requestWrapper.stringKey);
		if (reservation == null)
			return BadRequest("Reservation with the given ID was not found.");
		
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		// If guest wants to approve their own reservation
		if (session.isHost() &&  session.getUserID().equals(reservation.apartment.hostID)) {
			if (reservation.status == ReservationStatus.CREATED) {
				reservation.status = ReservationStatus.APPROVED;
				return OK(super.update(reservation));
			}
			else {
				return ForbiddenRequest();
			}
		}
		
		return ForbiddenRequest();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/finish")
	@Override
	public Response finish(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		if (requestWrapper == null)
			return BadRequest();
		if (requestWrapper.stringKey == null)
			return BadRequest();
		
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		Reservation reservation = dao.getByKey(requestWrapper.stringKey);
		if (reservation == null)
			return BadRequest("Reservation with the given ID was not found.");
		
		SessionToken session = super.getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isHost() &&  session.getUserID().equals(reservation.apartment.hostID)) {
			if (reservation.status == ReservationStatus.APPROVED) {
				Date endDate = reservation.startingDate;
				endDate.calendar.add(Calendar.DATE, reservation.numberOfNights);
				// TODO Check if this is correct
				if (!endDate.isFuture()) {
					reservation.status = ReservationStatus.FINISHED;
					return OK(super.update(reservation));
				}
				else 
					return BadRequest("Cannot finish a reservation that has not yet ended.");
			}
			else {
				return BadRequest("Cannot finish a reservation that was not approved.");
			}
		}
		
		return ForbiddenRequest();
	}
}
