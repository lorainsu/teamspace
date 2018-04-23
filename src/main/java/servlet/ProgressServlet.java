package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.convertor.ProgressBeanConvertor;
import common.constants.Constants;
import common.session.SessionManager;
import common.utils.StringUtils;
import dao.DaoServiceFactory;
import dao.base.BaseDaoService;
import dao.model.ProgressModel;
import dao.model.ProjectModel;

@WebServlet("/progress")
public class ProgressServlet extends HttpServlet
{
    private static final long serialVersionUID = 2044446075227513697L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProgressServlet doGet");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        int id = StringUtils.getId(req.getParameter("id"));
        if (id < 1)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal progress id:positive integer wanted");
            resp.getWriter().flush();
            return;
        }
        
        BaseDaoService<ProgressModel> progressDao = DaoServiceFactory.getService(ProgressModel.class);
        if (null == progressDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        ProgressModel model = progressDao.query(id);
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat(Constants.DATE_FORMAT_BEAN).create();
        resp.getWriter().print(gson.toJson(model));
        resp.getWriter().flush();
        
        System.out.println("ProgressServlet finish doGet");
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProgressServlet doPost");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        ProgressModel model = ProgressBeanConvertor.getModel(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        if (null == model.getContent() || 1 > model.getProj_id())
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Param content and proj_id wanted.");
            resp.getWriter().flush();
            return;
        }
        
        if (null == model.getUpdate_time())
        {
            model.setUpdate_time(new Date());
        }
        
        String sessionId = SessionManager.getInstance().getCurrentSessionId();
        int userId = SessionManager.getInstance().getSession(sessionId).getUser().getId();
        model.setCreator_id(userId);
        
        BaseDaoService<ProjectModel> projectDao = DaoServiceFactory.getService(ProjectModel.class);
        if (null == projectDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        if (null == projectDao.query(model.getProj_id()))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Param proj_id not exist.");
            resp.getWriter().flush();
            return;
        }
        
        boolean result = DaoServiceFactory.getService(ProgressModel.class).insert(model);
        if (result)
        {
            resp.getWriter().write("Operate Success");
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Operate failed when insert data.");
        }
        resp.getWriter().flush();
        System.out.println("ProgressServlet finish doPost");
    }
    
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProgressServlet doPut");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        ProgressModel model = ProgressBeanConvertor.getModel4Put(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        
        BaseDaoService<ProjectModel> projectDao = DaoServiceFactory.getService(ProjectModel.class);
        if (null == projectDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        if (0 < model.getProj_id() && null == projectDao.query(model.getProj_id()))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Param proj_id not exist.");
            resp.getWriter().flush();
            return;
        }
        
        BaseDaoService<ProgressModel> progressDao = DaoServiceFactory.getService(ProgressModel.class);
        if (null == progressDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        boolean result = progressDao.update(model);
        if (result)
        {
            resp.getWriter().write("Operate Success");
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Operate failed when update data.");
        }
        resp.getWriter().flush();
        System.out.println("ProgressServlet finish doPut");
    }
    
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProgressServlet doDelete");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String ids = req.getParameter("ids");
        if (StringUtils.isEmpty(ids))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Project ids wanted");
            resp.getWriter().flush();
            return;
        }
        
        String[] idStrs = ids.split(",");
        List<Integer> idList = new ArrayList<Integer>();
        for (String idStr : idStrs)
        {
            int id = StringUtils.getId(idStr);
            if (id < 1)
            {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Illegal progress id:positive integer wanted");
                resp.getWriter().flush();
                return;
            }
            idList.add(id);
        }
        
        BaseDaoService<ProgressModel> progressDao = DaoServiceFactory.getService(ProgressModel.class);
        if (null == progressDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        boolean result = false;
        for (Integer id : idList)
        {
            if (progressDao.delete(id))
            {
                result = true;
            }
        }
        
        if (result)
        {
            resp.getWriter().write("Operate Success");
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Operate failed when delete data.");
        }
        
        resp.getWriter().flush();
        System.out.println("ProgressServlet finish doDelete");
    }
}

