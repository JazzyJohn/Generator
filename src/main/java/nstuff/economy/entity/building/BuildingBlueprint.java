package nstuff.economy.entity.building;

import nstuff.economy.entity.resources.ResourceWithAmount;
import nstuff.economy.entity.resources.ResourceWithAmountBlueprint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by vania_000 on 05.03.2015.
 */
@XmlRootElement(name = "BuildingBlueprint")
@XmlType(propOrder = {"buildingCategory","workerAmount","neededListBlueprint","inventorySize","productivity","state","size","characteristicList"})
public class BuildingBlueprint {
    public BuildingBlueprint() {
    }

    @XmlElement
    private BuildingCategory buildingCategory;

    @XmlElement
    private int workerAmount;

    @XmlElement
    private List<ResourceWithAmountBlueprint> neededListBlueprint;

    private List<ResourceWithAmount>  neededList;

    @XmlElement
    private int inventorySize;

    @XmlElement
    private int productivity;

    @XmlElement
    private int state;

    @XmlElement
    private int size;

    @XmlElement
    private List<CharacteristicBlueprint> characteristicList;


    public BuildingCategory getBuildingCategory() {
        return buildingCategory;
    }

    public int getWorkerAmount() {
        return workerAmount;
    }

    public List<ResourceWithAmountBlueprint> getNeededListBlueprint() {
        return neededListBlueprint;
    }

    public List<ResourceWithAmount> getNeededList() {
        return neededList;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public int getProductivity() {
        return productivity;
    }

    public int getState() {
        return state;
    }

    public int getSize() {
        return size;
    }

    public List<CharacteristicBlueprint> getCharacteristicList() {
        return characteristicList;
    }

    public void setNeededList(List<ResourceWithAmount> neededList) {
        this.neededList = neededList;
        this.neededListBlueprint = null;
    }
}
