package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.convertor.ProjectBeanConvertor;
import common.constants.Constants;
import common.session.SessionManager;
import common.utils.StringUtils;
import dao.DaoServiceFactory;
import dao.base.BaseDaoService;
import dao.model.ProjectModel;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet
{
    
    private static final long serialVersionUID = -8610308323439963783L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        System.out.println("ProjectServlet doGet");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        int id = StringUtils.getId(req.getParameter("id"));
        if (id < 1)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal project id:positive integer wanted");
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
        ProjectModel model = projectDao.query(id);
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat(Constants.DATE_FORMAT_BEAN).create();
        resp.getWriter().print(gson.toJson(model));
        resp.getWriter().flush();
        
        System.out.println("ProjectServlet finish doGet");
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProjectServlet doPost");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        ProjectModel model = ProjectBeanConvertor.getModel(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        if (model.getStart_time() != null && model.getEnd_time() != null
            && model.getStart_time().after(model.getEnd_time()))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param:start_time and end_time");
            resp.getWriter().flush();
            return;
        }
        if (null == model.getProj_name() || null == model.getIsv() || null == model.getEsdk_liaison())
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Param proj_name, isv and esdk_liaison wanted.");
            resp.getWriter().flush();
            return;
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
        
        boolean result = projectDao.insert(model);
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
        System.out.println("ProjectServlet finish doPost");
    }
    
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProjectServlet doPut");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        ProjectModel model = ProjectBeanConvertor.getModel4Put(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        
        if (model.getStart_time() != null && model.getEnd_time() != null
            && model.getStart_time().after(model.getEnd_time()))
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param:start_time and end_time");
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
        
        boolean result = projectDao.update(model);
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
        System.out.println("ProjectServlet finish doPut");
    }
    
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProjectServlet doDelete");
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
                resp.getWriter().write("Illegal project id:positive integer wanted");
                resp.getWriter().flush();
                return;
            }
            idList.add(id);
        }
        
        BaseDaoService<ProjectModel> projectDao = DaoServiceFactory.getService(ProjectModel.class);
        if (null == projectDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        boolean result = false;
        for (Integer id : idList)
        {
            if (projectDao.delete(id))
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
        System.out.println("ProjectServlet finish doDelete");
    }
}

