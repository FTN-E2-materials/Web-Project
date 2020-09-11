package util.services;

import javax.servlet.ServletContext;

import dao.BeanDAO;
import services.interfaces.util.SharedContextServiceInterface;


/** Singleton class to allow access to all shared objects held within the ServletContext of the app */
public class SharedContextService {
	
	public static SharedContextService instance;
	private ServletContext ctx;
	
	public static SharedContextService getInstance(ServletContext ctx) {
		if (instance == null)
			instance = new SharedContextService(ctx);
	
		return instance;
	}
	
	public static SharedContextService getInstance() {
		return instance;
	}
	
	private SharedContextService(ServletContext ctx) {
		this.ctx = ctx;
	}
	
	public <T extends SharedContextServiceInterface> BeanDAO<?> getDAO(T service, String d) {
		if (ctx.getAttribute(d) == null)
			ctx.setAttribute(d, service.createDAO());
		
		return (BeanDAO<?>)ctx.getAttribute(d);
	}
}
