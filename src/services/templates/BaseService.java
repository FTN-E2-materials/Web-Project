package services.templates;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import beans.model.DatabaseEntity;
import dao.BeanDAO;
import services.interfaces.DatabaseServiceInterface;


/** Abstract template for a basic service class. Contains a ServletContext field
 *  , database access and storage access.
 * @author Nikola
 *
 * @param <T>
 * @param <DAO>
 */
public abstract class BaseService<T extends DatabaseEntity, DAO extends BeanDAO<T>> implements DatabaseServiceInterface {
	
	@Context
	protected ServletContext ctx;
	
	/** This string is used to identify unique database names accross the server. */
	protected String databaseAttributeString;
	/** Location of the data storage file where the data will be kept on the disk. */
	protected String storageFileLocation;
}
