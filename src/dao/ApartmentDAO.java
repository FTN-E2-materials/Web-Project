package dao;

import java.util.ArrayList;
import java.util.Collection;

import beans.model.entities.Apartment;
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
	
	/** Searches through all the apartments and returns those which contain the given word in their title.
	 *  Search is conducted only on ACTIVE and NOT DELETED apartments. */
	public Collection<Apartment> searchAsVisitor(String word) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE  &&  !ap.isDeleted()  && 
					ap.title.toLowerCase().contains(word.toLowerCase()))
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	/** Searches through all the apartments and returns those which contain the given word in their title.
	 *  Search is conducted only on NOT DELETED apartments. */
	public Collection<Apartment> searchAsAdmin(String word) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (!ap.isDeleted()  &&  ap.title.toLowerCase().contains(word.toLowerCase()))
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	/** Searches through all the apartments of the given host, and returns those which contain the given word in their title.
	 *  Search is conducted only on ACTIVE and NOT DELETED apartments.*/
	public Collection<Apartment> searchAsHost(String word, String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE  &&  !ap.isDeleted()  &&  
					ap.title.toLowerCase().contains(word.toLowerCase())  &&  ap.hostID.contentEquals(hostID))
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getActive() {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE  &&  !ap.isDeleted())
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getInactive() {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.INACTIVE  &&  !ap.isDeleted())
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.hostID.equals(hostID)  &&  !ap.isDeleted())
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getActiveByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.ACTIVE  &&  ap.hostID.equals(hostID)  &&  !ap.isDeleted())
				apartments.add(ap);
		}
		
		return apartments;
	}
	
	public Collection<Apartment> getInactiveByHost(String hostID) {
		Collection<Apartment> apartments = new ArrayList<Apartment>();
		
		for (Apartment ap : database.values()) {
			if (ap.status == ApartmentStatus.INACTIVE  &&  ap.hostID.equals(hostID)  &&  !ap.isDeleted())
				apartments.add(ap);
		}
		
		return apartments;
	}
}
