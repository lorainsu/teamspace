package dao.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import common.utils.PropertiesUtils;
import common.utils.StringUtils;

public abstract class BaseDao<T extends BaseModel>
{
    public String TBL_NAME = null;
    
    public String ORDER_CONDTION = null;
    
    public BaseDaoConvertor<T> convertor = null;
    
    private static String MYSQL_URL = PropertiesUtils.getValue("mysql_url").trim();
    
    private static String MYSQL_USERNAME = PropertiesUtils.getValue("mysql_username").trim();
    
    private static String MYSQL_PASSWD = PropertiesUtils.getValue("mysql_passwd").trim();
    
    private static Connection conn;
    
    public BaseDao()
        throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        if (null == conn)
        {
            initConnection();
        }
    }
    
    private void initConnection()
        throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        
        if (StringUtils.isEmpty(MYSQL_URL) || StringUtils.isEmpty(MYSQL_USERNAME) || StringUtils.isEmpty(MYSQL_PASSWD))
        {
            throw new IllegalArgumentException("illegal MySQL config");
        }
        else
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL + "?characterEncoding=utf8", MYSQL_USERNAME, MYSQL_PASSWD);
        }
    }
    
    public int doCount(T model)
    {
        Statement state = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT COUNT(*) FROM " + TBL_NAME + " WHERE 1=1" + convertor.model2Statement4Query(model);
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            state = conn.createStatement();
            resultSet = state.executeQuery(sql);
            
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
            else
            {
                System.out.println("Count Project with empty output.");
            }
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != resultSet)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (null != state)
            {
                try
                {
                    state.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return -1;
    }
    
    public T doQuery(int id)
    {
        PreparedStatement pstate = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * FROM " + TBL_NAME + " WHERE id=?";
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            pstate = conn.prepareStatement(sql);
            pstate.setInt(1, id);
            resultSet = pstate.executeQuery();
            
            if (resultSet.next())
            {
                return convertor.resultSet2Model(resultSet);
            }
            else
            {
                System.out.println("Empty query output");
            }
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != resultSet)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (null != pstate)
            {
                try
                {
                    pstate.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return null;
    }
    
    public List<T> doQuery(T model)
    {
        Statement state = null;
        ResultSet resultSet = null;
        List<T> modelList = new ArrayList<T>();
        
        String sql = "SELECT * FROM " + TBL_NAME + " WHERE 1=1" + convertor.model2Statement4Query(model) + " ORDER BY "
            + ORDER_CONDTION;
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            state = conn.createStatement();
            resultSet = state.executeQuery(sql);
            while (resultSet.next())
            {
                modelList.add(convertor.resultSet2Model(resultSet));
            }
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != resultSet)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (null != state)
            {
                try
                {
                    state.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return modelList;
    }
    
    public List<T> doQuery(T model, int pageNo, int pageSize)
    {
        PreparedStatement pstate = null;
        ResultSet resultSet = null;
        List<T> modelList = new ArrayList<T>();
        
        String sql = "SELECT * FROM " + TBL_NAME + " WHERE 1=1" + convertor.model2Statement4Query(model) + " ORDER BY "
            + ORDER_CONDTION + " LIMIT ?, ?";
        
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            pstate = conn.prepareStatement(sql);
            pstate.setInt(1, (pageNo - 1) * pageSize);
            pstate.setInt(2, pageSize);
            resultSet = pstate.executeQuery();
            while (resultSet.next())
            {
                modelList.add(convertor.resultSet2Model(resultSet));
            }
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != resultSet)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (null != pstate)
            {
                try
                {
                    pstate.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return modelList;
    }
    
    public synchronized boolean doInsert(T model)
    {
        PreparedStatement pstate = null;
        
        String sql = "INSERT INTO " + TBL_NAME + convertor.getInsertStatement();
        
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            pstate = conn.prepareStatement(sql);
            convertor.model2Statement4Insert(pstate, model);
            
            int result = pstate.executeUpdate();
            if (result > 0)
            {
                return true;
            }
            else
            {
                System.out.println("Fail Insert:" + result);
            }
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != pstate)
            {
                try
                {
                    pstate.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return false;
    }
    
    public synchronized boolean doDelete(int id)
    {
        PreparedStatement pstate = null;
        
        String sql = "DELETE FROM " + TBL_NAME + " WHERE id=?";
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            pstate = conn.prepareStatement(sql);
            pstate.setInt(1, id);
            int result = pstate.executeUpdate();
            
            if (result > 0)
            {
                return true;
            }
            else
            {
                System.out.println("Fail Delete:" + result);
            }
            
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != pstate)
            {
                try
                {
                    pstate.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return false;
    }
    
    public synchronized boolean doUpdate(T model)
    {
        PreparedStatement pstate = null;
        
        String sql_param = convertor.model2Statement4Update(model);
        if ("".equals(sql_param.trim()))
        {
            System.out.println("Update failed: param is null");
            return false;
        }
        
        if (model.getId() < 1)
        {
            System.out.println("Update failed: illegal model id");
            return false;
        }
        
        String sql = "UPDATE " + TBL_NAME + " SET " + sql_param + " WHERE id=?";
        try
        {
            if (null == conn)
            {
                initConnection();
            }
            pstate = conn.prepareStatement(sql);
            pstate.setInt(1, model.getId());
            int result = pstate.executeUpdate();
            
            if (result > 0)
            {
                return true;
            }
            else
            {
                System.out.println("Fail Update:" + result);
            }
        }
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            if (e instanceof CommunicationsException)
            {
                conn = null;
            }
            e.printStackTrace();
        }
        finally
        {
            if (null != pstate)
            {
                try
                {
                    pstate.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
        return false;
    }
}

