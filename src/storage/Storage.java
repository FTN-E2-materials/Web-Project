package storage;

import java.util.Map;

import com.google.gson.*;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import beans.interfaces.BeanInterface;
import util.IOService;

/** Template class whose instance is in charge of reading and writing JSON formatted files to and from the files given to it.
 *  On construction requires an database object type related to it, and the location of the storage where the data will be kept. */
public class Storage<T extends BeanInterface> {
	
	/** Location of the file where files will be read from and written to */
	protected String fileStorageLocation;
	/** Static Gson object to be shared by all the DB classes for I/O operations */
	public static Gson GSON = new Gson();
	/** Type token for dynamic deserialization 
	 *  Defines which type of object is tied to this class at runtime. */
	private TypeToken<Map<String, T>> targetType;
	
	public Storage(Class<T> dataType, String fileStorageLocation) {
		this.fileStorageLocation = fileStorageLocation;
		
		targetType = new TypeToken<Map<String, T>>() {}
        			.where(new TypeParameter<T>() {}, dataType);
	}
	
	/** Return a Map of all the JSON objects which were saved in the specified file. */
	public Map<String, T> readAll() {
		String objectsJSON = IOService.readFromFile(fileStorageLocation);	// This fetches the JSON-format objects from the text file 
		
        return GSON.fromJson(objectsJSON, targetType.getType());		// This deserializes the JSON string into a List of objects
	}
	
	/** Write all objects from the specified map to a file specified by the location parameter. */
	public void writeAll(Map<String, T> objects) {
		String objectsJSON = GSON.toJson(objects);
		IOService.writeToFile(objectsJSON, fileStorageLocation);
	}
}
