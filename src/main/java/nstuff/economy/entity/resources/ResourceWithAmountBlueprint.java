package nstuff.economy.entity.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by vania_000 on 05.03.2015.
 */
@XmlRootElement(name = "ResourceWithAmountBlueprint")
@XmlType(propOrder = {"resource","amount"})
public class ResourceWithAmountBlueprint {

    public ResourceWithAmountBlueprint() {
    }

    @XmlElement
    private String resource;
    @XmlElement
    private float amount;

    public String getResource() {
        return resource;
    }

    public float getAmount() {
        return amount;
    }
}
