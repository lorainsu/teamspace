package common.session;

import java.util.HashMap;
import java.util.Map;

import common.ThreadLocalHandler;
import common.constants.Constants;
import dao.model.UserModel;

public class SessionManager
{
    private static Map<String, SessionInfo> sessionMap = new HashMap<String, SessionInfo>();
    
    private static SessionManager sessionManager = null;
    
    private SessionManager()
    {
    }
    
    public static synchronized SessionManager getInstance()
    {
        if (null == sessionManager)
        {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }
    
    public SessionInfo getSession(String sessionId)
    {
        return sessionMap.get(sessionId);
    }
    
    public void setSession(String sessionId, UserModel user)
    {
        System.out.println("Save Session:" + sessionId);
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUser(user);
        sessionMap.put(sessionId, sessionInfo);
    }
    
    public void removeSession(String sessionId)
    {
        sessionMap.remove(sessionId);
    }
    
    public boolean isSessionExist(String sessionId)
    {
        return null == sessionId ? false : sessionMap.containsKey(sessionId);
    }
    
    public String getCurrentSessionId()
    {
        return (String)ThreadLocalHandler.get(Constants.THREAD_LOCAL_SESSION);
    }
}

