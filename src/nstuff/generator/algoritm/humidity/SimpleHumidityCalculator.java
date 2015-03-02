package nstuff.generator.algoritm.humidity;

import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.geography.PointHumidityType;
import nstuff.generator.geography.PointLandType;
import nstuff.generator.geography.PointTemperatureType;
import nstuff.generator.logic.LogicFinder;
import nstuff.generator.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 27.02.15.
 */
public class SimpleHumidityCalculator implements HumidityCalculator  {
    static Logger logger = LogManager.getLogger(SimpleHumidityCalculator.class);

    @Inject
    SettingManager settings;

    List<Integer> humidDictionary =new ArrayList<Integer>();

    @Inject
    @Named("WaterFinder")
    private LogicFinder waterFinder;

    @Override
    public void init() {
        int i = 0;
        int  temp =settings.getHumiditySetting("waterdist" + i,-1 , Integer.class);
        while(temp!=-1){
            humidDictionary.add(temp);
            i++;
            temp =settings.getHumiditySetting("waterdist" + i, -1, Integer.class);
        }
    }

    @Override
    public void calculate(Map map) {
        logger.debug("Generating Step: Calculating Humidity");
        int maxDistance = humidDictionary.get(humidDictionary.size()-1);
        for(int i=0;i<map.getHeight();i++){
            for(int j= 0;j<map.getWidth();j++){
                MapPoint point  =map.getPoint(i,j);
                if(point.getLandType()== PointLandType.LAND){
                     MapPoint water =map.getClosestInRange(point.getX(),point.getY(),maxDistance,waterFinder);
                     if(water!=null){
                         point.setHumidityType(getHumidityType(map.distance(point,water)));
                     }else{
                         point.setHumidityType(PointHumidityType.DESERT);
                     }
                }else{
                    point.setHumidityType(PointHumidityType.WATER);
                }
            }
        }

    }
    public PointHumidityType getHumidityType(int distance){
        for(int i=0;i<humidDictionary.size();i++){
            if(humidDictionary.get(i)>=distance){
                return PointHumidityType.values()[i];
            }
        }
        return  PointHumidityType.values()[PointHumidityType.values().length-1];
    }
}
