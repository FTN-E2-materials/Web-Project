package services.interfaces;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import beans.model.DatabaseEntity;
import util.RequestWrapper;

/** Offers methods for CRUD services which need to verify the users identity before 
 *  handling the data itself. Every method implemented should add Context annotation next 
 *  to the HttpServletRequest argument.
 * @author Nikola
 * @param <T>
 */
public interface CRUDServiceInterface<T extends DatabaseEntity> {
	public T create(T obj, HttpServletRequest request);
	public T update(T obj, HttpServletRequest request);
	public Collection<T> getAll(HttpServletRequest request);
	public T getByID(String key, HttpServletRequest request);
	public T delete(RequestWrapper requestWrapper, HttpServletRequest request);
}
