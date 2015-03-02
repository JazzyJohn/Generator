package nstuff.generator.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public class AbstractRegion {
    protected List<MapPoint> points  = new LinkedList<MapPoint>();

    public void add(MapPoint point){
        points.add(point);
    }
}
