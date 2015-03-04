package nstuff.world.algoritm.heightmap;

import nstuff.world.entity.Map;
import nstuff.world.entity.MapPoint;
import nstuff.world.geography.PointHeightType;
import nstuff.world.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vania_000 on 23.02.2015.
 */
public class HeightNormalizerImpl implements HeightNormalizer {
    static Logger logger = LogManager.getLogger(HeightNormalizerImpl.class);


    @Inject
    SettingManager settings;

    List<Integer> heightDictionary =new ArrayList<Integer>();
    @Override
    public void init() {
         int i = 0;
        int height =settings.getHeightNormalSetting("height"+i,-1,Integer.class);
        while(height!=-1){
            heightDictionary.add(height);
            i++;
            height =settings.getHeightNormalSetting("height"+i,-1,Integer.class);
        }

    }

    public PointHeightType getHeightType(int height){
        for(int i=0;i<heightDictionary.size();i++){
            if(heightDictionary.get(i)>height){
                return PointHeightType.values()[i];
            }
        }
        return  PointHeightType.values()[PointHeightType.values().length-1];
    }

    @Override
    public void normalize(Map map) {
        logger.debug("Generating Step: Normalazing Height Map");

        for(int i = 0;i<map.getWidth();i++){
            for(int j =0;j<map.getHeight();j++){
                MapPoint mapPoint = map.getPoint(i,j);
                mapPoint.setHeightType(getHeightType(mapPoint.getHeight()));
            }
        }

    }
}
