package nstuff.world.entity;


/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class River extends  AbstractRegion{

    public River(MapPoint start){
        points.add(start);
    }



    public void closeRiver(){
        for(MapPoint point : points){
            point.makeRiver(this);
        }
    }
    public MapPoint getStart(){
        return points.get(0);
    }



}
