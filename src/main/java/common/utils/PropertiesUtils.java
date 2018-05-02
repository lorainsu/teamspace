package common.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils
{
    private static Properties properties = null;
    
    static
    {
        properties = loadProperty();
    }
    
    private static Properties loadProperty()
    {
        Properties p = new Properties();
        loadProp("config.properties", p);
        return p;
    }
    
    private static void loadProp(String conf, Properties p)
    {
        InputStream is = null;
        try
        {
            is = getInputStream(conf);
            if (null != is)
            {
                p.load(is);
            }
        }
        catch (IOException e)
        {
            System.out.println("Exception happened in loadProp() :" + e.getMessage());
        }
        finally
        {
            if (is != null)
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    System.out.println("Exception happened in loadProperty() :" + e.getMessage());
                }
        }
    }
    
    public static String getValue(String key)
    {
        String value = properties.getProperty(key);
        return value != null ? value : "";
    }
    
    private static InputStream getInputStream(String path)
        throws IOException
    {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null)
            throw new FileNotFoundException(path + " cannot be opened because it does not exist");
        else
            return is;
    }
    
}
