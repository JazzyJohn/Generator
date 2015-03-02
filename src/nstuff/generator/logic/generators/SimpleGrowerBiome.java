package nstuff.generator.logic.generators;

import nstuff.generator.algoritm.grower.Grower;
import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.entity.biom.Biome;
import nstuff.generator.logic.LogicChanger;
import nstuff.generator.logic.generators.entity.ProtoBiomeBlueprint;
import nstuff.generator.settings.SettingException;
import nstuff.generator.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public class SimpleGrowerBiome implements ProtoBiomeGenerator {

    static Logger logger = LogManager.getLogger(SimpleGrowerBiome.class);

    private static final String FILE_NAME = "biomes.xml";

    @Inject
    SettingManager settings;

    @Inject
    Random rand;


    @Inject
    @Named("BiomeGrower")
    private Grower biomeGrower;

    List<ProtoBiomeBlueprint> allBiomes = new ArrayList<ProtoBiomeBlueprint>();

    int maxBiomeSize;

    @Override
    public void init() throws SettingException {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(FILE_NAME);
            if(url==null){
                throw new SettingException("No biome file");
            }
            String path = url.getFile();
            if(path==null){
                throw new SettingException("No biome file");
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
            maxBiomeSize = settings.getBiomesSetting("maxBiomeSize",8,Integer.class);

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

    private Map map;

    private  List<BiomeBuildGuide> definedGuides;
    @Override
    public void generate(Map map) {
        this.map = map;
        logger.debug("Generating Step: Generating Proto Biomes");
        preGenerateAction();
        generateBiomeWithoutChoice();
        generateRestOfBiomes();

    }


    private BiomeBuildGuide[][] guides;

    private long biomezedPoints=0;

    private void preGenerateAction() {
        logger.debug("Generating SubStep: Proto Biome Pregeneration Action");
        guides= new BiomeBuildGuide[map.getWidth()][map.getHeight()];
        definedGuides = new ArrayList<BiomeBuildGuide>();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                BiomeBuildGuide guide = new BiomeBuildGuide(map.getPoint(i, j));
                guides[i][j] = guide;
                guide.listAllowBiomes = allowedBiomes(guide.center);
                if (guide.listAllowBiomes.size()==1){
                    definedGuides.add(guide);
                }

            }
        }
    }
    private void resolveGuide(BiomeBuildGuide guide,int index){
        //TODO: DELTE INDEX
        if(guide.center.getBiome()==null&&index<guide.listAllowBiomes.size()) {
            final ProtoBiomeBlueprint protoBiomeBlueprint = guide.listAllowBiomes.get(index);
            final Biome biome = protoBiomeBlueprint.generateBiome();

            biomeGrower.setUp(maxBiomeSize, guide.center);
            biomeGrower.grow(map, new LogicChanger() {
                @Override
                public void init() {

                }

                @Override
                public boolean changePoint(MapPoint point) {
                    if (protoBiomeBlueprint.canPlaceMe(point) && point.getBiome() == null) {
                        point.setBiome(biome);
                        biome.add(point);
                        biomezedPoints++;

                        return true;
                    } else
                        return false;

                }
            });
        }
        guides[guide.center.getX()][guide.center.getY()]= null;

    }

    private void generateBiomeWithoutChoice(){
        logger.debug("Generating SubStep: Proto Biome Generating Without Choice");

        for(BiomeBuildGuide guide : definedGuides){
            resolveGuide(guide,0);
        }

    }
    private void generateRestOfBiomes() {
        logger.debug("Generating SubStep: Proto Biome Generating Rest Points");
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if(guides[i][j]!=null){
                    BiomeBuildGuide guide = guides[i][j];
                    int index =rand.nextInt(guides.length);
                    resolveGuide(guide,index);
                }
            }
        }
    }


    private List<ProtoBiomeBlueprint> allowedBiomes(MapPoint center) {
        List<ProtoBiomeBlueprint> blueprints = new LinkedList<ProtoBiomeBlueprint>();
        for (ProtoBiomeBlueprint protoBiomeBlueprint : allBiomes){
            if(protoBiomeBlueprint.canPlaceMe(center)){
                blueprints.add(protoBiomeBlueprint);
            }
        }
        return blueprints;
    }


    class BiomeBuildGuide{

        List<ProtoBiomeBlueprint> listAllowBiomes;

        MapPoint center;

        BiomeBuildGuide(MapPoint center){
            this.center = center;
        }



    }
}
