package beans.interfaces;

import util.wrappers.FieldWrapper;

/** Provides methods for backing up and retrieving specific field values from an object
 *  Intented use for before and after cloning an object from another object, in order to ensure 
 *  that specific field values are kept constant.
 * @author Nikola
 *
 */
public interface FieldWrapperInterface {
	/** Creates a wrapper around specific fields, which should be defined in each separate class implementing this interface */
	public FieldWrapper createFieldWrapper();
	/** Applies an existing wrapper to an object, overwriting its field values with the values from the wrapper */
	public void applyFieldWrapper(FieldWrapper wrapper);
}
