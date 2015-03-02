package nstuff.generator.entity;

import nstuff.generator.entity.biom.Biome;
import nstuff.generator.geography.PointHeightType;
import nstuff.generator.geography.PointHumidityType;
import nstuff.generator.geography.PointLandType;
import nstuff.generator.geography.PointTemperatureType;

import static nstuff.generator.Generator.GENERATE_PHASE;

/**
 * Created by vania_000 on 21.02.2015.
 */


public class MapPoint {
    protected int x;

    protected int y;

    private int height=-1;

    private float temperature;

    private GENERATE_PHASE stage =GENERATE_PHASE.START;

    private Map map;

    private PointLandType landType=PointLandType.OCEAN;

    private PointHeightType heightType= PointHeightType.OCEAN;

    private PointTemperatureType temperatureType = PointTemperatureType.SUB_TROPICAL;

    private PointHumidityType humidityType= PointHumidityType.WATER;

    private River river;

    private Lake lake;

    private Biome biome;

    public MapPoint(int x, int y,Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }

    public int getX() {
        return x;
    }

    public PointLandType getLandType() {
        return landType;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if(landType==PointLandType.OCEAN&&map.getSeaLevel()<height){
            if(height-map.getSeaLevel()<map.getIslandLevel()){
                height = map.getSeaLevel()-1;
            }

        }
        if(landType!=PointLandType.OCEAN&&map.getSeaLevel()>height){
            height = map.getSeaLevel();
        }
        if(height<0){
            this.height =0;
        }else {

            this.height = height;
        }
    }
    public void setStartHeight(int height) {
        if(stage !=GENERATE_PHASE.HEIGHT){
            stage =GENERATE_PHASE.HEIGHT;
            this.height = height;
        }
    }


    public void makeLand() {
        stage =GENERATE_PHASE.LAND;
        landType = PointLandType.LAND;

    }
    public  void makeRiver(River river){
        landType = PointLandType.RIVER;
        this.river = river;
    }

    public PointHeightType getHeightType() {
        return heightType;
    }

    public void setHeightType(PointHeightType heightType) {

        this.heightType = heightType;
        if(height>=map.getSeaLevel()&&stage !=GENERATE_PHASE.RIVER){
            landType = PointLandType.LAND;
        }
    }

    public PointTemperatureType getTemperatureType() {
        return temperatureType;
    }

    public void setTemperatureType(PointTemperatureType temperatureType) {
        this.temperatureType = temperatureType;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public PointHumidityType getHumidityType() {
        return humidityType;
    }

    public void setHumidityType(PointHumidityType humidityType) {
        this.humidityType = humidityType;
    }

    @Override
    public String toString() {

        return "x:"+x+" y:"+y+" height:"+height;
    }

    public boolean isWater() {
        return landType != PointLandType.LAND;
    }
    public boolean isSaltWater() {
        return landType == PointLandType.OCEAN;
    }
    public void makeLake(Lake lake) {
        landType = PointLandType.LAKE;
        this.lake = lake;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public Biome getBiome() {
        return biome;
    }
}
