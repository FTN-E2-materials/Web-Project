package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.model.Amenity;
import dao.AmenityDAO;
import services.interfaces.CRUDServiceInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.RequestWrapper;


@Path("/amenities")
public class AmenityService extends CRUDService<Amenity, AmenityDAO> implements CRUDServiceInterface<Amenity>{

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
	public Amenity create(Amenity obj, @Context HttpServletRequest request) {
		return super.create(obj);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Amenity update(Amenity obj, @Context HttpServletRequest request) {
		return super.update(obj);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Amenity> getAll(@Context HttpServletRequest request) {
		return super.getAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Amenity getByID(@PathParam("id") String key, @Context HttpServletRequest request) {
		return super.getByID(key);
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Amenity delete(RequestWrapper requestWrapper, @Context HttpServletRequest request) {
		return super.delete(requestWrapper);
	}

}
