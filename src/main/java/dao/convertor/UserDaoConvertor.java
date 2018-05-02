package dao.convertor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.base.BaseDaoConvertor;
import dao.model.UserModel;

public class UserDaoConvertor extends BaseDaoConvertor<UserModel>
{
    
    @Override
    public UserModel resultSet2Model(ResultSet resultSet)
        throws SQLException
    {
        UserModel model = new UserModel();
        model.setId(resultSet.getInt(1));
        model.setName(resultSet.getString(2));
        model.setEmail(resultSet.getString(3));
        model.setStaff_no(resultSet.getString(4));
        model.setLast_login(resultSet.getDate(5));
        model.setRole_id(resultSet.getInt(6));
        return model;
    }
    
    @Override
    public String model2Statement4Query(UserModel model)
    {
        StringBuilder builder = new StringBuilder();
        if (null != model.getName())
        {
            builder.append(" AND name = '" + model.getName() + "'");
        }
        if (null != model.getEmail())
        {
            builder.append(" AND email = '" + model.getEmail() + "'");
        }
        if (null != model.getStaff_no())
        {
            builder.append(" AND staff_no = '" + model.getStaff_no() + "'");
        }
        if (null != model.getLast_login())
        {
            builder.append(" AND last_login = '" + formator.format(model.getLast_login()) + "'");
        }
        if (0 != model.getRole_id())
        {
            builder.append(" AND role_id = " + model.getRole_id());
        }
        
        return builder.toString();
    }
    
    @Override
    public String model2Statement4Update(UserModel model)
    {
        StringBuilder builder = new StringBuilder();
        
        if (null != model.getName())
        {
            builder.append("name = '" + model.getName() + "',");
        }
        if (null != model.getEmail())
        {
            builder.append("email = '" + model.getEmail() + "',");
        }
        if (null != model.getStaff_no())
        {
            builder.append("staff_no = '" + model.getStaff_no() + "',");
        }
        if (null != model.getLast_login())
        {
            builder.append("last_login = '" + formator.format(model.getLast_login()) + "',");
        }
        if (0 != model.getRole_id())
        {
            builder.append("role_id = " + model.getRole_id() + ",");
        }
        
        String sql = builder.toString();
        if (sql.endsWith(","))
        {
            sql = sql.substring(0, sql.length() - 1);
        }
        return sql;
    }
    
    @Override
    public void model2Statement4Insert(PreparedStatement pstate, UserModel model)
        throws SQLException
    {
        
        pstate.setString(1, model.getName());
        pstate.setString(2, model.getEmail());
        pstate.setString(3, model.getStaff_no());
        if (null != model.getLast_login())
        {
            pstate.setString(4, formator.format(model.getLast_login()));
        }
        else
        {
            pstate.setString(4, null);
        }
        pstate.setInt(5, model.getRole_id());
    }
    
    @Override
    public String getInsertStatement()
    {
        return "(name,email,staff_no,last_login,role_id) VALUES(?,?,?,?,?)";
    }
    
}

