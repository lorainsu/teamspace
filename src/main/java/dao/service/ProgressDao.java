package dao.service;

import java.sql.SQLException;

import dao.DaoConvertorFactory;
import dao.base.BaseDaoServiceImpl;
import dao.model.ProgressModel;

public class ProgressDao extends BaseDaoServiceImpl<ProgressModel>
{
    private static ProgressDao instance = null;
    
    public ProgressDao()
        throws ClassNotFoundException, SQLException
    {
        super();
        
        TBL_NAME = "tbl_proj_progress";
        ORDER_CONDTION = "update_time DESC";
        convertor = DaoConvertorFactory.getConvertor(ProgressModel.class);
    }
    
    public static synchronized ProgressDao getInstance()
    {
        if (null == instance)
        {
            try
            {
                instance = new ProgressDao();
            }
            catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
            {
                e.printStackTrace();
                //need to log
            }
        }
        return instance;
    }
    
}

