package dao.service;

import java.sql.SQLException;

import dao.DaoConvertorFactory;
import dao.base.BaseDaoServiceImpl;
import dao.model.UserModel;

public class UserDao extends BaseDaoServiceImpl<UserModel>
{
    private static UserDao instance = null;
    
    private UserDao()
        throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        super();
        
        TBL_NAME = "tbl_user";
        ORDER_CONDTION = "id ASC";
        convertor = DaoConvertorFactory.getConvertor(UserModel.class);
    }
    
    public static synchronized UserDao getInstance()
    {
        if (null == instance)
        {
            try
            {
                instance = new UserDao();
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

