package nstuff.economy.entity.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */
@XmlRootElement(name = "RecipeBlueprint")
@XmlType(propOrder = {"name","neededListBlueprint","resultListBlueprint"})
public class RecipeBlueprint {


    @XmlElement
    private String name;

    @XmlElement
    private List<ResourceWithAmountBlueprint> neededListBlueprint;

    private List<ResourceWithAmount>  neededList;

    @XmlElement
    private List<ResourceWithAmountBlueprint> resultListBlueprint;

    private List<ResourceWithAmount>  resultList;

    public List<ResourceWithAmount> getNeededList() {
        return neededList;
    }
    @XmlTransient
    public void setNeededList(List<ResourceWithAmount> neededList) {
        neededListBlueprint = null;
        this.neededList = neededList;
    }

    public List<ResourceWithAmount> getResultList() {
        return resultList;
    }
    @XmlTransient
    public void setResultList(List<ResourceWithAmount> resultList) {
        resultListBlueprint = null;
        this.resultList = resultList;
    }

    public List<ResourceWithAmountBlueprint> getNeededListBlueprint() {
        return neededListBlueprint;
    }

    public List<ResourceWithAmountBlueprint> getResultListBlueprint() {
        return resultListBlueprint;
    }

    public String getName() {
        return name;
    }
}
