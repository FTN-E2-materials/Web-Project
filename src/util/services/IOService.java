package util.services;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;


/** Helper class for reading and writing to text files */
public class IOService {

	/** Write the string text into a file specified by the given location/path */
	public static void writeToFile(String text, String fileLocation) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fileLocation));
			System.out.println("Printing to location: " + fileLocation);
			out.print(text);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("There was an error while trying to write to file: " + fileLocation);
		}
	}
	
	/** Return all the lines from the specified file as a single String */
	public static String readFromFile(String fileLocation) {
		try {
			// TODO This crashes if the .txt file doesn't exist already!
			return new String(Files.readAllBytes(Paths.get(fileLocation)));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("There was an error while trying to write to file: " + fileLocation);
			
			return null;
		}
	}
}
