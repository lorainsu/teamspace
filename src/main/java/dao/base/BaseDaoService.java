package dao.base;

import java.util.List;

public interface BaseDaoService<T extends BaseModel>
{
    /**
     * Count Model by specify info
     * @param model
     * @return count
     */
    public int count(T model);
    
    /**
     * Query Model Info by Id
     * @param id
     * @return ProjectModel
     */
    public T query(int id);
    
    /**
     * Query Model Info by specify info and page
     * @param model
     * @return
     */
    public List<T> query(T model);
    
    /**
     * Query Model Info by specify info and page
     * @param model
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<T> query(T model, int pageNo, int pageSize);
    
    /**
     * Insert Model
     * @param model
     * @return result
     */
    public boolean insert(T model);
    
    /**
     * Delete Model by Id
     * @param id
     * @return result
     */
    public boolean delete(int id);
    
    /**
     * Update Model
     * @param model
     * @return result
     */
    public boolean update(T model);
}

