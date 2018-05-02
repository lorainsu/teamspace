package dao.model;

import java.util.Date;

import dao.base.BaseModel;

public class UserModel extends BaseModel
{
    private String name;
    
    private String email;
    
    private String staff_no;
    
    private Date last_login;
    
    private int role_id;
    
    public String getStaff_no()
    {
        return staff_no;
    }
    
    public void setStaff_no(String staff_no)
    {
        this.staff_no = staff_no;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public Date getLast_login()
    {
        return last_login;
    }
    
    public void setLast_login(Date last_login)
    {
        this.last_login = last_login;
    }
    
    public int getRole_id()
    {
        return role_id;
    }
    
    public void setRole_id(int role_id)
    {
        this.role_id = role_id;
    }
    
}

