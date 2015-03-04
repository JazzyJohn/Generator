package nstuff.world.logic.generators.entity;

import nstuff.world.entity.MapPoint;
import nstuff.world.entity.biom.Biome;
import nstuff.world.geography.PointHeightType;
import nstuff.world.geography.PointHumidityType;
import nstuff.world.geography.PointTemperatureType;
import nstuff.world.geography.ProtoBiome;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
@XmlRootElement(name = "ProtoBiomeBlueprint")
@XmlType(propOrder = {"protoBiome","allowedHeightTypes","allowedTemperatureTypes","allowedHumidityTypes"})
public class ProtoBiomeBlueprint {
    @XmlElement
    private ProtoBiome protoBiome;

    @XmlElement
    private List<PointHeightType> allowedHeightTypes;
    @XmlElement
    private List<PointTemperatureType> allowedTemperatureTypes;
    @XmlElement
    private List<PointHumidityType> allowedHumidityTypes;

    public ProtoBiomeBlueprint(){

    }

    public ProtoBiomeBlueprint(ProtoBiome protoBiome){
        this.protoBiome = protoBiome;
    }

    public void setProtoBiome(ProtoBiome protoBiome) {
        this.protoBiome = protoBiome;
    }

    public boolean canPlaceMe(MapPoint mapPoint){
        return allowedHeightTypes.contains(mapPoint.getHeightType())&&
                allowedHumidityTypes.contains(mapPoint.getHumidityType())&&
                allowedTemperatureTypes.contains(mapPoint.getTemperatureType());
    }
    public Biome generateBiome(){
        return new Biome(protoBiome);
    }
}
