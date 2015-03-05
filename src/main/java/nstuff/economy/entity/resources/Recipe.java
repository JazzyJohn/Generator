package nstuff.economy.entity.resources;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */
public class Recipe {

    public Recipe(RecipeBlueprint recipeBlueprint) {
        this.recipeBlueprint = recipeBlueprint;
    }

    private RecipeBlueprint recipeBlueprint;

    public RecipeBlueprint getRecipeBlueprint() {
        return recipeBlueprint;
    }
}
