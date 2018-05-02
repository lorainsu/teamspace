package dao.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import common.constants.Constants;

public abstract class BaseDaoConvertor<T>
{
    public static SimpleDateFormat formator = new SimpleDateFormat(Constants.DATE_FORMAT_DAO);
    
    public abstract T resultSet2Model(ResultSet resultSet)
        throws SQLException;
    
    public abstract String model2Statement4Query(T model);
    
    public abstract String model2Statement4Update(T model);
    
    public abstract void model2Statement4Insert(PreparedStatement pstate, T model)
        throws SQLException;
    
    public abstract String getInsertStatement();
}

