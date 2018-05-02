package dao.service;

import java.sql.SQLException;

import dao.DaoConvertorFactory;
import dao.base.BaseDaoServiceImpl;
import dao.model.ProjectModel;

public class ProjectDao extends BaseDaoServiceImpl<ProjectModel>
{
    private static ProjectDao instance = null;
    
    private ProjectDao()
        throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        super();
        
        TBL_NAME = "tbl_proj";
        ORDER_CONDTION = "id DESC";
        convertor = DaoConvertorFactory.getConvertor(ProjectModel.class);
    }
    
    public static synchronized ProjectDao getInstance()
    {
        if (null == instance)
        {
            try
            {
                instance = new ProjectDao();
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

