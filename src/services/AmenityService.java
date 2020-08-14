package services;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Amenity;
import dao.AmenityDAO;
import storage.Storage;
import util.Config;


@Path("/amenities")
public class AmenityService extends Service<Amenity, AmenityDAO> implements DatabaseServiceInterface {

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

}
