package nstuff.economy.entity.building.moudles;

import nstuff.economy.entity.building.Building;
import nstuff.economy.entity.resources.Resource;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by vania_000 on 05.03.2015.
 */

public interface FunctionalModule {

    ModuleType getType();

    void  doWork(Building building, float time);

}
