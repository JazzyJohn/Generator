package nstuff.economy.entity.building;

import nstuff.economy.entity.Ticker;
import nstuff.economy.entity.resources.Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vania_000 on 05.03.2015.
 */
public class Building implements Ticker {

    public Building(BuildingBlueprint blueprint) {
        this.blueprint = blueprint;
    }



    private BuildingBlueprint blueprint;

    private int workerAmount;

    private int productivity;

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

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }

    public int getWorkerAmount() {
        return workerAmount;
    }

    public void setWorkerAmount(int workerAmount) {
        this.workerAmount = workerAmount;
    }

    @Override
    public void tick(int hour) {
        
    }
}
