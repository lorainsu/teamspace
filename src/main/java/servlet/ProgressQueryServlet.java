package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.Progresses;
import bean.convertor.ProgressBeanConvertor;
import common.constants.Constants;
import common.utils.StringUtils;
import dao.DaoServiceFactory;
import dao.base.BaseDaoService;
import dao.model.ProgressModel;

@WebServlet("/progress/query")
public class ProgressQueryServlet extends HttpServlet
{
    
    private static final long serialVersionUID = 5372161798334439631L;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProgressQueryServlet doPost");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        
        int pageNo = StringUtils.getId(req.getParameter("pageNo"));
        int pageSize = StringUtils.getId(req.getParameter("pageSize"));
        
        if (pageNo < 1 || pageSize < 1)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal pageNo or pageSize:positive integer wanted");
            resp.getWriter().flush();
            return;
        }
        
        ProgressModel model = ProgressBeanConvertor.getModel(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        
        Progresses progresses = new Progresses();
        List<ProgressModel> models = new ArrayList<ProgressModel>();
        
        BaseDaoService<ProgressModel> progressDao = DaoServiceFactory.getService(ProgressModel.class);
        if (null == progressDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        progresses.setTotal(progressDao.count(model));
        
        if (progresses.getTotal() > -1)
        {
            models = progressDao.query(model, pageNo, pageSize);
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Get count from database failed");
            resp.getWriter().flush();
            return;
        }
        
        for (ProgressModel progress : models)
        {
            ProgressModel row = new ProgressModel();
            row.setId(progress.getId());
            row.setUpdate_time(progress.getUpdate_time());
            row.setContent(progress.getContent());
            row.setProj_id(progress.getProj_id());
            progresses.getRows().add(row);
        }
        
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat(Constants.DATE_FORMAT_BEAN).create();
        resp.getWriter().print(gson.toJson(progresses));
        resp.getWriter().flush();
        
        System.out.println("ProgressQueryServlet finish doPost");
    }
}

