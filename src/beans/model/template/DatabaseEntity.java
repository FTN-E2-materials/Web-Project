package beans.model.template;

import util.exceptions.EntityValidationException;

/** Parent class for all database entity objects. Provides String id field along with get and set methods */
public abstract class DatabaseEntity {

	public String key;
	protected boolean isCountable = true;
	private boolean isDeleted = false;
	
	/** Used to determine whether the ID of the entity can be overridden by an entity counter during Create() in DAO 
	 *  Only false in Users and similar classes which already come with a predetermined ID. */
	public boolean isCountable() {
		return isCountable;
	}
	
	public void delete() {
		this.isDeleted = true;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	
	/** Validates object fields. 
	 * @throws IllegalArgumentException if any of the required fields is empty or has a wrong value
	 */
	public void validate() throws EntityValidationException {
		if (key == null)
			throw new EntityValidationException("Key cannot be empty");
		if (key.isEmpty())
			throw new EntityValidationException("Key cannot be empty");
	}
}
