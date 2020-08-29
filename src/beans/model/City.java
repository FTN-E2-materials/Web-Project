package beans.model;

public class City {
   public String name;
   public String postalCode;
   
	@Override
	public boolean equals(Object obj) {
		try { 
			City otherCity = (City)obj;
			return this.name.equals(otherCity.name) && this.postalCode.equals(otherCity.postalCode);
		}
		catch (Exception e) {
			return false;
		}
	}
}