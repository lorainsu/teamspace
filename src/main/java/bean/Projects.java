package bean;

import java.util.ArrayList;
import java.util.List;

public class Projects
{
    public int total;
    
    public List<ProjectBean> rows = new ArrayList<ProjectBean>();
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public List<ProjectBean> getRows()
    {
        return rows;
    }
    
}

