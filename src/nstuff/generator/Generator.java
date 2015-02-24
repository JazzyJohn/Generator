package nstuff.generator;

import nstuff.generator.algoritm.grower.Grower;
import nstuff.generator.algoritm.heightmap.HeightMapGenerator;
import nstuff.generator.algoritm.heightmap.HeightNormalizer;
import nstuff.generator.algoritm.rivers.RiverGenerator;
import nstuff.generator.entity.HexMap;
import nstuff.generator.entity.Map;
import nstuff.generator.logic.LogicChanger;
import nstuff.generator.settings.SettingManager;

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
    @Inject
    private HeightMapGenerator heightMapGenerator;

    @Inject
    private HeightNormalizer heightNormalizer;

    @Inject
    private RiverGenerator riverGenerator;

    @Inject
    private SettingManager settingManager;

    @Inject
    @Named("IslandGrower")
    private Grower islandGrower;

    @Inject
    @Named("IslandMaker")
    private LogicChanger islandMaker;

    @Inject
    Random rand;

    int seed;

    boolean repeat;

    public Map generateHexMap(int x, int y) throws GeneratorException {
        if(seed!=0&&repeat){
            rand.setSeed(seed);
        }
        Map map = new HexMap(x,y,
                settingManager.getHeightMapSetting("landlvl",0,Integer.class),
                settingManager.getHeightMapSetting("islandLvl",0,Integer.class));


        int count = settingManager.getIslandSetting("count",5,Integer.class);
        int minSize = settingManager.getIslandSetting("minsize",5,Integer.class);
        int maxSize = settingManager.getIslandSetting("maxsize",5,Integer.class);
        for(int i=0;i<count;i++){
            int size= rand.nextInt(maxSize-minSize)+minSize;
            int islandX =  rand.nextInt(x);

            int islandY  =  rand.nextInt(y);

            islandGrower.setUp(size,islandX,islandY);
            islandGrower.grow(map,islandMaker);
        }
        heightMapGenerator.generate(map);
        heightNormalizer.normalize(map);
        riverGenerator.generate(map);
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
    }
}
