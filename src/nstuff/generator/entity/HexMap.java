package nstuff.generator.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class HexMap implements Map {

    static Logger logger = LogManager.getLogger(Map.class);

    private HexMapPoint[][]  mapPoints;

    private int width;

    private int height;

    private int seaLvl;

    private int islandLevel;

    public HexMap(int w, int h,int seaLvl,int islandLevel){
        mapPoints = new HexMapPoint[w+h/2][h];
        width = w;
        height = h;
        this.seaLvl= seaLvl;
        this.islandLevel =islandLevel;
    }
    public MapPoint getPoint(int x,int y){
      /*  int xIndex = x+height/2;
        if(mapPoints[xIndex][y]==null){
            mapPoints[xIndex][y] = new HexMapPoint(x,y);
        }
        return mapPoints[xIndex][y];*/

        int xIndex =  x-y/2+height/2;

        if(mapPoints[xIndex][y]==null){
            mapPoints[xIndex][y] = new HexMapPoint(x,y,this);
        }
        return mapPoints[xIndex][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getSeaLevel() {
        return seaLvl;
    }
    @Override
    public int getIslandLevel() {
        return islandLevel;
    }

    @Override
    public List<MapPoint> getRadius(int startX, int startY, int radius) {
        List<MapPoint> points = new ArrayList<MapPoint>();
        MapPoint center = getPoint(startX,startY);

        for(int i= startX-radius;i<=startX+radius;i++){
           for(int j=startY-radius;j<=startY+radius;j++){
               if(i>=-height/2&&i<width&&j>=0&&j<height) {
                   int distance =distance(startX,startY,i,j);
                       if(distance <=radius){
                       points.add(getRawPoint(i, j));
                   }

               }
           }
        }

        return points;
    }


    public MapPoint getRawPoint(int x, int y) {
        int xIndex = x+height/2;

        if(mapPoints[xIndex][y]==null){
            mapPoints[xIndex][y] = new HexMapPoint(x+y/2,y,this);
        }
        return mapPoints[xIndex][y];
    }

    /*function hex_distance(a, b):
            return (abs(a.q - b.q)
    + abs(a.q + a.r - b.q - b.r)
    + abs(a.r - b.r)) / 2*/
    private int distance(int startX, int startY, int x, int y) {

        return (Math.abs(startX-x) +Math.abs(startX + startY-y-x) + Math.abs(startY-y))/2;
    }


}
