package common.session;

import dao.model.UserModel;

public class SessionInfo
{
    private UserModel user;
    
    public UserModel getUser()
    {
        return user;
    }
    
    public void setUser(UserModel user)
    {
        this.user = user;
    }
    
}

