package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.constants.Constants;
import common.session.SessionManager;
import dao.model.UserModel;

@WebServlet("/user")
public class UserServlet extends HttpServlet
{
    
    private static final long serialVersionUID = -384554213505312705L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("UserServlet doGet");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setHeader("Cache-Control", "no-cache, must-revalidate");
        
        String sessionId = SessionManager.getInstance().getCurrentSessionId();
        UserModel user = SessionManager.getInstance().getSession(sessionId).getUser();
        
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat(Constants.DATE_FORMAT_DAO).create();
        resp.getWriter().print(gson.toJson(user));
        resp.getWriter().flush();
        
        System.out.println("UserServlet finish doGet");
    }
    
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("UserServlet doDelete");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String sessionId = SessionManager.getInstance().getCurrentSessionId();
        UserModel user = SessionManager.getInstance().getSession(sessionId).getUser();
        
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat(Constants.DATE_FORMAT_DAO).create();
        resp.getWriter().print(gson.toJson(user));
        resp.getWriter().flush();
        
        System.out.println("UserServlet finish doGet");
    }
}

