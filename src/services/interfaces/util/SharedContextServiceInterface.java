package services.interfaces.util;

import dao.BeanDAO;

public interface SharedContextServiceInterface {
	public BeanDAO<?> createDAO();
}
