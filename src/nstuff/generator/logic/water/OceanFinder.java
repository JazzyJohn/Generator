package nstuff.generator.logic.water;

import nstuff.generator.entity.MapPoint;
import nstuff.generator.geography.PointLandType;
import nstuff.generator.logic.LogicFinder;

/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class OceanFinder implements LogicFinder{
    @Override
    public boolean find(MapPoint point) {
        return point.getLandType()== PointLandType.OCEAN;

    }
}
