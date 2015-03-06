package nstuff.economy.entity.town;

import nstuff.economy.entity.building.BuildingBlueprint;
import nstuff.economy.entity.building.BuildingCategory;
import nstuff.economy.entity.resources.ResourceWithAmount;
import nstuff.economy.entity.resources.ResourceWithAmountBlueprint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */
@XmlRootElement(name = "TownHallBlueprint")
@XmlType(propOrder = {"name","lvl","allowedBuildingsNames","workersMax","nextTownHallsNames","neededListBlueprint"})
public class TownHallBlueprint {
    @XmlElement
    private String name;

    @XmlElement
    private int lvl;

    @XmlElement
    private List<String> allowedBuildingsNames;

    private List<BuildingBlueprint> allowedBuildings;

    @XmlElement
    private int workersMax;

    @XmlElement
    private List<String> nextTownHallsNames = new ArrayList<String>();

    private List<TownHallBlueprint> nextTownHalls;

    @XmlElement
    private List<ResourceWithAmountBlueprint> neededListBlueprint;

    private List<ResourceWithAmount>  neededList;


    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    @XmlTransient
    public void setAllowedBuildings(List<BuildingBlueprint> allowedBuildings) {
        this.allowedBuildings = allowedBuildings;
        this.allowedBuildingsNames = null;
    }

    public List<String> getAllowedBuildingsNames() {
        return allowedBuildingsNames;
    }

    public List<BuildingBlueprint> getAllowedBuildings() {
        return allowedBuildings;
    }

    public List<ResourceWithAmountBlueprint> getNeededListBlueprint() {
        return neededListBlueprint;
    }
    @XmlTransient
    public void setNeededList(List<ResourceWithAmount> neededList) {
        this.neededList = neededList;
        this.neededListBlueprint = null;
    }

    public List<String> getNextTownHallsNames() {
        return nextTownHallsNames;
    }
    @XmlTransient
    public void setNextTownHalls(List<TownHallBlueprint> nextTownHalls) {
        this.nextTownHalls = nextTownHalls;
        this.nextTownHallsNames = null;
    }
}
