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
import common.utils.StringUtils;
import dao.model.ProgressModel;

public class ProgressBeanConvertor
{
    private static SimpleDateFormat formator = new SimpleDateFormat(Constants.DATE_FORMAT_BEAN);
    
    public static ProgressModel getModel(HttpServletRequest req)
    {
        ProgressModel model = new ProgressModel();
        
        String update_time = req.getParameter("update_time");
        String content = req.getParameter("content");
        String proj_id = req.getParameter("proj_id");
        String user_id = req.getParameter("user_id");
        
        if (!StringUtils.isEmpty(update_time))
        {
            try
            {
                Date updateTime = formator.parse(update_time);
                model.setUpdate_time(updateTime);
            }
            catch (ParseException e)
            {
            }
        }
        
        if (!StringUtils.isEmpty(content))
            model.setContent(content);
        
        int projId = StringUtils.getId(proj_id);
        if (projId > 0)
        {
            model.setProj_id(projId);
        }
        
        int userId = StringUtils.getId(user_id);
        if (userId > 0)
        {
            model.setCreator_id(userId);
        }
        
        return model;
    }
    
    public static ProgressModel getModel4Put(HttpServletRequest req)
        throws IOException
    {
        ProgressModel model = new ProgressModel();
        
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
        
        if (!StringUtils.isEmpty(paramMap.get("update_time")))
        {
            try
            {
                Date updateTime = formator.parse(paramMap.get("update_time"));
                model.setUpdate_time(updateTime);
            }
            catch (ParseException e)
            {
            }
        }
        
        if (null != paramMap.get("content"))
            model.setContent(paramMap.get("content"));
        
        int projId = StringUtils.getId(paramMap.get("proj_id"));
        if (projId > 0)
        {
            model.setProj_id(projId);
        }
        
        int userId = StringUtils.getId(paramMap.get("user_id"));
        if (userId > 0)
        {
            model.setCreator_id(userId);
        }
        
        return model;
    }
}

