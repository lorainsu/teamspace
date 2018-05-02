package dao.convertor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.constants.Constants;
import common.constants.Ecosphere;
import common.constants.Industry;
import common.constants.Product;
import common.constants.Status;
import dao.base.BaseDaoConvertor;
import dao.model.ProjectModel;

public class ProjectDaoConvertor extends BaseDaoConvertor<ProjectModel>
{
    @Override
    public ProjectModel resultSet2Model(ResultSet resultSet)
        throws SQLException
    {
        ProjectModel model = new ProjectModel();
        model.setId(resultSet.getInt(1));
        model.setIndustry(Industry.valueOf(resultSet.getString(2)));
        model.setEcosphere(Ecosphere.valueOf(resultSet.getString(3)));
        model.setProduct(Product.valueOf(resultSet.getString(4)));
        model.setProj_name(resultSet.getString(5));
        model.setIsv(resultSet.getString(6));
        model.setSpp_solution(resultSet.getString(7));
        model.setProj_background(resultSet.getString(8));
        model.setRequirement(resultSet.getString(9));
        model.setAmount(resultSet.getString(10));
        model.setDelivery_time(resultSet.getString(11));
        model.setAbility(resultSet.getString(12));
        model.setProj_status(Status.valueOf(resultSet.getString(13)));
        model.setLiaison(resultSet.getString(14));
        model.setLiaison_tel(resultSet.getString(15));
        model.setLiaison_email(resultSet.getString(16));
        model.setHuawei_liaison(resultSet.getString(17));
        model.setHuawei_liaison_dept(resultSet.getString(18));
        model.setEsdk_liaison(resultSet.getString(19));
        model.setStart_time(resultSet.getDate(20));
        model.setEnd_time(resultSet.getDate(21));
        model.setRemark(resultSet.getString(22));
        model.setCreator_id(resultSet.getInt(23));
        model.setSpp_status(resultSet.getString(24));
        if (resultSet.getBoolean(25))
        {
            model.setKey_proj(1);
        }
        return model;
    }
    
    @Override
    public String model2Statement4Query(ProjectModel model)
    {
        StringBuilder builder = new StringBuilder();
        if (null != model.getIndustry())
        {
            builder.append(" AND industry = '" + model.getIndustry().toString() + "'");
        }
        if (null != model.getEcosphere())
        {
            builder.append(" AND ecosphere = '" + model.getEcosphere().toString() + "'");
        }
        if (null != model.getProduct())
        {
            builder.append(" AND product = '" + model.getProduct().toString() + "'");
        }
        if (null != model.getProj_name())
        {
            //for web page search
            builder.append(" AND proj_name LIKE '%" + model.getProj_name() + "%'");
        }
        if (null != model.getIsv())
        {
            //for web page search
            builder.append(" AND isv LIKE '%" + model.getIsv() + "%'");
        }
        if (null != model.getSpp_solution())
        {
            builder.append(" AND spp_solution = '" + model.getSpp_solution() + "'");
        }
        if (null != model.getProj_background())
        {
            builder.append(" AND proj_background = '" + model.getProj_background() + "'");
        }
        if (null != model.getRequirement())
        {
            builder.append(" AND requirement = '" + model.getRequirement() + "'");
        }
        if (null != model.getAmount())
        {
            builder.append(" AND amount = '" + model.getAmount() + "'");
        }
        if (null != model.getDelivery_time())
        {
            builder.append(" AND delivery_time = '" + model.getDelivery_time() + "'");
        }
        if (null != model.getAbility())
        {
            builder.append(" AND ability = '" + model.getAbility() + "'");
        }
        if (null != model.getProj_status())
        {
            builder.append(" AND proj_status = '" + model.getProj_status() + "'");
        }
        if (null != model.getLiaison())
        {
            builder.append(" AND liaison = '" + model.getLiaison() + "'");
        }
        if (null != model.getLiaison_tel())
        {
            builder.append(" AND liaison_tel = '" + model.getLiaison_tel() + "'");
        }
        if (null != model.getLiaison_email())
        {
            builder.append(" AND liaison_email = '" + model.getLiaison_email() + "'");
        }
        if (null != model.getHuawei_liaison())
        {
            builder.append(" AND huawei_liaison = '" + model.getHuawei_liaison() + "'");
        }
        if (null != model.getHuawei_liaison_dept())
        {
            builder.append(" AND huawei_liaison_dept = '" + model.getHuawei_liaison_dept() + "'");
        }
        if (null != model.getEsdk_liaison())
        {
            //for web page search
            builder.append(" AND esdk_liaison LIKE '%" + model.getEsdk_liaison() + "%'");
        }
        if (null != model.getStart_time() && null == model.getEnd_time())
        {
            builder.append(" AND ((start_time IS NOT NULL) AND (DATE_FORMAT(start_time, '" + Constants.DATE_FORMAT_SQL
                + "') >= DATE_FORMAT('" + formator.format(model.getStart_time()) + "', '" + Constants.DATE_FORMAT_SQL
                + "')))");
        }
        if (null == model.getStart_time() && null != model.getEnd_time())
        {
            builder.append(" AND ((end_time IS NOT NULL) AND (DATE_FORMAT(end_time, '" + Constants.DATE_FORMAT_SQL
                + "') <= DATE_FORMAT('" + formator.format(model.getEnd_time()) + "', '" + Constants.DATE_FORMAT_SQL
                + "')))");
        }
        if (null != model.getStart_time() && null != model.getEnd_time())
        {
            String cond_start = "((start_time IS NOT NULL) AND (end_time IS NULL) AND (DATE_FORMAT(start_time, '"
                + Constants.DATE_FORMAT_SQL + "') >= DATE_FORMAT('" + formator.format(model.getStart_time()) + "', '"
                + Constants.DATE_FORMAT_SQL + "')) AND (DATE_FORMAT(start_time, '" + Constants.DATE_FORMAT_SQL
                + "') <= DATE_FORMAT('" + formator.format(model.getEnd_time()) + "', '" + Constants.DATE_FORMAT_SQL
                + "')))";
            String cond_end = "((start_time IS NULL) AND (end_time IS NOT NULL) AND (DATE_FORMAT(end_time, '"
                + Constants.DATE_FORMAT_SQL + "') <= DATE_FORMAT('" + formator.format(model.getEnd_time()) + "', '"
                + Constants.DATE_FORMAT_SQL + "')) AND (DATE_FORMAT(end_time, '" + Constants.DATE_FORMAT_SQL
                + "') >= DATE_FORMAT('" + formator.format(model.getStart_time()) + "', '" + Constants.DATE_FORMAT_SQL
                + "')))";
            String cond_start_end =
                "((start_time IS NOT NULL) AND (end_time IS NOT NULL) AND (DATE_FORMAT(start_time, '"
                    + Constants.DATE_FORMAT_SQL + "') >= DATE_FORMAT('" + formator.format(model.getStart_time()) + "', '"
                    + Constants.DATE_FORMAT_SQL + "')) AND (DATE_FORMAT(end_time, '" + Constants.DATE_FORMAT_SQL
                    + "') <= DATE_FORMAT('" + formator.format(model.getEnd_time()) + "', '" + Constants.DATE_FORMAT_SQL
                    + "')))";
            builder.append(" AND (" + cond_start + " OR " + cond_end + " OR " + cond_start_end + ")");
        }
        if (null != model.getRemark())
        {
            builder.append(" AND remark = '" + model.getRemark() + "'");
        }
        if (0 != model.getCreator_id())
        {
            builder.append(" AND creator_id = " + model.getCreator_id() + "");
        }
        if (null != model.getSpp_status())
        {
            builder.append(" AND spp_status = '" + model.getSpp_status() + "'");
        }
        if (-1 != model.getKey_proj())
        {
            builder.append(" AND key_proj = " + model.getKey_proj());
        }
        
        return builder.toString();
    }
    
    @Override
    public String model2Statement4Update(ProjectModel model)
    {
        StringBuilder builder = new StringBuilder();
        if (null != model.getIndustry())
        {
            builder.append("industry = '" + model.getIndustry().toString() + "',");
        }
        if (null != model.getEcosphere())
        {
            builder.append("ecosphere = '" + model.getEcosphere().toString() + "',");
        }
        if (null != model.getProduct())
        {
            builder.append("product = '" + model.getProduct().toString() + "',");
        }
        if (null != model.getProj_name())
        {
            builder.append("proj_name = '" + model.getProj_name() + "',");
        }
        if (null != model.getIsv())
        {
            builder.append("isv = '" + model.getIsv() + "',");
        }
        if (null != model.getSpp_solution())
        {
            builder.append("spp_solution = '" + model.getSpp_solution() + "',");
        }
        if (null != model.getProj_background())
        {
            builder.append("proj_background = '" + model.getProj_background() + "',");
        }
        if (null != model.getRequirement())
        {
            builder.append("requirement = '" + model.getRequirement() + "',");
        }
        if (null != model.getAmount())
        {
            builder.append("amount = '" + model.getAmount() + "',");
        }
        if (null != model.getDelivery_time())
        {
            builder.append("delivery_time = '" + model.getDelivery_time() + "',");
        }
        if (null != model.getAbility())
        {
            builder.append("ability = '" + model.getAbility() + "',");
        }
        if (null != model.getProj_status())
        {
            builder.append("proj_status = '" + model.getProj_status() + "',");
        }
        if (null != model.getLiaison())
        {
            builder.append("liaison = '" + model.getLiaison() + "',");
        }
        if (null != model.getLiaison_tel())
        {
            builder.append("liaison_tel = '" + model.getLiaison_tel() + "',");
        }
        if (null != model.getLiaison_email())
        {
            builder.append("liaison_email = '" + model.getLiaison_email() + "',");
        }
        if (null != model.getHuawei_liaison())
        {
            builder.append("huawei_liaison = '" + model.getHuawei_liaison() + "',");
        }
        if (null != model.getHuawei_liaison_dept())
        {
            builder.append("huawei_liaison_dept = '" + model.getHuawei_liaison_dept() + "',");
        }
        if (null != model.getEsdk_liaison())
        {
            builder.append("esdk_liaison = '" + model.getEsdk_liaison() + "',");
        }
        if (null != model.getStart_time())
        {
            builder.append("start_time = '" + formator.format(model.getStart_time()) + "',");
        }
        if (null != model.getEnd_time())
        {
            builder.append("end_time = '" + formator.format(model.getEnd_time()) + "',");
        }
        if (null != model.getRemark())
        {
            builder.append("remark = '" + model.getRemark() + "',");
        }
        if (0 != model.getCreator_id())
        {
            builder.append("creator_id = " + model.getCreator_id() + ",");
        }
        if (null != model.getSpp_status())
        {
            builder.append("spp_status = '" + model.getSpp_status() + "',");
        }
        builder.append("key_proj = " + model.getKey_proj() + ",");
        
        String sql = builder.toString();
        if (sql.endsWith(","))
        {
            sql = sql.substring(0, sql.length() - 1);
        }
        return sql;
    }
    
    @Override
    public void model2Statement4Insert(PreparedStatement pstate, ProjectModel model)
        throws SQLException
    {
        if (null != model.getIndustry())
        {
            pstate.setString(1, model.getIndustry().toString());
        }
        else
        {
            pstate.setString(1, Industry.其他.toString());
        }
        if (null != model.getEcosphere())
        {
            pstate.setString(2, model.getEcosphere().toString());
        }
        else
        {
            pstate.setString(2, Ecosphere.其他.toString());
        }
        if (null != model.getProduct())
        {
            pstate.setString(3, model.getProduct().toString());
        }
        else
        {
            pstate.setString(3, Product.其他.toString());
        }
        
        pstate.setString(4, model.getProj_name());
        pstate.setString(5, model.getIsv());
        pstate.setString(6, model.getSpp_solution());
        pstate.setString(7, model.getProj_background());
        pstate.setString(8, model.getRequirement());
        pstate.setString(9, model.getAmount());
        pstate.setString(10, model.getDelivery_time());
        pstate.setString(11, model.getAbility());
        if (null != model.getProj_status())
        {
            pstate.setString(12, model.getProj_status().toString());
        }
        else
        {
            pstate.setString(12, Status.未知.toString());
        }
        pstate.setString(13, model.getLiaison());
        pstate.setString(14, model.getLiaison_tel());
        pstate.setString(15, model.getLiaison_email());
        pstate.setString(16, model.getHuawei_liaison());
        pstate.setString(17, model.getHuawei_liaison_dept());
        pstate.setString(18, model.getEsdk_liaison());
        
        if (null != model.getStart_time())
        {
            pstate.setString(19, formator.format(model.getStart_time()));
        }
        else
        {
            pstate.setString(19, null);
        }
        if (null != model.getEnd_time())
        {
            pstate.setString(20, formator.format(model.getEnd_time()));
        }
        else
        {
            pstate.setString(20, null);
        }
        
        pstate.setString(21, model.getRemark());
        pstate.setInt(22, model.getCreator_id());
        pstate.setString(23, model.getSpp_status());
        if (-1 != model.getKey_proj())
        {
            pstate.setInt(24, model.getKey_proj());
        }
        else
        {
            pstate.setInt(24, 0);
        }
    }
    
    @Override
    public String getInsertStatement()
    {
        return "(industry,ecosphere,product,proj_name,isv,spp_solution,proj_background,requirement,amount,delivery_time,ability,proj_status,liaison,liaison_tel,liaison_email,huawei_liaison,huawei_liaison_dept,esdk_liaison,start_time,end_time,remark,creator_id,spp_status,key_proj) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }
}

