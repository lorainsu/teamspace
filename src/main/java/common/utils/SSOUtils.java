package common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dao.model.UserModel;

public class SSOUtils
{
    public static String SSO_SERVER = PropertiesUtils.getValue("sso_server").trim();
    
    public static UserModel vaildateTicket(String ticket, String basePath)
        throws IOException
    {
        UserModel user = null;
        
        URL validateURL = new URL(SSO_SERVER + "uniportal/serviceValidate?ticket=" + ticket + "&service=" + basePath);
        URLConnection conn = validateURL.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        
        String responseXML = "", line;
        while (null != (line = reader.readLine()))
            responseXML += line;
        
        if (!StringUtils.isEmpty(responseXML))
        {
            String errorCode = "";
            try
            {
                errorCode = XMLUtils.getNodeAttr(responseXML, "cas:serviceResponse/cas:authenticationFailure", "code");
            }
            catch (ParserConfigurationException | SAXException e)
            {
                e.printStackTrace();
            }
            
            if (StringUtils.isEmpty(errorCode))
            {
                String uniportalId = "";
                String email = "";
                String username = "";
                try
                {
                    uniportalId = XMLUtils.getNodeValue(responseXML,
                        "cas:serviceResponse/cas:authenticationSuccess/cas:attributes/uniportalId");
                    email = XMLUtils.getNodeValue(responseXML,
                        "cas:serviceResponse/cas:authenticationSuccess/cas:attributes/email");
                    username = XMLUtils.getNodeValue(responseXML,
                        "cas:serviceResponse/cas:authenticationSuccess/cas:attributes/username");
                }
                catch (ParserConfigurationException | SAXException e)
                {
                    e.printStackTrace();
                }
                
                if (!StringUtils.isEmpty(uniportalId))
                {
                    user = new UserModel();
                    user.setEmail(email);
                    user.setName(username);
                    user.setStaff_no(uniportalId);
                    return user;
                }
            }
            else
            {
                System.out.println("Validate SSO Ticket failed:" + errorCode);
            }
        }
        return user;
    }
}

