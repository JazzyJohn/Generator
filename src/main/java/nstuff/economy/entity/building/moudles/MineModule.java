package nstuff.economy.entity.building.moudles;

import nstuff.economy.entity.building.Building;
import nstuff.economy.entity.resources.ResourceWithAmount;
import nstuff.economy.entity.resources.ResourceWithAmountBlueprint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */
@XmlRootElement( name = "MineModule" )
@XmlType(propOrder = {"mineResourcesBlueprint"})
public class MineModule implements FunctionalModule {

    @XmlElement
    private List<ResourceWithAmountBlueprint> mineResourcesBlueprint;

    private List<ResourceWithAmount>  mineResources;

    public void setMineResources(List<ResourceWithAmount> mineResources) {
        this.mineResourcesBlueprint = null;
        this.mineResources = mineResources;
    }

    @Override
    public ModuleType getType() {
        return ModuleType.MINE;
    }

    @Override
    public void doWork(Building building, float time) {
        for(ResourceWithAmount resource : mineResources ){
            building.addResource(resource.getResource(),resource.getAmount()*time*building.getProductivity());
        }
    }
}
