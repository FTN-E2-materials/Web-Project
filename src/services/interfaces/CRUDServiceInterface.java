package services.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import beans.model.DatabaseEntity;
import util.RequestWrapper;

/** Offers methods for CRUD services which need to verify the users identity before 
 *  handling the data itself. Every method implemented should add Context annotation next 
 *  to the HttpServletRequest argument.
 * @author Nikola
 * @param <T>
 */
public interface CRUDServiceInterface<T extends DatabaseEntity> {
	public Response create(T obj, HttpServletRequest request);
	public Response update(T obj, HttpServletRequest request);
	public Response getAll(HttpServletRequest request);
	public Response getByID(String key, HttpServletRequest request);
	public Response delete(RequestWrapper requestWrapper, HttpServletRequest request);
}
