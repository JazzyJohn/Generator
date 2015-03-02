package nstuff.generator.logic.generators;

import nstuff.generator.algoritm.grower.Grower;
import nstuff.generator.entity.Map;
import nstuff.generator.logic.generators.entity.ProtoBiomeBlueprint;
import nstuff.generator.settings.SettingException;
import nstuff.generator.settings.SettingManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public class SimpleGrowerBiome implements ProtoBiomeGenerator {

    private static final String FILE_NAME = "biomes.xml";

    @Inject
    SettingManager settings;

    @Inject
    @Named("BiomeGrower")
    private Grower islandGrower;

    List<ProtoBiomeBlueprint> allBiomes = new ArrayList<ProtoBiomeBlueprint>();

    @Override
    public void init() throws SettingException {
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
            Document doc = dBuilder.parse(fXmlFile);
            NodeList nodeList =doc.getElementsByTagName("ProtoBiomeBlueprint");
            for(int i =0;i<nodeList.getLength();i++){
                JAXBContext jc = JAXBContext.newInstance(ProtoBiomeBlueprint.class);
                Unmarshaller u = jc.createUnmarshaller();
                Object obj = u.unmarshal(nodeList.item(i));
                ProtoBiomeBlueprint blueprint = (ProtoBiomeBlueprint)obj;
                allBiomes.add(blueprint);
            }

        } catch (JAXBException e) {
            throw new SettingException(e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void generate(Map map) {

    }
}
