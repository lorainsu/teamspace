package dao.convertor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.base.BaseDaoConvertor;
import dao.model.ProgressModel;

public class ProgressDaoConvertor extends BaseDaoConvertor<ProgressModel>
{
    
    @Override
    public ProgressModel resultSet2Model(ResultSet resultSet)
        throws SQLException
    {
        ProgressModel model = new ProgressModel();
        model.setId(resultSet.getInt(1));
        model.setUpdate_time(resultSet.getDate(2));
        model.setContent(resultSet.getString(3));
        model.setProj_id(resultSet.getInt(4));
        model.setCreator_id(resultSet.getInt(5));
        return model;
    }
    
    @Override
    public String model2Statement4Query(ProgressModel model)
    {
        StringBuilder builder = new StringBuilder();
        if (null != model.getUpdate_time())
        {
            builder.append(" AND update_time = '" + formator.format(model.getUpdate_time()) + "'");
        }
        if (null != model.getContent())
        {
            builder.append(" AND content = '" + model.getContent() + "'");
        }
        if (0 != model.getProj_id())
        {
            builder.append(" AND proj_id = " + model.getProj_id());
        }
        if (0 != model.getCreator_id())
        {
            builder.append(" AND creator_id = " + model.getCreator_id());
        }
        
        return builder.toString();
    }
    
    @Override
    public String model2Statement4Update(ProgressModel model)
    {
        StringBuilder builder = new StringBuilder();
        if (null != model.getUpdate_time())
        {
            builder.append("update_time = '" + formator.format(model.getUpdate_time()) + "',");
        }
        if (null != model.getContent())
        {
            builder.append("content = '" + model.getContent() + "',");
        }
        if (0 != model.getProj_id())
        {
            builder.append("proj_id = " + model.getProj_id() + ",");
        }
        if (0 != model.getCreator_id())
        {
            builder.append("creator_id = " + model.getCreator_id() + ",");
        }
        
        String sql = builder.toString();
        if (sql.endsWith(","))
        {
            sql = sql.substring(0, sql.length() - 1);
        }
        return sql;
    }
    
    @Override
    public void model2Statement4Insert(PreparedStatement pstate, ProgressModel model)
        throws SQLException
    {
        if (null != model.getUpdate_time())
        {
            pstate.setString(1, formator.format(model.getUpdate_time()));
        }
        else
        {
            pstate.setString(1, null);
        }
        pstate.setString(2, model.getContent());
        pstate.setInt(3, model.getProj_id());
        pstate.setInt(4, model.getCreator_id());
    }
    
    @Override
    public String getInsertStatement()
    {
        return "(update_time,content,proj_id,creator_id) VALUES(?,?,?,?)";
    }
}

