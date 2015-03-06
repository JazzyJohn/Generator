package nstuff.economy.entity.building.moudles;

import nstuff.economy.entity.building.Building;
import nstuff.economy.entity.resources.Recipe;
import nstuff.economy.entity.resources.RecipeBlueprint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */

@XmlRootElement( name = "ProductionModule" )
@XmlType(propOrder = {"recipeNames"})
public class ProductionModule implements FunctionalModule {

    @XmlElement
    private List<String> recipeNames;

    private List<RecipeBlueprint> recipeBlueprints;

    public List<String> getRecipeNames() {
        return recipeNames;
    }

    @XmlTransient
    public void setRecipeBlueprints(List<RecipeBlueprint> recipeBlueprints) {
        this.recipeBlueprints = recipeBlueprints;
        recipeNames = null;
    }

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
