package nstuff.generator.settings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class SettingManager {

    private static final String FILE_NAME = "conf.xml";

    private static final String HEIGHT_MAP_SECTION = "heightMap";

    private static final String HEIGHT_NORMAL_SECTION = "heightNormal";

    private static final String MAIN_SECTION = "main";

    private static final String ISLAND_SECTION = "island";

    private static final String RIVER_SECTION = "river";

    Document doc;

    public void init() throws SettingException{
        try {
        URL url = Thread.currentThread().getContextClassLoader().getResource(FILE_NAME);
        if(url==null){
            throw new SettingException("No conf file");
        }
        String path = url.getFile();
        if(path==null){
             throw new SettingException("No conf file");
        }
        File fXmlFile = new File(url.getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);


        } catch (ParserConfigurationException e) {
            throw new SettingException(e);
        } catch (SAXException e) {
            throw new SettingException(e);
        } catch (IOException e) {
            throw new SettingException(e);
        }

    }

    public  <T> T getHeightMapSetting(String param,T defaultValue,Class className) {
        if (doc == null) {
            return defaultValue;
        }
        return getSection(param,defaultValue,className,HEIGHT_MAP_SECTION);

    }
    public  <T> T getHeightNormalSetting(String param,T defaultValue,Class className) {
        if (doc == null) {
            return defaultValue;
        }
        return getSection(param,defaultValue,className,HEIGHT_NORMAL_SECTION);

    }
    public  <T> T getMainSetting(String param,T defaultValue,Class className) {
        if (doc == null) {
            return defaultValue;
        }
        return getSection(param,defaultValue,className,MAIN_SECTION);

    }
    public  <T> T getIslandSetting(String param,T defaultValue,Class className) {
        if (doc == null) {
            return defaultValue;
        }
        return getSection(param,defaultValue,className,ISLAND_SECTION);

    }
    public  <T> T getRiverSetting(String param,T defaultValue,Class className) {
        if (doc == null) {
            return defaultValue;
        }
        return getSection(param,defaultValue,className,RIVER_SECTION);

    }
    private <T> T getSection(String param,T defaultValue,Class className,String sectionName) {
        NodeList list = doc.getElementsByTagName(sectionName);
        if(list.getLength()==0){
            return  defaultValue;
        }
        Element section = (Element)list.item(0);

        list  =section.getElementsByTagName(param);
        if(list.getLength()==0){
            return  defaultValue;
        }
        Node node =list.item(0);
        if(className.equals(Integer.class)){
            return (T)className.cast(Integer.parseInt(node.getTextContent()));
        }
        if(className.equals(String.class)){
            return (T)className.cast(node.getTextContent());
        }
        if(className.equals(Float.class)){
            return (T)className.cast(Float.parseFloat(node.getTextContent()));
        }
        if(className.equals(Boolean.class)){
            return (T)className.cast(Boolean.parseBoolean(node.getTextContent()));
        }
        return null;
    }

}
