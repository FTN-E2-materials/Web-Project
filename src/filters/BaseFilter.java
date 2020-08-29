package filters;

import java.util.Collection;

import beans.model.DatabaseEntity;
import dao.BeanDAO;


/** Class used for database filtering */
public abstract class BaseFilter<T extends DatabaseEntity> {
	
	protected Collection<T> entities;
	
	/** Initiates a new filtering operation on the database operated by the given DAO object */
	protected BaseFilter<T> initiateFilterOperation(BeanDAO<T> dao) {
		entities = dao.getAll();
		
		return this;
	}
	
	/** Returns the result of the previous chain of filtering operations */
	public Collection<T> getResults() {
		return entities;
	}
}
