package dao.model;

import java.util.Date;

import dao.base.BaseModel;

public class ProgressModel extends BaseModel
{
    private Date update_time;
    
    private String content;
    
    private int proj_id;
    
    private int creator_id;
    
    public Date getUpdate_time()
    {
        return update_time;
    }
    
    public void setUpdate_time(Date update_time)
    {
        this.update_time = update_time;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public int getProj_id()
    {
        return proj_id;
    }
    
    public void setProj_id(int proj_id)
    {
        this.proj_id = proj_id;
    }
    
    public int getCreator_id()
    {
        return creator_id;
    }
    
    public void setCreator_id(int creator_id)
    {
        this.creator_id = creator_id;
    }
    
}

