package nstuff.generator.algoritm.heightmap;

import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.geography.PointHeightType;
import nstuff.generator.settings.SettingManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vania_000 on 23.02.2015.
 */
public class HeightNormalizerImpl implements HeightNormalizer {

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

        for(int i = 0;i<map.getWidth();i++){
            for(int j =0;j<map.getHeight();j++){
                MapPoint mapPoint = map.getPoint(i,j);
                mapPoint.setHeightType(getHeightType(mapPoint.getHeight()));
            }
        }

    }
}
