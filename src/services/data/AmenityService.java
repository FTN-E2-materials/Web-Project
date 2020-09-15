package services.data;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.interfaces.SessionToken;
import beans.model.entities.Amenity;
import dao.AmenityDAO;
import services.interfaces.rest.ResponseCRUDInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.exceptions.EntityValidationException;
import util.wrappers.RequestWrapper;


@Path(Config.AMENITIES_SERVICE_PATH)
public class AmenityService extends CRUDService<Amenity, AmenityDAO> implements ResponseCRUDInterface<Amenity>{

	@Override
	@PostConstruct
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}

	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.amenityDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.amenitiesDataLocation;
	}

	@Override
	public void initAttributes() {
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<Amenity>(Amenity.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new AmenityDAO(
										(Storage<Amenity>)ctx.getAttribute(storageFileLocation)
									));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response create(Amenity obj, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isAdmin()) {
			try {
				return OK(super.create(obj));
			}
			catch (EntityValidationException e) {
				return BadRequest(e.message);
			}
		}
		
		return ForbiddenRequest();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response update(Amenity obj, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isAdmin()) {
			try {
				obj.validate();
				super.update(obj);
			}
			catch (EntityValidationException e) {
				return BadRequest(e.message);
			}
		}
		
		return ForbiddenRequest();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAll(@Context HttpServletRequest request) {
		return OK(super.getAll());
	}
	
	@GET
	@Path("{amenityID}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getByID(@PathParam("amenityID") String key, @Context HttpServletRequest request) {
		return NotAllowed();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		SessionToken session = getCurrentSession(request);
		
		if (session == null)
			return ForbiddenRequest();
		if (session.isAdmin())
			return OK(super.delete(requestWrapper));
		
		return ForbiddenRequest();
	}

}
