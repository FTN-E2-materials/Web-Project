package beans.interfaces;

/** Interface used for classes which are used as identification tokens for user session */
public interface SessionToken {
	
	public boolean isGuest();
	public boolean isHost();
	public boolean isAdmin();
	
	public String getID();
}
