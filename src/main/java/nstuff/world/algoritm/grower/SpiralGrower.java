package nstuff.world.algoritm.grower;

import nstuff.world.entity.Map;
import nstuff.world.entity.MapPoint;
import nstuff.world.logic.LogicChanger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vania_000 on 02.03.2015.
 */
public class SpiralGrower implements Grower {

    private int startX;

    private int startY;

    private  int size;


    @Override
    public void setUp(int size, int startX, int startY) {
        this.size = size;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void grow(Map map, LogicChanger changer) {
        logger.debug("Spiral Grower At Work");
        LinkedList<MapPoint> open = new LinkedList<MapPoint>();
        LinkedList<MapPoint> resolved = new LinkedList<MapPoint>();
        MapPoint center =map.getPoint(startX,startY);
        open.add(center);
        while(open.size()>0){
            MapPoint current = open.pollFirst();
            resolved.add(current);
            if(map.distance(current,center)>size){

                continue;
            }
            if(changer.changePoint(current)){
                List<MapPoint> neighbors= map.getRadius(current.getX(),current.getY(),1,true);
                for(MapPoint neighbor : neighbors ){
                    if(open.contains(neighbor)||resolved.contains(neighbor)){
                        continue;
                    }
                    open.add(neighbor);
                }
            }
        }
    }

    @Override
    public void setUp(int size, MapPoint center) {
        setUp(size,center.getX(),center.getY());
    }
}
