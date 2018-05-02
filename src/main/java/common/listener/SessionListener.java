package common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import common.session.SessionManager;

public class SessionListener implements HttpSessionListener
{
    public void sessionCreated(HttpSessionEvent sessionEvent)
    {
        System.out.println("sessionCreated:" + sessionEvent.getSession().getId());
    }
    
    public void sessionDestroyed(HttpSessionEvent sessionEvent)
    {
        HttpSession session = sessionEvent.getSession();
        SessionManager.getInstance().removeSession(session.getId());
        System.out.println("sessionDestroyed:" + session.getId());
    }
}

