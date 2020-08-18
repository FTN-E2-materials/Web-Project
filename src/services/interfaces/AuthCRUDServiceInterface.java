package services.interfaces;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import beans.model.DatabaseEntity;
import util.RequestWrapper;

public interface AuthCRUDServiceInterface<T extends DatabaseEntity> {
	public T create(T obj, HttpServletRequest request);
	public T update(T obj, HttpServletRequest request);
	public Collection<T> getAll(HttpServletRequest request);
	public T getByID(String key, HttpServletRequest request);
	public T delete(RequestWrapper requestWrapper, HttpServletRequest request);
}
