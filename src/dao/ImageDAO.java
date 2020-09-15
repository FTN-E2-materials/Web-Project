package dao;

import beans.model.entities.Image;
import storage.Storage;
import util.Config;

public class ImageDAO extends BeanDAO<Image> {

	public ImageDAO(Storage<Image> storage) {
		super(storage);
		init();
	}

	@Override
	protected void init() {
		idHeader = Config.imageIdHeader;
	}
	
}
