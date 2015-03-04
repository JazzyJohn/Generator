package nstuff.economy.entity.building;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by vania_000 on 05.03.2015.
 */
@XmlRootElement(name = "ResourceWithAmountBlueprint")
@XmlType(propOrder = {"resource","amount"})
public class CharacteristicBlueprint {

    public CharacteristicBlueprint() {
    }

    @XmlElement
    private String characteristic;
    @XmlElement
    private int amount;

    public String getCharacteristic() {
        return characteristic;
    }

    public int getAmount() {
        return amount;
    }
}
