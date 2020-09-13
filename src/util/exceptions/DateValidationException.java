package util.exceptions;

/** Signals a failed attempt to organize working and available dates for Apartments.
 * @author Nikola
 */
public class DateValidationException extends BaseException {
	
	public DateValidationException(String message) {
		super(message);
	}
}
