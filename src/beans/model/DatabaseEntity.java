package beans.model;


/** Parent class for all database entity objects. Provides String id field along with get and set methods */
public abstract class DatabaseEntity<T> {

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
	public abstract void validate() throws IllegalArgumentException;
	
	/** Method to overwrite specific fields of an existing entity with new values.
	 * Should be used withing the CRUD - Update method in order to keep constant values in tact.
	 * Values passed to this method should be validated beforehand, in order to avoid exceptions.
	 * @param existingEntity
	 * @param newEntity
	 */
	public abstract void updateAllowedFields(T newEntity);
}
