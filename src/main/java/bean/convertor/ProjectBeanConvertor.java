package bean.convertor;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import common.constants.Constants;
import common.constants.Ecosphere;
import common.constants.Industry;
import common.constants.Product;
import common.constants.Status;
import common.utils.StringUtils;
import dao.model.ProjectModel;

public class ProjectBeanConvertor
{
    private static SimpleDateFormat formator = new SimpleDateFormat(Constants.DATE_FORMAT_BEAN);
    
    public static ProjectModel getModel(HttpServletRequest req)
    {
        ProjectModel model = new ProjectModel();
        
        String industry = req.getParameter("industry");
        String ecosphere = req.getParameter("ecosphere");
        String product = req.getParameter("product");
        String proj_name = req.getParameter("proj_name");
        String isv = req.getParameter("isv");
        String spp_solution = req.getParameter("spp_solution");
        String proj_background = req.getParameter("proj_background");
        String requirement = req.getParameter("requirement");
        String amount = req.getParameter("amount");
        String delivery_time = req.getParameter("delivery_time");
        String ability = req.getParameter("ability");
        String proj_status = req.getParameter("proj_status");
        String liaison = req.getParameter("liaison");
        String liaison_tel = req.getParameter("liaison_tel");
        String liaison_email = req.getParameter("liaison_email");
        String huawei_liaison = req.getParameter("huawei_liaison");
        String huawei_liaison_dept = req.getParameter("huawei_liaison_dept");
        String esdk_liaison = req.getParameter("esdk_liaison");
        String start_time = req.getParameter("start_time");
        String end_time = req.getParameter("end_time");
        String remark = req.getParameter("remark");
        String creator_id = req.getParameter("creator_id");
        String spp_status = req.getParameter("spp_status");
        String key_proj = req.getParameter("key_proj");
        
        if (!StringUtils.isEmpty(industry))
        {
            try
            {
                model.setIndustry(Industry.valueOf(industry));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        if (!StringUtils.isEmpty(ecosphere))
        {
            try
            {
                model.setEcosphere(Ecosphere.valueOf(ecosphere));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        if (!StringUtils.isEmpty(product))
        {
            try
            {
                model.setProduct(Product.valueOf(product));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        if (!StringUtils.isEmpty(proj_name))
            model.setProj_name(proj_name);
        if (!StringUtils.isEmpty(isv))
            model.setIsv(isv);
        if (!StringUtils.isEmpty(spp_solution))
            model.setSpp_solution(spp_solution);
        if (!StringUtils.isEmpty(proj_background))
            model.setProj_background(proj_background);
        if (!StringUtils.isEmpty(requirement))
            model.setRequirement(requirement);
        if (!StringUtils.isEmpty(amount))
            model.setAmount(amount);
        if (!StringUtils.isEmpty(delivery_time))
            model.setDelivery_time(delivery_time);
        if (!StringUtils.isEmpty(ability))
            model.setAbility(ability);
        if (!StringUtils.isEmpty(proj_status))
        {
            try
            {
                model.setProj_status(Status.valueOf(proj_status));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        if (!StringUtils.isEmpty(liaison))
            model.setLiaison(liaison);
        if (!StringUtils.isEmpty(liaison_tel))
            model.setLiaison_tel(liaison_tel);
        if (!StringUtils.isEmpty(liaison_email))
            model.setLiaison_email(liaison_email);
        if (!StringUtils.isEmpty(huawei_liaison))
            model.setHuawei_liaison(huawei_liaison);
        if (!StringUtils.isEmpty(huawei_liaison_dept))
            model.setHuawei_liaison_dept(huawei_liaison_dept);
        if (!StringUtils.isEmpty(esdk_liaison))
            model.setEsdk_liaison(esdk_liaison);
        
        if (!StringUtils.isEmpty(start_time))
        {
            try
            {
                Date startTime = formator.parse(start_time);
                model.setStart_time(startTime);
            }
            catch (ParseException e)
            {
            }
        }
        
        if (!StringUtils.isEmpty(end_time))
        {
            try
            {
                Date endTime = formator.parse(end_time);
                model.setEnd_time(endTime);
            }
            catch (ParseException e)
            {
            }
        }
        
        if (!StringUtils.isEmpty(remark))
            model.setRemark(remark);
        
        int creatorId = StringUtils.getId(creator_id);
        if (creatorId > 0)
        {
            model.setCreator_id(creatorId);
        }
        
        if (!StringUtils.isEmpty(spp_status))
            model.setSpp_status(spp_status);
        
        if (!StringUtils.isEmpty(key_proj))
        {
            if ("1".equals(key_proj))
            {
                model.setKey_proj(1);
            }
            else if (!"0".equals(key_proj))
            {
                return null;
            }
        }
        else
        {
            model.setKey_proj(-1);
        }
        return model;
    }
    
    public static ProjectModel getModel4Put(HttpServletRequest req)
        throws IOException
    {
        ProjectModel model = new ProjectModel();
        
        String formLine = req.getReader().readLine();
        formLine = URLDecoder.decode(formLine, "UTF-8");
        
        String[] params = formLine.split("&");
        Map<String, String> paramMap = new HashMap<String, String>();
        
        for (String param : params)
        {
            if (param.contains("="))
            {
                if (1 == param.split("=").length)
                {
                    paramMap.put(param.split("=")[0], "");
                }
                if (2 == param.split("=").length)
                {
                    paramMap.put(param.split("=")[0], param.split("=")[1]);
                }
            }
        }
        
        int id = StringUtils.getId(paramMap.get("id"));
        if (id > 0)
        {
            model.setId(id);
        }
        else
        {
            return null;
        }
        
        if (!StringUtils.isEmpty(paramMap.get("industry")))
        {
            try
            {
                model.setIndustry(Industry.valueOf(paramMap.get("industry")));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        else
        {
            model.setIndustry(Industry.其他);
        }
        if (!StringUtils.isEmpty(paramMap.get("ecosphere")))
        {
            try
            {
                model.setEcosphere(Ecosphere.valueOf(paramMap.get("ecosphere")));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        else
        {
            model.setEcosphere(Ecosphere.其他);
        }
        if (!StringUtils.isEmpty(paramMap.get("product")))
        {
            try
            {
                model.setProduct(Product.valueOf(paramMap.get("product")));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        else
        {
            model.setProduct(Product.其他);
        }
        if (null != paramMap.get("proj_name"))
            model.setProj_name(paramMap.get("proj_name"));
        if (null != paramMap.get("isv"))
            model.setIsv(paramMap.get("isv"));
        if (null != paramMap.get("spp_solution"))
            model.setSpp_solution(paramMap.get("spp_solution"));
        if (null != paramMap.get("proj_background"))
            model.setProj_background(paramMap.get("proj_background"));
        if (null != paramMap.get("requirement"))
            model.setRequirement(paramMap.get("requirement"));
        if (null != paramMap.get("amount"))
            model.setAmount(paramMap.get("amount"));
        if (null != paramMap.get("delivery_time"))
            model.setDelivery_time(paramMap.get("delivery_time"));
        if (null != paramMap.get("ability"))
            model.setAbility(paramMap.get("ability"));
        if (!StringUtils.isEmpty(paramMap.get("proj_status")))
        {
            try
            {
                model.setProj_status(Status.valueOf(paramMap.get("proj_status")));
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        else
        {
            model.setProj_status(Status.未知);
        }
        if (null != paramMap.get("liaison"))
            model.setLiaison(paramMap.get("liaison"));
        if (null != paramMap.get("liaison_tel"))
            model.setLiaison_tel(paramMap.get("liaison_tel"));
        if (null != paramMap.get("liaison_email"))
            model.setLiaison_email(paramMap.get("liaison_email"));
        if (null != paramMap.get("huawei_liaison"))
            model.setHuawei_liaison(paramMap.get("huawei_liaison"));
        if (null != paramMap.get("huawei_liaison_dept"))
            model.setHuawei_liaison_dept(paramMap.get("huawei_liaison_dept"));
        if (null != paramMap.get("esdk_liaison"))
            model.setEsdk_liaison(paramMap.get("esdk_liaison"));
        
        if (!StringUtils.isEmpty(paramMap.get("start_time")))
        {
            try
            {
                Date startTime = formator.parse(paramMap.get("start_time"));
                model.setStart_time(startTime);
            }
            catch (ParseException e)
            {
            }
        }
        
        if (!StringUtils.isEmpty(paramMap.get("end_time")))
        {
            try
            {
                Date endTime = formator.parse(paramMap.get("end_time"));
                model.setEnd_time(endTime);
            }
            catch (ParseException e)
            {
            }
        }
        
        if (null != paramMap.get("remark"))
            model.setRemark(paramMap.get("remark"));
        
        int creatorId = StringUtils.getId(paramMap.get("creator_id"));
        if (creatorId > 0)
        {
            model.setCreator_id(creatorId);
        }
        
        if (null != paramMap.get("spp_status"))
            model.setSpp_status(paramMap.get("spp_status"));
        
        if (!StringUtils.isEmpty(paramMap.get("key_proj")))
        {
            if ("1".equals(paramMap.get("key_proj")))
            {
                model.setKey_proj(1);
            }
            else if (!"0".equals(paramMap.get("key_proj")))
            {
                return null;
            }
        }
        
        return model;
    }
    
    public static String convertProduct(Product product)
    {
        switch (product)
        {
            case EC30:
                return "EC3.0";
            case EC60:
                return "EC6.0";
            case FusionInsightHD:
                return "FusionInsight HD";
            case AgentLite_OceanConnect:
                return "AgentLite&OceanConnect";
            case NBIoT_OceanConnect:
                return "NBIoT&OceanConnect";
            default:
                return product.toString();
        }
    }
}

