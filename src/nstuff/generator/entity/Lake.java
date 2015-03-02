package nstuff.generator.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class Lake extends  AbstractRegion{


    private MapPoint center;


    public Lake(MapPoint center) {
        this.center = center;
        points.add(center);
    }


    public void  closeLake(){
        for(MapPoint point : points){
            point.makeLake(this);
        }
    }
}
