package dao;

import dao.base.BaseDaoConvertor;
import dao.convertor.ProgressDaoConvertor;
import dao.convertor.ProjectDaoConvertor;
import dao.convertor.UserDaoConvertor;
import dao.model.ProgressModel;
import dao.model.ProjectModel;
import dao.model.UserModel;

public class DaoConvertorFactory
{
    @SuppressWarnings("unchecked")
    public static <T> BaseDaoConvertor<T> getConvertor(Class<T> modelName)
    {
        if (ProjectModel.class.getName().equals(modelName.getName()))
        {
            return (BaseDaoConvertor<T>)(new ProjectDaoConvertor());
        }
        if (ProgressModel.class.getName().equals(modelName.getName()))
        {
            return (BaseDaoConvertor<T>)(new ProgressDaoConvertor());
        }
        if (UserModel.class.getName().equals(modelName.getName()))
        {
            return (BaseDaoConvertor<T>)(new UserDaoConvertor());
        }
        else
        {
            return null;
        }
    }
}

