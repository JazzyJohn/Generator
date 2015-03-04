package nstuff.economy.entity.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by vania_000 on 05.03.2015.
 */

public class ResourceWithAmount {

    public ResourceWithAmount(ResourceBlueprint resource,int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    private ResourceBlueprint resource;

    private float amount;

    public ResourceBlueprint getResource() {
        return resource;
    }

    public float getAmount() {
        return amount;
    }
}
