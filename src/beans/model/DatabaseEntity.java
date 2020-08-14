package beans.model;

import beans.interfaces.BeanInterface;


/** Parent class for all database entity objects. Provides String id field along with get and set methods */
public abstract class DatabaseEntity implements BeanInterface {

	public String id;
	
	@Override
	public String getKey() {
		return id;
	}

	@Override
	public void setKey(String key) {
		id = key;
	}

}
