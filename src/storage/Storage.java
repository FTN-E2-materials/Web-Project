package storage;

import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import beans.interfaces.BeanInterface;
import beans.model.Review;
import util.TextFileHandler;


public class Storage<T extends BeanInterface> {
	
	/** Location of the file where files will be read from and written to */
	protected String fileStorageLocation;
	/** Static Gson object to be shared by all the DB classes for I/O operations */
	public static Gson GSON = new Gson();
	
	public Storage(String fileStorageLocation) {
		this.fileStorageLocation = fileStorageLocation;
	}
	
	/** Return a map of all the JSON objects which were saved in the specified file. */
	@SuppressWarnings("unchecked")
	public ArrayList<T> readAll() {
		String objectsJSON = TextFileHandler.readFromFile(fileStorageLocation);
		return GSON.fromJson(objectsJSON, TypeToken.getParameterized(ArrayList.class, Review.class).getType());
	}
	
	/** Write all objects from the specified map to a file specified by the location parameter. */
	public void writeAll(ArrayList<T> objects) {
		String objectsJSON = GSON.toJson(objects);
		System.out.println("JSON: " + objectsJSON);
		TextFileHandler.writeToFile(objectsJSON, fileStorageLocation);
	}
}
