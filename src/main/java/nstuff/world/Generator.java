package nstuff.world;

import nstuff.world.algoritm.grower.Grower;
import nstuff.world.algoritm.heightmap.HeightMapGenerator;
import nstuff.world.algoritm.heightmap.HeightNormalizer;
import nstuff.world.algoritm.humidity.HumidityCalculator;
import nstuff.world.algoritm.rivers.RiverGenerator;
import nstuff.world.algoritm.temperature.TemperatureCalculator;
import nstuff.world.entity.HexMap;
import nstuff.world.entity.Map;
import nstuff.world.logic.LogicChanger;
import nstuff.world.logic.generators.ProtoBiomeGenerator;
import nstuff.world.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Random;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class Generator {
    public enum GENERATE_PHASE{
        START,
        LAND,
        HEIGHT,
        RIVER;
    }

    static Logger logger = LogManager.getLogger(Generator.class);

    @Inject
    private HeightMapGenerator heightMapGenerator;

    @Inject
    private HeightNormalizer heightNormalizer;

    @Inject
    private RiverGenerator riverGenerator;

    @Inject
    private SettingManager settingManager;

    @Inject
    private ProtoBiomeGenerator protoBiomeGenerator;

    @Inject
    @Named("IslandGrower")
    private Grower islandGrower;

    @Inject
    @Named("IslandMaker")
    private LogicChanger islandMaker;

    @Inject
    private TemperatureCalculator temperatureCalculator;

    @Inject
    private HumidityCalculator humidityCalculator;

    @Inject
    Random rand;

    int seed;

    boolean repeat;

    public Map generateHexMap(int x, int y) throws GeneratorException {
        if(seed!=0&&!repeat){
            rand.setSeed(seed);
        }
        Map map = new HexMap(x,y,
                settingManager.getHeightMapSetting("landlvl",0,Integer.class),
                settingManager.getHeightMapSetting("islandLvl",0,Integer.class));


        int count = settingManager.getIslandSetting("count",5,Integer.class);
        int minSize = settingManager.getIslandSetting("minsize",5,Integer.class);
        int maxSize = settingManager.getIslandSetting("maxsize",5,Integer.class);
        logger.debug("Generating Step: Generating Island and Continent");

        for(int i=0;i<count;i++){

            int size= rand.nextInt(maxSize-minSize)+minSize;
            int islandX =  rand.nextInt(x);

            int islandY  =  rand.nextInt(y);
            logger.debug("Generating Land "+i +" size "+size);

            islandGrower.setUp(size,islandX,islandY);
            islandGrower.grow(map,islandMaker);
        }

        heightMapGenerator.generate(map);

        heightNormalizer.normalize(map);

        riverGenerator.generate(map);

        temperatureCalculator.calculateForMap(map);

        humidityCalculator.calculate(map);

        protoBiomeGenerator.generate(map);

        return  map;
    }

    public void init() throws GeneratorException {
        settingManager.init();
        seed =settingManager.getSection("seed", 0, Integer.class, "seed");
        rand.setSeed(seed);
        repeat =settingManager.getSection("seed", false, Boolean.class, "repeat");
        islandMaker.init();
        heightNormalizer.init();
        riverGenerator.init();
        temperatureCalculator.init();
        humidityCalculator.init();
        protoBiomeGenerator.init();
    }
}
