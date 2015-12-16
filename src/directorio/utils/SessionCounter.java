package directorio.utils;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;

public class SessionCounter	implements HttpSessionListener {

	private	static int activeSessions =	0;

	/* Session Creation	Event */
	public void	sessionCreated(HttpSessionEvent	se)	{
		activeSessions++;
	}

	/* Session Invalidation	Event */
	public void	sessionDestroyed(HttpSessionEvent se) {
		if(activeSessions >	0)
			activeSessions--;
	}

	public static int getActiveSessions() {
		return activeSessions;
	}
}

