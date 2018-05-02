package common;

import java.util.HashMap;
import java.util.Map;

public class MessageContext
{
    private Map<String, Object> entites = new HashMap<String, Object>();
    
    public Object getEntity(String key)
    {
        return entites.get(key);
    }
    
    public void setEntity(String key, Object entity)
    {
        this.entites.put(key, entity);
    }
    
}

