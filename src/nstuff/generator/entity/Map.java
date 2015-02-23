package nstuff.generator.entity;

import java.util.List;

/**
 * Created by vania_000 on 22.02.2015.
 */
public interface Map {

    public MapPoint getPoint(int x,int y);

    public int getWidth();

    public int getHeight();

    List<MapPoint> getRadius(int startX, int startY, int radius);

    public int getSeaLevel();

    public int getIslandLevel();

  //  MapPoint getDrawPoint(int i, int j);
}
