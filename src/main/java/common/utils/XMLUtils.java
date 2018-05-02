package common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtils
{
    public static String getNodeValue(String xml, String nodeName)
        throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        
        String[] tags = nodeName.split("/");
        String lastTag = tags[tags.length - 1];
        
        NodeList nodeList = doc.getElementsByTagName(lastTag);
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            String value = node.getFirstChild().getNodeValue();
            
            String baseURI = lastTag;
            while (null != node.getParentNode().getNodeName()
                && !"#document".equals(node.getParentNode().getNodeName()))
            {
                baseURI = node.getParentNode().getNodeName() + "/" + baseURI;
                node = node.getParentNode();
            }
            
            if (baseURI.equals(nodeName))
            {
                return value;
            }
        }
        
        return "";
    }
    
    public static String getNodeAttr(String xml, String nodeName, String attrName)
        throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        
        String[] tags = nodeName.split("/");
        String lastTag = tags[tags.length - 1];
        
        NodeList nodeList = doc.getElementsByTagName(lastTag);
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            NamedNodeMap attrMap = node.getAttributes();
            String attrValue = attrMap.getNamedItem(attrName).getNodeValue();
            
            String baseURI = lastTag;
            while (null != node.getParentNode().getNodeName()
                && !"#document".equals(node.getParentNode().getNodeName()))
            {
                baseURI = node.getParentNode().getNodeName() + "/" + baseURI;
                node = node.getParentNode();
            }
            
            if (baseURI.equals(nodeName))
            {
                return attrValue;
            }
        }
        
        return "";
    }
}

