package nstuff.economy.entity.building;

import nstuff.economy.entity.Ticker;
import nstuff.economy.entity.resources.Recipe;
import nstuff.economy.entity.resources.RecipeBlueprint;
import nstuff.economy.entity.resources.Resource;
import nstuff.economy.entity.resources.ResourceBlueprint;
import nstuff.economy.entity.town.TownHall;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vania_000 on 05.03.2015.
 */
public class Building implements Ticker {

    public Building(BuildingBlueprint blueprint,TownHall hall) {
        this.blueprint = blueprint;
        this.townHall = hall;
        for(RecipeBlueprint recipeBlueprint : blueprint.getRecipeBlueprints()){
            recipes.add(new Recipe(recipeBlueprint));
        }
    }

    private List<Recipe> recipes;

    private BuildingBlueprint blueprint;

    private TownHall townHall;

    private int workerAmount;

    private float productivity;

    private int state;

    private List<Resource> inventory = new LinkedList<Resource>();

    public boolean addResource(Resource resource){
        if(getInvSize()+resource.getAmount()>blueprint.getInventorySize()){
            return false;
        }
        for(Resource res :inventory){
            if(res.addResource(resource)){
                return true;
            }
        }
        inventory.add(resource);
        return true;
    }

    public void addResource(ResourceBlueprint resource, float amount) {
        if(getInvSize()+amount>blueprint.getInventorySize()){
            return;
        }
        for(Resource res :inventory){
            if(res.addResource(resource,amount)){
                return;
            }
        }

        inventory.add(new Resource(resource,amount));

    }

    public float getInvSize(){
        float size =0;
        for(Resource resource :inventory){
            size+=resource.getAmount();
        }
        return size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getProductivity() {
        return productivity;
    }

    public void setProductivity(float productivity) {
        this.productivity = productivity;
    }

    public int getWorkerAmount() {
        return workerAmount;
    }

    public void setWorkerAmount(int workerAmount) {
        this.workerAmount = workerAmount;
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public void tick(float hour) {
        
    }


    public void tryDoRecipe(Recipe recipe) {

    }
}
