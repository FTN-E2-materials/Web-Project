package util.services;

import beans.interfaces.Cloneable;
import beans.interfaces.FieldWrapperInterface;
import util.wrappers.FieldWrapper;

public class UpdateService {

	/** Method to overwrite specific fields of an existing entity with new values.
	 * Should be used withing the CRUD - Update method in order to keep important/nonmodifiable values in tact.
	 * Values passed to this method should be validated beforehand, in order to avoid exceptions.
	 * @param oldValue
	 * @param newValue
	 */
	public static <T extends FieldWrapperInterface & Cloneable<T>>void update(T oldValue, T newValue) {
		FieldWrapper wrapper = oldValue.createFieldWrapper();
		oldValue.clone(newValue);
		oldValue.applyFieldWrapper(wrapper);
	}
}
