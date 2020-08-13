package storage;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import java.lang.reflect.Type;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import beans.interfaces.BeanInterface;
import util.TextFileHandler;


public class Storage<T extends BeanInterface> {
	
	/** Location of the file where files will be read from and written to */
	protected String fileStorageLocation;
	/** Static Gson object to be shared by all the DB classes for I/O operations */
	public static Gson GSON = new Gson();
	/** Type token for dynamic deserialization 
	 *  Define which type of object is tied to this class at runtime. */
	private TypeToken<List<T>> targetType;
	
	public Storage(Class<T> dataType, String fileStorageLocation) {
		this.fileStorageLocation = fileStorageLocation;
		
		targetType = new TypeToken<List<T>>() {}
        			.where(new TypeParameter<T>() {}, dataType);
	}
	
	/** Return an ArrayList of all the JSON objects which were saved in the specified file. */
	@SuppressWarnings("unchecked")
	public List<T> readAll() {
		String objectsJSON = TextFileHandler.readFromFile(fileStorageLocation);	// This fetches the JSON-format objects from the text file 
		
        return GSON.fromJson(objectsJSON, targetType.getType());		// This deserializes the JSON string into a List of objects
	}
	
	/** Write all objects from the specified map to a file specified by the location parameter. */
	public void writeAll(ArrayList<T> objects) {
		String objectsJSON = GSON.toJson(objects);
		TextFileHandler.writeToFile(objectsJSON, fileStorageLocation);
	}
}
