package nstuff.world.entity;

import nstuff.world.logic.LogicFinder;

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

    boolean isInRadius(int startX, int startY, int radius,LogicFinder finder);

    MapPoint getClosestInRange(int x, int y, int maxDistance,LogicFinder finder);

    List<MapPoint> getRadius(int startX, int startY, int radius,boolean valid);
  //  MapPoint getDrawPoint(int i, int j);

    int distance(int startX, int startY, int x, int y);

    int distance(MapPoint start, MapPoint end);

    void addRiver(River river);

    void addLake(Lake lake);



}
