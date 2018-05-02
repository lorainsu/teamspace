package dao.model;

import dao.base.BaseModel;

public class LogModel extends BaseModel
{
    private String logTime;
    
    private String level;
    
    private String module;
    
    private String user_id;
    
    private String client_ip;
    
    private String log_message;
    
    public String getLogTime()
    {
        return logTime;
    }
    
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }
    
    public String getLevel()
    {
        return level;
    }
    
    public void setLevel(String level)
    {
        this.level = level;
    }
    
    public String getModule()
    {
        return module;
    }
    
    public void setModule(String module)
    {
        this.module = module;
    }
    
    public String getUser_id()
    {
        return user_id;
    }
    
    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }
    
    public String getClient_ip()
    {
        return client_ip;
    }
    
    public void setClient_ip(String client_ip)
    {
        this.client_ip = client_ip;
    }
    
    public String getLog_message()
    {
        return log_message;
    }
    
    public void setLog_message(String log_message)
    {
        this.log_message = log_message;
    }
    
}

