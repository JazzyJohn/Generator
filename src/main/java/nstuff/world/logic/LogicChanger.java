package nstuff.world.logic;

import nstuff.world.entity.MapPoint;

/**
 * Created by vania_000 on 22.02.2015.
 */
public interface LogicChanger {

    public void init();

    public boolean changePoint(MapPoint point);
}
