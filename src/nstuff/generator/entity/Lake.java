package nstuff.generator.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class Lake {

    private List<MapPoint> points  = new ArrayList<MapPoint>();

    private MapPoint center;


    public Lake(MapPoint center) {
        this.center = center;
        points.add(center);
    }

    public void add(MapPoint point){
        points.add(point);
    }

    public void  closeLake(){
        for(MapPoint point : points){
            point.makeLake(this);
        }
    }
}
