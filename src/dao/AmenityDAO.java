package dao;

import beans.model.entities.Amenity;
import storage.Storage;
import util.Config;


public class AmenityDAO extends BeanDAO<Amenity> {
	
	public AmenityDAO(Storage<Amenity> storage) {
		super(storage);
		init();
	}

	@Override
	protected void init() {
		idHeader = Config.amenityIdHeader;
	}
}
