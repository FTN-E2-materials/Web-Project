package beans.model;

import beans.interfaces.BeanInterface;


/** Parent class for all database entity objects. Provides String id field along with get and set methods */
public abstract class DatabaseEntity implements BeanInterface {

	public String id;
	protected boolean isCountable = true;
	private boolean available = true;
	
	/** Used to determine whether the ID of the entity can be overridden by an entity counter during Create() in DAO 
	 *  Only false in Users and similar classes which already come with a predetermined ID. */
	public boolean isCountable() {
		return isCountable;
	}
	
	@Override
	public String getKey() {
		return id;
	}

	@Override
	public void setKey(String key) {
		id = key;
	}
	
	public void delete() {
		this.available = false;
	}
	
	public boolean isDeleted() {
		return !available;
	}
}
