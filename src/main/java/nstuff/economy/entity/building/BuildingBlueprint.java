package nstuff.economy.entity.building;

import nstuff.economy.entity.building.moudles.FunctionalModule;
import nstuff.economy.entity.building.moudles.MineModule;
import nstuff.economy.entity.building.moudles.ProductionModule;
import nstuff.economy.entity.resources.RecipeBlueprint;
import nstuff.economy.entity.resources.ResourceWithAmount;
import nstuff.economy.entity.resources.ResourceWithAmountBlueprint;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vania_000 on 05.03.2015.
 */
@XmlRootElement(name = "BuildingBlueprint")
@XmlType(propOrder = {"name","buildingCategory","workerAmount","neededListBlueprint","inventorySize","productivity","state","size","characteristicList","recipeNames","modules"})
public class BuildingBlueprint {
    public BuildingBlueprint() {
    }

    @XmlElement
    private String name;

    @XmlElement
    private BuildingCategory buildingCategory;

    @XmlElement
    private int workerAmount;

    @XmlElement
    private List<ResourceWithAmountBlueprint> neededListBlueprint = new ArrayList<ResourceWithAmountBlueprint>();

    private List<ResourceWithAmount>  neededList;

    @XmlElement
    private int inventorySize;

    @XmlElement
    private float productivity;

    @XmlElement
    private int state;

    @XmlElement
    private int size;

    @XmlElement
    private List<CharacteristicBlueprint> characteristicList = new ArrayList<CharacteristicBlueprint>();

    @XmlElement
    private List<String>  recipeNames = new ArrayList<String>();

    private List<RecipeBlueprint>  recipeBlueprints;

    @XmlElements({
            @XmlElement(name="MineModule", type=MineModule.class),
            @XmlElement(name="ProductionModule", type=ProductionModule.class)
    })
    private List<FunctionalModule> modules;


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

    public float getProductivity() {
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
    @XmlTransient
    public void setNeededList(List<ResourceWithAmount> neededList) {
        this.neededList = neededList;
        this.neededListBlueprint = null;
    }

    public String getName() {
        return name;
    }
    @XmlTransient
    public void setRecipeBlueprints(List<RecipeBlueprint> recipeBlueprints) {
        this.recipeBlueprints = recipeBlueprints;
        this.recipeNames = null;
    }

    public List<RecipeBlueprint> getRecipeBlueprints() {
        return recipeBlueprints;
    }

    public List<FunctionalModule> getModules() {
        return modules;
    }

    public List<String> getRecipeNames() {
        return recipeNames;
    }
}
