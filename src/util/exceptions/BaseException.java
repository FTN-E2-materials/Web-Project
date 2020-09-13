package util.exceptions;

public abstract class BaseException extends Exception {

	public String message;
	
	public BaseException(String message) {
		this.message = message;
	}
}
