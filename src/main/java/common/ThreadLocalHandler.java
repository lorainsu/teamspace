package common;

public class ThreadLocalHandler
{
    private static ThreadLocal<MessageContext> localVariable = new ThreadLocal<MessageContext>()
    {
        protected MessageContext initialValue()
        {
            return new MessageContext();
        }
    };
    
    public static Object get(String key)
    {
        return localVariable.get().getEntity(key);
    }
    
    public static void set(String key, Object entity)
    {
        localVariable.get().setEntity(key, entity);
    }
    
}

