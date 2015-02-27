package nstuff.generator.algoritm.rivers;

import nstuff.generator.entity.Lake;
import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.entity.River;
import nstuff.generator.entity.pathfinding.Node;
import nstuff.generator.logic.LogicFinder;
import nstuff.generator.settings.SettingManager;
import nstuff.generator.utils.SortedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vania_000 on 23.02.2015.
 */
public class RunningDownRiver implements RiverGenerator {

    static Logger logger = LogManager.getLogger(RunningDownRiver.class);

    public int riverCnt;

    public int riverDistance;

    public int lakeDepth;

    @Inject
    SettingManager settings;

    @Inject
    @Named("OceanFinder")
    private LogicFinder waterFinder;

    @Inject
    Random rand;

    @Override
    public void init() {
        riverCnt = settings.getRiverSetting("riverCnt", 30, Integer.class);
        riverDistance  = settings.getRiverSetting("riverDistance", 2, Integer.class);
        lakeDepth = settings.getRiverSetting("lakeDepth",20,Integer.class);
    }

    @Override
    public void generate(Map map) {
        List<MapPoint> riverPossiblePoint = new ArrayList<MapPoint>();
        for(int i =0;i<map.getWidth();i++){
            for(int j=0;j<map.getHeight();j++ ){
               if(!map.isInRadius(i,j,riverDistance,waterFinder)){
                   riverPossiblePoint.add(map.getPoint(i,j));
               }
            }
        }
        logger.debug("Find "+ riverPossiblePoint.size()+" possible river point");
        logger.debug("Generating  "+ riverCnt+"  rivers ");
        for(int i =0;i<riverCnt;i++){
            logger.debug("Generating  ¹"+ i+"  river ");
            int index = rand.nextInt(riverPossiblePoint.size());
            MapPoint start =  riverPossiblePoint.remove(index);
            if(start.isWater()){
               continue;
            }
            River river = new River(start);
            Lake lake = generateRiver(river,map);
            river.closeRiver();
            map.addRiver(river);
            if(lake!=null){
                lake.closeLake();
                map.addLake(lake);
            }

        }


    }

    private Lake generateRiver(River river,Map map) {
        SortedList open  = new SortedList();
        List<Node> close= new ArrayList<Node>();
        Node.clear(map.getWidth(),map.getHeight());
        Node start =  Node.getInstance(river.getStart());
        start.setHeuristic(map.getHeight());

        open.add(start);
        Node current =start;
        while( open.size()>0){
            current = (Node) open.first();
            if(map.getPoint(current.getX(),current.getY()).isWater()){
                logger.debug("Resolving  river path");
                LinkedList<Node> path = new LinkedList<Node>();

                while(current!=null){
                    path.add(current);
                    current = current.getParent();
                }
                logger.debug("Writing into river");
                current = path.pollLast();
                while(current!=null){
                    if(current!=start){
                        river.add(map.getPoint(current.getX(),current.getY()));
                    }
                    current = path.pollLast();
                }
                return null;
            }

            open.remove(current);
            close.add(current);
            MapPoint currentPoint = map.getPoint(current.getX(),current.getY());
            for(MapPoint neighborMapPoint :map.getRadius(current.getX(),current.getY(),1,true)){
                if(currentPoint.getHeight()<neighborMapPoint.getHeight()){
                    continue;
                }
                Node neighbor = Node.getInstance(neighborMapPoint);
                if(close.contains(neighbor)){
                    continue;
                }
                int temp = current.getCost() +1;
                if(!open.contains(neighbor)||temp<neighbor.getCost()){
                    neighbor.setParent(current);
                    neighbor.setCost(temp);
                    neighbor.setHeuristic(start.getHeuristic() - map.distance(start.getX(), start.getY(), neighbor.getX(), neighbor.getY()));
                    if(!open.contains(neighbor)){
                        open.add(neighbor);
                    }
                }


            }
        }
        logger.debug("Resolving  Lake");
        List<MapPoint> resolved= new ArrayList<MapPoint>();
        List<Node> lakeNodes = new ArrayList<Node>();
        LinkedList<MapPoint> lakePossible= new LinkedList<MapPoint>();
        MapPoint currentPoint = map.getPoint(current.getX(),current.getY());
        MapPoint startPoint= currentPoint;
        Lake lake= new Lake(currentPoint);

        for(MapPoint neighborMapPoint :map.getRadius(current.getX(),current.getY(),1,true)){
            if(lakePossible.contains(neighborMapPoint)||resolved.contains(neighborMapPoint)){
                continue;
            }
            lakePossible.add(neighborMapPoint);

        }


        while(currentPoint!=null){
            resolved.add(currentPoint);
            if(currentPoint.getHeight()<startPoint.getHeight()||(currentPoint.getHeight()-startPoint.getHeight())<lakeDepth){

                for(MapPoint neighborMapPoint :map.getRadius(current.getX(),current.getY(),1,true)){
                    if(lakePossible.contains(neighborMapPoint)||resolved.contains(neighborMapPoint)){
                        continue;
                    }
                    if(neighborMapPoint.isSaltWater()){
                        continue;
                    }
                    lakePossible.add(neighborMapPoint);

                }
                lakeNodes.add(Node.getInstance(currentPoint));
                lake.add(currentPoint);
            }
            currentPoint= lakePossible.pollFirst();
        }

        logger.debug("Resolving  river path");
        LinkedList<Node> path = new LinkedList<Node>();

        while(current!=null){
            path.add(current);
            current = current.getParent();
        }
        logger.debug("Writing into river");
        current = path.pollLast();
        while(current!=null){
            if(!lakeNodes.contains(current)){
                river.add(map.getPoint(current.getX(),current.getY()));
            }
            current = path.pollLast();
        }

        return lake;
    }
}
