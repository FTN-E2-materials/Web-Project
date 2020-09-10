package services.templates;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;


/** Abstract template for the most basic REST service class. Contains a ServletContext field.
 * @author Nikola
 */
public abstract class BaseService {

	@Context
	protected ServletContext ctx;
}
