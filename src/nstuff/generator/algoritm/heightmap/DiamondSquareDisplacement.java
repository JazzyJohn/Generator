package nstuff.generator.algoritm.heightmap;

import com.sun.org.apache.xpath.internal.operations.Bool;
import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.settings.SettingManager;


import javax.inject.Inject;
import java.util.Random;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Created by vania_000 on 21.02.2015.
 */
public class DiamondSquareDisplacement implements HeightMapGenerator {

    static Logger logger = LogManager.getLogger(DiamondSquareDisplacement.class);

    @Inject
    SettingManager settings;

    private int minH;

    private int maxH;

    private int randomCoef;

    private int range;

    private int sectionSize;

    private boolean wrap=false;

    private Random rand = new Random();

    private Map map;

    private boolean center = false;

    @Override
    public void generate(Map map) throws HeightMapException {
        this.map =map;
        minH = settings.getHeightMapSetting("minH",0,Integer.class);
        sectionSize = settings.getHeightMapSetting("sectionSize",33,Integer.class);
        maxH = settings.getHeightMapSetting("maxH",256,Integer.class);
        randomCoef = settings.getHeightMapSetting("randomCoef",10,Integer.class);
        wrap = settings.getHeightMapSetting("wrap",false, Boolean.class);
        range= maxH-minH;

        int horCount =map.getWidth()/sectionSize;
        int verCount =map.getHeight()/sectionSize;
        for(int i =0;i <horCount;i++){
            for(int j=0;j<verCount;j++){
                generateStart(i,j);
                center = true;
                oneStep((map.getWidth()-1)/2,i,j);
            }
        }


    }
    private  void generateStart(int x, int y){
        MapPoint rightTop = map.getPoint(sectionSize*(x+1)-1,sectionSize*y),
                leftBottom = map.getPoint(sectionSize*x,sectionSize*(x+1)-1),
                rightBottom = map.getPoint(sectionSize*(x+1)-1,sectionSize-1),
                leftTop = map.getPoint(sectionSize*x,sectionSize*y);

        rightTop.setStartHeight(getStartHeight());

        leftBottom.setStartHeight(getStartHeight());
        rightBottom.setStartHeight(getStartHeight());
        leftTop.setStartHeight(getStartHeight());

        logger.debug("Right Top"+ rightTop);

        logger.debug("Right Bot"+ rightBottom);

        logger.debug("Left Top"+ leftTop);

        logger.debug("Left Bot"+ leftBottom);

    }

    private int getStartHeight(){
        if(!wrap) {
            return rand.nextInt(range) + minH;
        }else{
            return minH;
        }
    }
    private  int getMiddleHeight(int stepSize,int... heights){
        int sum  =0;

        //String str="";
        for(int i :heights){
            sum+=i;
          //  str+=i + "  ";
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
        if(center){

            center = false;
            int displacment = 0;
            if (randomCoef != 0) {
                displacment = Math.round((rand.nextFloat()) * stepSize * 2 / sectionSize * randomCoef);
            }

            sum += displacment;
            logger.debug(sum);
            //logger.debug(str +sum);
        }else {
            int displacment = 0;
            if (randomCoef != 0) {
                displacment = Math.round((rand.nextFloat() - 0.5f) * stepSize * 2 / sectionSize * randomCoef);
            }
            // logger.debug(displacment);
            sum += displacment;
        }
        return sum;

    }

    private void oneStep(int stepSize,int xStep,int yStep){
        if(stepSize==0){
            return;
        }
        for(int i=xStep*sectionSize;i+stepSize*2<(xStep+1)*sectionSize;i+=stepSize*2){
            for(int j =yStep*sectionSize;j+stepSize*2<(yStep+1)*sectionSize;j+=stepSize*2) {
                squareStep(stepSize, map.getPoint(i, j));
            }
        }
        for(int i=xStep*sectionSize+stepSize;i<(xStep+1)*sectionSize;i+=stepSize*2){
            for(int j =yStep*sectionSize;j<(yStep+1)*sectionSize;j+=stepSize*2) {
                diamondStep(stepSize, map.getPoint(i, j));
            }
        }
        for(int i=xStep*sectionSize;i<(xStep+1)*sectionSize;i+=stepSize*2){
            for(int j =yStep*sectionSize+stepSize;j<(yStep+1)*sectionSize;j+=stepSize*2) {
                diamondStep(stepSize, map.getPoint(i, j));
            }
        }
       // randomCoef=randomCoef/2;
        oneStep(stepSize/2,xStep,yStep);

    }

    private void squareStep(int stepSize,MapPoint corner){
        MapPoint rightTop = map.getPoint(corner.getX()+stepSize*2,corner.getY()),
                leftBottom = map.getPoint(corner.getX(),corner.getY()+stepSize*2),
                rightBottom = map.getPoint(corner.getX()+stepSize*2,corner.getY()+stepSize*2),
                center = map.getPoint(corner.getX()+stepSize,corner.getY()+stepSize);
        center.setHeight(getMiddleHeight(stepSize,corner.getHeight(),leftBottom.getHeight(),rightBottom.getHeight(),rightTop.getHeight()));


    }
    private void diamondStep(int stepSize,MapPoint center){
        MapPoint right;
        if(center.getX()+stepSize>=map.getWidth()){
            right = map.getPoint(center.getX() + stepSize - map.getWidth()+1, center.getY());
        }else{
            right =map.getPoint(center.getX()+stepSize,center.getY());
        }
        MapPoint bottom;
        if(center.getY()+stepSize>=map.getHeight()){
            bottom = map.getPoint(center.getX(),center.getY()+stepSize-map.getHeight()+1);
        }else{
            bottom =map.getPoint(center.getX(),center.getY()+stepSize);
        }
        MapPoint left;
        if(center.getX()-stepSize<0){
            left = map.getPoint(map.getWidth()-1+(center.getX()-stepSize),center.getY());
        }else{
            left =map.getPoint(center.getX()-stepSize,center.getY());
        }
        MapPoint top;
        if(center.getY()-stepSize<0){
            top = map.getPoint(center.getX(),map.getHeight()-1+(center.getY()-stepSize));
        }else{
            top =map.getPoint(center.getX(),center.getY()-stepSize);
        }
        center.setHeight(getMiddleHeight(stepSize,right.getHeight(), bottom.getHeight(),left.getHeight(),top.getHeight()));
    }

}
