package bean;

import java.util.ArrayList;
import java.util.List;

import dao.model.ProgressModel;

public class Progresses
{
    public int total;
    
    public List<ProgressModel> rows = new ArrayList<ProgressModel>();
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public List<ProgressModel> getRows()
    {
        return rows;
    }
    
}

