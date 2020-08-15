 package beans.interfaces;

/** Interface used for classes which need ID for unique identification */
public interface BeanInterface {
   
   String getKey();
   void setKey(String key);
   /** Validates all the fields of the object to make sure every required field has a correct value */
   void validate();
}