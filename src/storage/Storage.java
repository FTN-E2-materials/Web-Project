package storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

import beans.interfaces.BeanInterface;
import util.TextFileHandler;


public abstract class Storage<T extends BeanInterface> {
	
	/** Location of the file where files will be read from and written to */
	protected String fileStorageLocation;
	/** Static Gson object to be shared by all the DB classes for I/O operations */
	public static Gson GSON = new Gson();
	
	/** Return a map of all the JSON objects which were saved in the specified file. */
	public ArrayList<T> readAll() {
		String objectsJSON = TextFileHandler.readFromFile(fileStorageLocation);
		return GSON.fromJson(objectsJSON, ArrayList.class);
	}
	
	/** Write all objects from the specified map to a file specified by the location parameter. */
	public void writeAll(ArrayList<T> objects) {
		String objectsJSON = GSON.toJson(objects);
		System.out.println("JSON: " + objectsJSON);
		TextFileHandler.writeToFile(objectsJSON, fileStorageLocation);
	}
}
