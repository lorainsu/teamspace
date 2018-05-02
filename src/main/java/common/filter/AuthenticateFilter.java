package common.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ThreadLocalHandler;
import common.constants.Constants;
import common.session.SessionManager;
import common.utils.SSOUtils;
import common.utils.StringUtils;
import dao.DaoServiceFactory;
import dao.base.BaseDaoService;
import dao.model.UserModel;

public class AuthenticateFilter implements Filter
{
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        String contextPath = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + contextPath;
        
        if (null != req.getSession(false))
        {
            if (SessionManager.getInstance().isSessionExist(req.getSession(false).getId()))
            {
                ThreadLocalHandler.set(Constants.THREAD_LOCAL_SESSION, req.getSession(false).getId());
                chain.doFilter(request, response);
                return;
            }
            req.getSession().invalidate();
            System.out.println("Session Invalid!");
        }
        if (!StringUtils.isEmpty(request.getParameter("ticket")))
        {
            UserModel user = SSOUtils.vaildateTicket(request.getParameter("ticket"), basePath);
            if (null != user)
            {
                BaseDaoService<UserModel> userDao = DaoServiceFactory.getService(UserModel.class);
                if (null == userDao)
                {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("Initialize connection to database failed");
                    resp.getWriter().flush();
                    return;
                }
                List<UserModel> userList = userDao.query(user);
                if (userList.size() > 0)
                {
                    user.setId(userList.get(0).getId());
                    user.setLast_login(new Date());
                    if (!userDao.update(user))
                    {
                        System.out.println("Update userInfo into database failed.");
                    }
                }
                else
                {
                    user.setLast_login(new Date());
                    if (!userDao.insert(user))
                    {
                        System.out.println("Insert userInfo into database failed.");
                        return;
                    }
                }
                
                //Refresh user info from db
                user.setLast_login(null);
                user = userDao.query(user).get(0);
                
                HttpSession session = req.getSession();
                ThreadLocalHandler.set(Constants.THREAD_LOCAL_SESSION, session.getId());
                SessionManager.getInstance().setSession(session.getId(), user);
                chain.doFilter(request, response);
                return;
            }
        }
        if (isAjax(req))
        {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Session and ticket are invalid!");
            resp.getWriter().flush();
        }
        else
        {
            resp.sendRedirect(SSOUtils.SSO_SERVER + "uniportal/login?service=" + basePath);
        }
    }
    
    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        System.out.println("Init AuthFilter");
    }
    
    public void destroy()
    {
        System.out.println("Destroy AuthFilter");
    }
    
    private boolean isAjax(HttpServletRequest req)
    {
        String ajaxHeader = req.getHeader("X-Requested-With");
        if (!StringUtils.isEmpty(ajaxHeader))
        {
            if ("XMLHttpRequest".equals(ajaxHeader))
            {
                return true;
            }
        }
        return false;
    }
}

