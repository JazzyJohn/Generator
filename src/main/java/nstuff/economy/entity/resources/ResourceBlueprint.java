package nstuff.economy.entity.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by vania_000 on 05.03.2015.
 */

@XmlRootElement(name = "ResourceBlueprint")
@XmlType(propOrder = {"productivity","baseCost","level","baseSize","resourceCategory","lifeTime","name"})
public class ResourceBlueprint {

    public ResourceBlueprint() {
    }

    @XmlElement
    private String name;

    @XmlElement
    private int productivity;

    @XmlElement
    private int baseCost;

    @XmlElement
    private int level;

    @XmlElement
    private int baseSize;

    @XmlElement
    private ResourceCategory resourceCategory;

    /*
    Time to get from one ExpirationState to another in game hours
     */

    @XmlElement
    private int lifeTime;


    public String getName() {
        return name;
    }

    public int getProductivity() {
        return productivity;
    }

    public int getBaseCost() {
        return baseCost;
    }

    public int getLevel() {
        return level;
    }

    public int getBaseSize() {
        return baseSize;
    }

    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
