package dao;

import java.util.ArrayList;
import java.util.Collection;

import beans.model.Apartment;
import beans.model.enums.ApartmentStatus;
import dao.interfaces.ApartmentDAOInterface;
import storage.Storage;
import util.Config;


public class ApartmentDAO extends BeanDAO<Apartment> implements ApartmentDAOInterface{

	@Override
	protected void init() {
		idHeader = Config.apartmentIdHeader;
	}
	 
	public ApartmentDAO(Storage<Apartment> storage) {
		super(storage);
		init();
	}
	
	public Collection<Apartment> getActive() {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE)
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getInactive() {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.INACTIVE)
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getActiveByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE  &&  ap.hostID.equals(hostID))
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getInactiveByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.INACTIVE  &&  ap.hostID.equals(hostID))
				apartments.add(ap);
		}
		
		return apartments;
	}
}
