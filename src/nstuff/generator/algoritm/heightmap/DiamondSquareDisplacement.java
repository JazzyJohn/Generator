package nstuff.generator.algoritm.heightmap;

import nstuff.generator.entity.HexMap;
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

    private int start;

    private int randomCoef;

    private int range;

    private int sectionSize;

    private boolean needUpCenter;

    private boolean wrap = false;

    @Inject
    Random rand;

    private Map map;

    private boolean center = false;

    @Override
    public void generate(Map map) throws HeightMapException {
        this.map = map;
        logger.debug("Generating Step: Generating Height Map");
        minH = settings.getHeightMapSetting("minH", 0, Integer.class);
        start = settings.getHeightMapSetting("start", 0, Integer.class);
        sectionSize = settings.getHeightMapSetting("sectionSize", 33, Integer.class);
        maxH = settings.getHeightMapSetting("maxH", 256, Integer.class);
        randomCoef = settings.getHeightMapSetting("randomCoef", 10, Integer.class);
        needUpCenter = settings.getHeightMapSetting("needUpCenter", false, Boolean.class);
        wrap = settings.getHeightMapSetting("wrap", false, Boolean.class);
        range = maxH - minH;
        if (sectionSize > map.getWidth()) {
            sectionSize = map.getWidth();
        }
        int horCount = (map.getWidth() + 1) / sectionSize;
        int verCount = (map.getHeight() + 1) / sectionSize;
        for (int i = 0; i < horCount; i++) {
            for (int j = 0; j < verCount; j++) {

                center = needUpCenter;
                int xMin = i * sectionSize - 1;
                int xMax = (i + 1) * sectionSize - 1;
                if (i == 0) {
                    xMin = 0;
                    xMax = sectionSize;
                }
                int yMin = j * sectionSize - 1;
                int yMax = (j + 1) * sectionSize - 1;
                if (j == 0) {
                    yMin = 0;
                    yMax = sectionSize;
                }

                logger.debug(("section " + i + " " + j));
                generateStart(xMin, xMax, yMin, yMax);
                oneStep((sectionSize - 1) / 2, xMin, xMax, yMin, yMax);
            }
        }


    }

    private void generateStart(int xMin, int xMax, int yMin, int yMax) {
        MapPoint rightTop = map.getPoint(xMax - 1, yMin),
                leftBottom = map.getPoint(xMin, yMax - 1),
                rightBottom = map.getPoint(xMax - 1, yMax - 1),
                leftTop = map.getPoint(xMin, yMin);


        rightTop.setStartHeight(getStartHeight(xMax == map.getWidth() && yMin == 0));

        leftBottom.setStartHeight(getStartHeight((xMin == 0 && yMax == map.getHeight())));
        rightBottom.setStartHeight(getStartHeight((xMax == map.getWidth() && yMax == map.getHeight())));
        leftTop.setStartHeight(getStartHeight((xMin == 0 && yMin == 0)));

        logger.debug("Right Top" + rightTop);

        logger.debug("Right Bot" + rightBottom);

        logger.debug("Left Top" + leftTop);

        logger.debug("Left Bot" + leftBottom);

    }

    private int getStartHeight(boolean isSide) {
        if (wrap && isSide) {
            return start;
        } else {
            return rand.nextInt(range) + minH;
        }
    }

    private int getMiddleHeight(int stepSize, int... heights) {
        int sum = 0;

        //String str="";
        for (int i : heights) {
            sum += i;
            //  str+=i + "  ";
        }
        sum = sum / heights.length;
        int maxDisplacement = 0;
        for (int i : heights) {
            int dif = Math.abs(sum - i);
            if (dif > maxDisplacement) {
                maxDisplacement = dif;
            }
        }

        // System.out.println(str + sum);
        if (center) {

            center = false;
            int displacment = 0;
            if (randomCoef != 0) {
                displacment = Math.round((rand.nextFloat()) * stepSize * 2 / sectionSize * randomCoef);
            }

            sum += displacment;
            logger.debug(sum);
            //logger.debug(str +sum);
        } else {
            int displacment = 0;
            if (randomCoef != 0) {
                displacment = Math.round((rand.nextFloat() - 0.5f) * stepSize * 2 / sectionSize * randomCoef);
            }
            // logger.debug(displacment);
            sum += displacment;
        }
        return sum;

    }

    private void oneStep(int stepSize, int xMin, int xMax, int yMin, int yMax) {
        if (stepSize == 0) {
            return;
        }
        for (int i = xMin; i + stepSize * 2 < xMax; i += stepSize * 2) {
            for (int j = yMin; j + stepSize * 2 < yMax; j += stepSize * 2) {
                squareStep(stepSize, map.getPoint(i, j));
            }
        }
        for (int i = xMin + stepSize; i < xMax; i += stepSize * 2) {
            for (int j = yMin; j < yMax; j += stepSize * 2) {
                diamondStep(stepSize, map.getPoint(i, j));
            }
        }
        for (int i = xMin; i < xMax; i += stepSize * 2) {
            for (int j = yMin + stepSize; j < yMax; j += stepSize * 2) {
                diamondStep(stepSize, map.getPoint(i, j));
            }
        }
        // randomCoef=randomCoef/2;
        oneStep(stepSize / 2, xMin, xMax, yMin, yMax);

    }

    private void squareStep(int stepSize, MapPoint corner) {
        MapPoint rightTop = map.getPoint(corner.getX() + stepSize * 2, corner.getY()),
                leftBottom = map.getPoint(corner.getX(), corner.getY() + stepSize * 2),
                rightBottom = map.getPoint(corner.getX() + stepSize * 2, corner.getY() + stepSize * 2),
                center = map.getPoint(corner.getX() + stepSize, corner.getY() + stepSize);
        center.setHeight(getMiddleHeight(stepSize, corner.getHeight(), leftBottom.getHeight(), rightBottom.getHeight(), rightTop.getHeight()));


    }

    private void diamondStep(int stepSize, MapPoint center) {
        MapPoint right;
        if (center.getX() + stepSize >= map.getWidth()) {
            right = map.getPoint(center.getX() + stepSize - map.getWidth() + 1, center.getY());
        } else {
            right = map.getPoint(center.getX() + stepSize, center.getY());
        }
        MapPoint bottom;
        if (center.getY() + stepSize >= map.getHeight()) {
            bottom = map.getPoint(center.getX(), center.getY() + stepSize - map.getHeight() + 1);
        } else {
            bottom = map.getPoint(center.getX(), center.getY() + stepSize);
        }
        MapPoint left;
        if (center.getX() - stepSize < 0) {
            left = map.getPoint(map.getWidth() - 1 + (center.getX() - stepSize), center.getY());
        } else {
            left = map.getPoint(center.getX() - stepSize, center.getY());
        }
        MapPoint top;
        if (center.getY() - stepSize < 0) {
            top = map.getPoint(center.getX(), map.getHeight() - 1 + (center.getY() - stepSize));
        } else {
            top = map.getPoint(center.getX(), center.getY() - stepSize);
        }
        center.setHeight(getMiddleHeight(stepSize, right.getHeight(), bottom.getHeight(), left.getHeight(), top.getHeight()));
    }

}
