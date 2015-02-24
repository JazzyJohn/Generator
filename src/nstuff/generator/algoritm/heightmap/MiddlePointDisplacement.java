package nstuff.generator.algoritm.heightmap;

import nstuff.generator.entity.HexMap;
import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Random;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class MiddlePointDisplacement implements HeightMapGenerator {

    static Logger logger = LogManager.getLogger(MiddlePointDisplacement.class);

    @Inject
    protected SettingManager settings;

    private int minH;

    private int maxH;

    private int randomCoef;



    private int range;

    @Inject
    Random rand;

    private Map map;

    @Override
    public void generate(Map map) throws HeightMapException {
        this.map =map;
        minH = settings.getHeightMapSetting("minH",0,Integer.class);
        maxH = settings.getHeightMapSetting("maxH",256,Integer.class);
        randomCoef = settings.getHeightMapSetting("randomCoef",10,Integer.class);

        range= maxH-minH;
        generateStart();
        oneStep((map.getWidth()-1)/2);

    }
    private  void generateStart(){
        MapPoint rightTop = map.getPoint(map.getWidth()-1,0),
                leftBottom = map.getPoint(0,map.getHeight()-1),
                rightBottom = map.getPoint(map.getWidth()-1,map.getHeight()-1),
                leftTop = map.getPoint(0,0);

        rightTop.setHeight(getStartHeight());
        leftBottom.setHeight(getStartHeight());
        rightBottom.setHeight(getStartHeight());
        leftTop.setHeight(getStartHeight());

        logger.debug("Right Top"+ rightTop);

        logger.debug("Right Bot"+ rightBottom);

        logger.debug("Left Top"+ leftTop);

        logger.debug("Left Bot"+ leftBottom);
    }

    private int getStartHeight(){

        return minH;
        //return rand.nextInt(range/2)+minH;
    }
    private  int getMiddleHeight(int stepSize,int... heights){
        int sum  =0;
        // String str="";
        for(int i :heights){
            sum+=i;
            //   str+=i + "  ";
        }
        sum =sum / heights.length;
        int maxDisplacement=0;
        for(int i:heights){
            int dif = Math.abs(sum-i);
            if(dif>maxDisplacement){
                maxDisplacement =dif;
            }
        }
        // System.out.println(str + sum);
        int displacment=0;
        if(randomCoef!=0){
            displacment = Math.round((rand.nextFloat()-0.5f)*stepSize*2/map.getWidth()*randomCoef);
        }
        // logger.debug(displacment);
        sum+=displacment;
        return sum;

    }
    private void oneStep(int stepSize){
        if(stepSize==0){
            return;
        }
        for(int i=0;i+stepSize*2<map.getWidth();i+=stepSize*2){
            for(int j =0;j+stepSize*2<map.getHeight();j+=stepSize*2) {
                squareStep(stepSize, map.getPoint(i, j));
            }
        }
        for(int i=0;i+stepSize*2<map.getWidth();i+=stepSize*2){
            for(int j =0;j<map.getHeight();j+=stepSize*2) {
                horizontalStep(stepSize, map.getPoint(i, j));
            }
        }
        for(int i=0;i<map.getWidth();i+=stepSize*2){
            for(int j =0;j+stepSize*2<map.getHeight();j+=stepSize*2) {
                verticalStep(stepSize, map.getPoint(i, j));
            }
        }
      //  randomCoef=randomCoef/2;
        oneStep(stepSize/2);

    }

    private void squareStep(int stepSize,MapPoint corner){
        MapPoint rightTop = map.getPoint(corner.getX()+stepSize*2,corner.getY()),
                leftBottom = map.getPoint(corner.getX(),corner.getY()+stepSize*2),
                rightBottom = map.getPoint(corner.getX()+stepSize*2,corner.getY()+stepSize*2),
                center = map.getPoint(corner.getX()+stepSize,corner.getY()+stepSize);
        center.setHeight(getMiddleHeight(stepSize,corner.getHeight(),leftBottom.getHeight(),rightBottom.getHeight(),rightTop.getHeight()));


    }
    private void verticalStep(int stepSize,MapPoint corner){
        MapPoint leftBottom = map.getPoint(corner.getX(),corner.getY()+stepSize*2),
                 center = map.getPoint(corner.getX(),corner.getY()+stepSize);
        center.setHeight(getMiddleHeight(stepSize,corner.getHeight(), leftBottom.getHeight()));
    }
    private void horizontalStep(int stepSize,MapPoint corner){
        MapPoint  rightTop = map.getPoint(corner.getX()+stepSize*2,corner.getY()),
                center = map.getPoint(corner.getX()+stepSize,corner.getY());
        center.setHeight(getMiddleHeight(stepSize,corner.getHeight(),rightTop.getHeight()));
    }
}
