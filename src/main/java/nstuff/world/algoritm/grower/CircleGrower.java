package nstuff.world.algoritm.grower;

import nstuff.world.entity.Map;
import nstuff.world.entity.MapPoint;
import nstuff.world.logic.LogicChanger;

import java.util.List;

/**
 * Created by vania_000 on 22.02.2015.
 */
public class CircleGrower implements  Grower{

    private int startX;

    private int startY;

    private  int size;

    @Override
    public void setUp(int size, int startX, int startY) {
        this.size =size;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void grow(Map map, LogicChanger changer) {
        logger.debug("Circle Grower At Work");
        List<MapPoint> points = map.getRadius(startX,startY,size);
        for(MapPoint point : points){
            changer.changePoint(point);
        }

    }

    @Override
    public void setUp(int size, MapPoint center) {
        setUp(size,center.getX(),center.getY());
    }
}
