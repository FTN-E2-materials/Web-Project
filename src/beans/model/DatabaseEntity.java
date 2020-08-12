package beans.model;

import beans.interfaces.BeanInterface;

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
