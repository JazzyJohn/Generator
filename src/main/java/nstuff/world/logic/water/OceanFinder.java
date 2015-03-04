package nstuff.world.logic.water;

import nstuff.world.entity.MapPoint;
import nstuff.world.geography.PointLandType;
import nstuff.world.logic.LogicFinder;

/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class OceanFinder implements LogicFinder{
    @Override
    public boolean find(MapPoint point) {
        return point.getLandType()== PointLandType.OCEAN;

    }
}
