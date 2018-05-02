package dao;

import dao.base.BaseDaoService;
import dao.base.BaseModel;
import dao.model.ProgressModel;
import dao.model.ProjectModel;
import dao.model.UserModel;
import dao.service.ProjectDao;
import dao.service.UserDao;
import dao.service.ProgressDao;

public class DaoServiceFactory
{
    @SuppressWarnings("unchecked")
    public static <T extends BaseModel> BaseDaoService<T> getService(Class<T> modelName)
    {
        if (ProjectModel.class.getName().equals(modelName.getName()))
        {
            return (BaseDaoService<T>)ProjectDao.getInstance();
        }
        if (ProgressModel.class.getName().equals(modelName.getName()))
        {
            return (BaseDaoService<T>)ProgressDao.getInstance();
        }
        if (UserModel.class.getName().equals(modelName.getName()))
        {
            return (BaseDaoService<T>)UserDao.getInstance();
        }
        else
        {
            return null;
        }
    }
}

