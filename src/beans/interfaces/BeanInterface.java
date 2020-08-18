 package beans.interfaces;

/** Interface used for classes which need ID for unique identification */
public interface BeanInterface {
   
	/** Get the unique key identifier tied to this object */
    String getKey();
    void setKey(String key);
    /** Validates all the fields of the object to make sure every required field has a correct value.
     *  @throws IllegalArgumentException in case a required field is null, or a certain value is incorrect. */
    void validate() throws IllegalArgumentException;
}