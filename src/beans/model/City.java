package beans.model;

public class City {
   public String name;
   public String postalCode;
   
	public boolean containsFilterParameters(Object obj) {
		try { 
			City otherCity = (City)obj;
			return this.name.toLowerCase().contains(otherCity.name.toLowerCase()) && this.postalCode.equals(otherCity.postalCode);
		}
		catch (Exception e) {
			return false;
		}
	}
}