package nstuff.economy.entity.building.moudles;

import nstuff.economy.entity.building.Building;
import nstuff.economy.entity.resources.Recipe;
import nstuff.economy.entity.resources.RecipeBlueprint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */

@XmlRootElement( name = "ProductionModule" )
@XmlType(propOrder = {"recipeBlueprints"})
public class ProductionModule implements FunctionalModule {

    @XmlElement
    private List<RecipeBlueprint> recipeBlueprints;


    @Override
    public ModuleType getType() {
        return ModuleType.PRODUCTION;
    }

    @Override
    public void doWork(Building building, float time) {
        for(Recipe recipe : building.getRecipes()){
            if(recipeBlueprints.contains(recipe.getRecipeBlueprint())){
                building.tryDoRecipe(recipe);
            }
        }
    }
}
