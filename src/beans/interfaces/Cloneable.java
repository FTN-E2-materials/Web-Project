package beans.interfaces;

/** Interface to provide a clone method for objects */
public interface Cloneable<T extends FieldWrapperInterface> {
	/** Copy all the fields from the T obj argument into the calling object */
	public void clone(T obj);
}
