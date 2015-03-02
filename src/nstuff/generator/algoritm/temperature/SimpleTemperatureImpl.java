package nstuff.generator.algoritm.temperature;

import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.geography.PointTemperatureType;
import nstuff.generator.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 25.02.15.
 */
public class SimpleTemperatureImpl implements TemperatureCalculator {

    static Logger logger = LogManager.getLogger(SimpleTemperatureImpl.class);


    @Inject
    SettingManager settings;

    List<Float> tempDictionary =new ArrayList<Float>();

    private float degreePerHeight;

    private float degreePerLatitude;

    private int seaLvl;

    private int equator;

    private float startTemperature;

    @Override
    public void init() {
        int i = 0;
        float temp =settings.getTemperatureSetting("temp" + i, -1.f, Float.class);
        while(temp!=-1.f){
            tempDictionary.add(temp);
            i++;
            temp =settings.getTemperatureSetting("temp" + i, -1.f, Float.class);
        }
        degreePerHeight =settings.getTemperatureSetting("degreePerHeight", -0.1f, Float.class);
        degreePerLatitude =settings.getTemperatureSetting("degreePerLatitude", -0.1f, Float.class);
        seaLvl = settings.getHeightMapSetting("landlvl",0,Integer.class);
        startTemperature=settings.getTemperatureSetting("startTemperature", -0.1f, Float.class);
    }
    @Override
    public void calculateForMap(Map map) {
        logger.debug("Generating Step: Calculating Temperature");
        equator =map.getHeight()/2;
        for(int i=0;i<map.getHeight();i++){
            for(int j= 0;j<map.getWidth();j++){
                MapPoint point  =map.getPoint(i,j);
                float temperature = startTemperature+Math.abs(equator-point.getY())*degreePerLatitude;

                if(!point.isSaltWater()){
                    temperature+=(point.getHeight()- seaLvl)*degreePerHeight;
                }
                point.setTemperature(temperature);
                point.setTemperatureType(getTempType(temperature));
            }
        }

    }

    public PointTemperatureType getTempType(float temp){
        for(int i=0;i<tempDictionary.size();i++){
            if(tempDictionary.get(i)>temp){
                return PointTemperatureType.values()[i];
            }
        }
        return  PointTemperatureType.values()[PointTemperatureType.values().length-1];
    }
}
