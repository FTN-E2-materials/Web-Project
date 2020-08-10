package services;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.interfaces.DatabaseServiceInterface;
import beans.model.Amenity;
import beans.model.Apartment;
import dao.ApartmentDAO;


@Path("/apartments")
public class ApartmentService extends Service<Apartment, ApartmentDAO> implements DatabaseServiceInterface {
	private String attributeString = "apartmentDatabase";
	
	@Override
	@PostConstruct
	public void onCreate() {
		databaseAttributeString = attributeString;
		
		if (ctx.getAttribute(databaseAttributeString) == null)
			ctx.setAttribute(databaseAttributeString, new ApartmentDAO());	
		
		ApartmentDAO dao = (ApartmentDAO)ctx.getAttribute(databaseAttributeString);
		Apartment apartment = new Apartment();
		apartment.id = "AP1";
		dao.create(apartment);
	}
	
	/** Test method. */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Amenity test(){
		Amenity amenity = new Amenity();
		amenity.id = "A1";
		amenity.name = "Amenity";
		
		return amenity;
	}
}
