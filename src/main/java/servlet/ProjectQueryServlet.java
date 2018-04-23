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

import bean.Projects;
import bean.convertor.ProjectBeanConvertor;
import common.constants.Constants;
import common.utils.StringUtils;
import dao.DaoServiceFactory;
import dao.base.BaseDaoService;
import dao.model.ProjectModel;

@WebServlet("/project/query")
public class ProjectQueryServlet extends HttpServlet
{
    private static final long serialVersionUID = -734401406270189566L;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProjectQueryServlet doPost");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        int pageNo = StringUtils.getId(req.getParameter("pageNo"));
        int pageSize = StringUtils.getId(req.getParameter("pageSize"));
        if (pageNo < 1 || pageSize < 1)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal pageNo or pageSize:positive integer wanted");
            resp.getWriter().flush();
            return;
        }
        
        ProjectModel model = ProjectBeanConvertor.getModel(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        
        Projects projects = new Projects();
        List<ProjectModel> models = new ArrayList<ProjectModel>();
        
        BaseDaoService<ProjectModel> projectDao = DaoServiceFactory.getService(ProjectModel.class);
        if (null == projectDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        
        projects.setTotal(projectDao.count(model));
        
        if (projects.getTotal() > -1)
        {
            models = projectDao.query(model, pageNo, pageSize);
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Get count from database failed");
            resp.getWriter().flush();
            return;
        }
        
        for (ProjectModel project : models)
        {
            ProjectModel row = new ProjectModel();
            row.setId(project.getId());
            row.setIndustry(project.getIndustry());
            row.setEcosphere(project.getEcosphere());
            row.setProduct(project.getProduct());
            row.setProj_name(project.getProj_name());
            row.setIsv(project.getIsv());
            row.setEsdk_liaison(project.getEsdk_liaison());
            projects.getRows().add(row);
        }
        
        Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_BEAN).create();
        resp.getWriter().print(gson.toJson(projects));
        resp.getWriter().flush();
        
        System.out.println("ProjectQueryServlet finish doPost");
    }
}

