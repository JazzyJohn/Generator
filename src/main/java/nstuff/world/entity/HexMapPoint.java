package nstuff.world.entity;

/**
 * Created by vania_000 on 22.02.2015.
 */
public class HexMapPoint extends MapPoint {

    public static final double SQRT_3 = Math.sqrt(3);

    public HexMapPoint(int x, int y,Map map) {
       super(x,y,map);
    }

   /* @Override
    public float getDrawX() {
        return (float) (x + 0.5 * y);
    }

    @Override
    public float getDrawY() {

            return ((float)y)*3/4;
    }*/
}
