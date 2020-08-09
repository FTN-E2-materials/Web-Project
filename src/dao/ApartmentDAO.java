package dao;

import java.util.ArrayList;
import java.util.Collection;

import beans.model.Apartment;
import beans.model.ApartmentStatus;


public class ApartmentDAO extends BeanDAO<Apartment> {

	@Override
	protected void init() {
		
	}
	
	/** Returns a collection of all active apartments */
	public Collection<Apartment> getActive() {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE)
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	/** Returns a collection of all inactive apartments */
	public Collection<Apartment> getInactive() {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.INACTIVE)
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	/** Returns a collection of active apartments for the given host */
	public Collection<Apartment> getActiveByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE  &&  ap.hostID.equals(hostID))
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	/** Returns a collection of inactive apartments for the given host */
	public Collection<Apartment> getInactiveByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.INACTIVE  &&  ap.hostID.equals(hostID))
				apartments.add(ap);
		}
		
		return apartments;
	}
}
