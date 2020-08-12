package storage;

import beans.model.Review;
import util.Config;


public class ReviewStorage extends Storage<Review> {
	
	public ReviewStorage(String fileLocation) {
		fileStorageLocation = fileLocation;
	}

}
