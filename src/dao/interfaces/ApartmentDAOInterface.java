package dao.interfaces;

import java.util.Collection;
import beans.model.Apartment;


public interface ApartmentDAOInterface {

	/** Returns a collection of all active apartments */
	public Collection<Apartment> getActive();
	/** Returns a collection of all inactive apartments */
	public Collection<Apartment> getInactive();
	
	/** Returns a collection of active apartments for the given host */
	public Collection<Apartment> getActiveByHost(String hostID);
	/** Returns a collection of inactive apartments for the given host */
	public Collection<Apartment> getInactiveByHost(String hostID);
}
