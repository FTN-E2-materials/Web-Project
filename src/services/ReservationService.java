package services;

import java.util.Calendar;

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
import beans.model.Reservation;
import beans.model.enums.ReservationStatus;
import dao.ReservationDAO;
import services.interfaces.ResponseCRUDServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path("/reservations")
public class ReservationService extends CRUDService<Reservation, ReservationDAO> implements ResponseCRUDServiceInterface<Reservation>{

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
	public Response create(Reservation reservation, @Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		
		if (session.isGuest())
			super.create(reservation);
			
		return null;					
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Reservation obj, @Context HttpServletRequest request) {
		return ForbiddenRequest(); // Reservations cannot be updated.
								   // Only status changes are allowed via separate PUT methods
	}
	
	@PUT 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancel")
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
		// If guest wants to change
		if (session.isGuest()  &&  session.getSessionID().equals(reservation.guestID)) {
			if (reservation.status == ReservationStatus.CREATED  ||
					reservation.status == ReservationStatus.APPROVED) {
				reservation.status = ReservationStatus.CANCELLED;
				return OK(super.update(reservation));
			}
			else {
				return ForbiddenRequest("This reservation cannot be cancelled.");
			}
		}
		// If host wants to change
		if (session.isHost() &&  session.getSessionID().equals(reservation.apartment.hostID)) {
			if (reservation.status == ReservationStatus.CREATED  ||
					reservation.status == ReservationStatus.APPROVED) {
				reservation.status = ReservationStatus.DENIED;
				return OK(super.update(reservation));
			}
			else {
				return ForbiddenRequest("This reservation cannot be cancelled.");
			}
		}
		
		return ForbiddenRequest();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/approve")
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
		if (session.isHost() &&  session.getSessionID().equals(reservation.apartment.hostID)) {
			if (reservation.status == ReservationStatus.CREATED) {
				reservation.status = ReservationStatus.APPROVED;
				return OK(super.update(reservation));
			}
			else {
				return ForbiddenRequest("This reservation cannot be approved.");
			}
		}
		
		return ForbiddenRequest();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/finish")
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
		if (session.isHost() &&  session.getSessionID().equals(reservation.apartment.hostID)) {
			if (reservation.status == ReservationStatus.APPROVED) {
				Calendar endDate = reservation.startingDate;
				endDate.add(Calendar.DATE, reservation.numberOfNights);
				// TODO Check if this is correct
				if (endDate.compareTo(Calendar.getInstance()) < 0) {
					reservation.status = ReservationStatus.FINISHED;
					return OK(super.update(reservation));
				}
			}
			else {
				return ForbiddenRequest("This reservation cannot be finished.");
			}
		}
		
		return ForbiddenRequest();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context HttpServletRequest request) {
		SessionToken session = super.getCurrentSession(request);
		ReservationDAO dao = (ReservationDAO)ctx.getAttribute(databaseAttributeString);
		
		if (session == null)
			return ForbiddenRequest();
		
		if (session.isGuest())
			return OK(dao.getByGuestID(session.getSessionID()));
		if (session.isHost())
			return OK(dao.getByHostID(session.getSessionID()));
		if (session.isAdmin())
			return OK(dao.getAll());
		else 
			return ForbiddenRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		return OK(super.getByID(key));
	}

	public Response delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		return OK(super.delete(requestWrapper));
	}


}
