package services.data;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.model.entities.Image;
import dao.ImageDAO;
import services.interfaces.rest.ResponseCRUDInterface;
import services.templates.CRUDService;
import storage.Storage;
import util.Config;
import util.exceptions.EntityValidationException;
import util.wrappers.RequestWrapper;


@Path(Config.IMAGES_SERVICE_PATH)
public class ImageService extends CRUDService<Image, ImageDAO> implements ResponseCRUDInterface<Image> {

	@Override
	@PostConstruct
	public void onCreate() {
		setDatabaseString();
		setStorageLocation();
		initAttributes();
	}

	@Override
	public void setDatabaseString() {
		databaseAttributeString = Config.imageDatabaseString;
	}

	@Override
	public void setStorageLocation() {
		storageFileLocation = Config.imagesRootLocation;
	}

	@Override
	public void initAttributes() {
		if (ctx.getAttribute(storageFileLocation) == null)
			ctx.setAttribute(storageFileLocation, new Storage<Image>(Image.class, storageFileLocation));
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, 
									new ImageDAO(
										(Storage<Image>)ctx.getAttribute(storageFileLocation)
									));
	}

	@POST
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Image image, @Context HttpServletRequest request) {
		try {
			return OK(super.create(image));
		}
		catch (EntityValidationException e) {
			e.printStackTrace();
			return BadRequest(e.message);
		}
	}

	@Override
	public Response update(Image obj, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAll(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Override
	@Path("{imageID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByID(@PathParam("imageID") String key, HttpServletRequest request) {
		return OK(super.getByID(key));
	}

	@Override
	public Response delete(RequestWrapper requestWrapper, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
