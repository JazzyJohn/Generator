package nstuff.economy.entity.resources;

/**
 * Created by vania_000 on 05.03.2015.
 */
public class Resource {

    public Resource(ResourceBlueprint blueprint, float amount) {
        this.blueprint = blueprint;
        this.amount = amount;
        state = ExpirationState.FRESH;
    }

    private ResourceBlueprint blueprint;

    private float amount;

    private ExpirationState state;

    public ResourceBlueprint getBlueprint() {
        return blueprint;
    }

    public float getAmount() {
        return amount;
    }

    public ExpirationState getState() {
        return state;
    }
    public boolean addResource(Resource other){
        if(blueprint==other.blueprint&&state==other.state){
            amount+= other.amount;
            return true;
        }
        return false;
    }

    public boolean spendResource(float amount){
        this.amount-=amount;
        return this.amount>0;
    }

    public boolean addResource(ResourceBlueprint resource, float amount) {
        if(blueprint==resource&&state==ExpirationState.FRESH){
            this.amount+= amount;
            return true;
        }
        return false;
    }
}
