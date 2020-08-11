package dao;

import beans.model.Amenity;
import util.Config;


public class AmenityDAO extends BeanDAO<Amenity> {
	
	@Override
	protected void init() {
		idHeader = Config.amenityIdHeader;
	}
}
