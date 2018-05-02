package common.utils;

public class StringUtils
{
    public static boolean isEmpty(String str)
    {
        if (null == str || 0 == str.trim().length())
        {
            return true;
        }
        
        return false;
    }
    
    public static int getId(String idStr)
    {
        int id = -1;
        
        if (StringUtils.isEmpty(idStr))
        {
            return -1;
        }
        try
        {
            id = Integer.parseInt(idStr);
        }
        catch (NumberFormatException e)
        {
        }
        
        return id;
    }
}

