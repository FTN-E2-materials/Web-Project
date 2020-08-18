package services.interfaces;

import javax.ws.rs.core.Response;

public interface HttpResponseHandlerInterface {
	public Response BadRequest();
	public Response BadRequest(Object object);
	public Response ForbiddenRequest();
	public Response OK(Object object);
	public Response AuthFailed(Object object);
}
