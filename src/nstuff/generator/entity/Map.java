package nstuff.generator.entity;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class Map {

    private MapPoint[][]  mapPoints;

    private int width;

    private int height;

    public Map( int w,int h){
        mapPoints = new MapPoint[w][h];
        width = w;
        height = h;
    }
    public MapPoint getPoint(int x,int y){
        if(mapPoints[x][y]==null){
            mapPoints[x][y] = new MapPoint(x,y);
        }
        return mapPoints[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
