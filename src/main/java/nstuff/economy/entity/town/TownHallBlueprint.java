package nstuff.economy.entity.town;

import nstuff.economy.entity.building.BuildingBlueprint;
import nstuff.economy.entity.building.BuildingCategory;
import nstuff.economy.entity.resources.ResourceWithAmountBlueprint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */
@XmlRootElement(name = "BuildingBlueprint")
@XmlType(propOrder = {"name","lvl","buildingsBlueprints"})
public class TownHallBlueprint {
    @XmlElement
    private String name;

    @XmlElement
    private int lvl;

    @XmlElement
    private List<BuildingBlueprint> allowedBuildings;

    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    public List<BuildingBlueprint> getAllowedBuildings() {
        return allowedBuildings;
    }
}
