package nstuff.managers;

import nstuff.economy.entity.building.BuildingBlueprint;
import nstuff.economy.entity.resources.RecipeBlueprint;
import nstuff.economy.entity.resources.ResourceBlueprint;
import nstuff.economy.entity.town.TownHall;
import nstuff.economy.entity.town.TownHallBlueprint;
import nstuff.world.settings.SettingException;

import java.util.List;

/**
 * Created by Ivan.Ochincenko on 06.03.15.
 */
public interface ContentManager  {

    void init() throws SettingException;

    ResourceBlueprint getResource(String name);

    BuildingBlueprint getBuilding(String name);

    TownHallBlueprint getTownHall(String name);

    RecipeBlueprint getRecipe(String name);

    List<TownHallBlueprint> getTownHallByCriteria(TownHallCriteria criteria);
}
