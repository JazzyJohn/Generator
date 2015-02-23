package nstuff.generator.algoritm.grower;

import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.logic.LogicChanger;

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
        List<MapPoint> points = map.getRadius(startX,startY,size);
        for(MapPoint point : points){
            changer.changePoint(point);
        }

    }
}
