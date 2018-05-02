package dao.base;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseDaoServiceImpl<T extends BaseModel> extends BaseDao<T> implements BaseDaoService<T>
{
    public BaseDaoServiceImpl()
        throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        super();
    }
    
    @Override
    public int count(T model)
    {
        return doCount(model);
    }
    
    @Override
    public T query(int id)
    {
        return doQuery(id);
    }
    
    @Override
    public List<T> query(T model)
    {
        return doQuery(model);
    }
    
    @Override
    public List<T> query(T model, int pageNo, int pageSize)
    {
        return doQuery(model, pageNo, pageSize);
    }
    
    @Override
    public boolean insert(T model)
    {
        return doInsert(model);
    }
    
    @Override
    public boolean delete(int id)
    {
        return doDelete(id);
    }
    
    @Override
    public boolean update(T model)
    {
        return doUpdate(model);
    }
}

