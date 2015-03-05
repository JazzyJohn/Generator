package nstuff.economy.entity.town;

import nstuff.world.entity.MapPoint;

/**
 * Created by Ivan.Ochincenko on 05.03.15.
 */
public class TownHall {

    public TownHall(MapPoint mapPoint, TownHallBlueprint blueprint) {
        this.mapPoint = mapPoint;
        this.blueprint = blueprint;
        this.name = blueprint.getName() +" in " + mapPoint.getBiome().getName();
    }

    private MapPoint mapPoint;

    private String name;

    private TownHallBlueprint blueprint;


}
