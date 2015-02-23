import com.google.inject.Guice;
import com.google.inject.Injector;
import nstuff.generator.Generator;
import nstuff.generator.GeneratorException;
import nstuff.generator.GeneratorModule;
import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.geography.PointHeightType;
import nstuff.generator.geography.PointLandType;
import nstuff.generator.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

/**
 * Created by vania_000 on 23.02.2015.
 */
public class Drawer implements ActionListener {

    enum MapType{
        HEIGHT_MAP,
        WATER;
    }

    static Logger logger = LogManager.getLogger(Drawer.class);

    private  int h;

    private int w;

    private  int size;

    Injector injector;

    Generator generator;

    SettingManager settingManager;

    JPanel heightMap;

    JTabbedPane tabbedPane;

    JPanel waterMap;

    Map map;

    public Drawer(){
        injector = Guice.createInjector(new GeneratorModule());
        generator =injector.getInstance(Generator.class);
        try {
            generator.init();
        } catch (GeneratorException e) {
            logger.error(e);
        }
        settingManager =injector.getInstance(SettingManager.class);

        size = settingManager.getMainSetting("size",33,Integer.class);

        h = settingManager.getMainSetting("drawSize",12,Integer.class);
        w =  settingManager.getMainSetting("drawSize",12,Integer.class);

        JFrame frame = new JFrame("FrameDemo");
        tabbedPane = new JTabbedPane();

        heightMap =new JPanel();
        tabbedPane.addTab("Height Map",  heightMap );
        JButton button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        heightMap.add(button);

        waterMap =new JPanel();
        tabbedPane.addTab("Water Map",  waterMap );
        button = new JButton("Regenerate Water");
        button.addActionListener(this);
        button.setSize(10, 10);
        waterMap.add(button);

        frame.add(tabbedPane);
        frame.setSize(Math.round((size+4)*w),Math.round((size+15)*h*3/4));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void drawMap(JPanel panel,MapType type ){
        Graphics2D ig2 = (Graphics2D) panel.getGraphics();
        int fontSize = settingManager.getMainSetting("font",12,Integer.class);
        boolean needFont = settingManager.getMainSetting("fontNeed",false,Boolean.class);
        Font font = new Font("TimesRoman", Font.BOLD, fontSize);
        ig2.setFont(font);
        ig2.translate(w*1,h*4);
        for(int i=0; i<map.getWidth();i++){
            for(int j=0;j<map.getHeight();j++){
                MapPoint point = map.getPoint(i, j);

                switch(type){
                    case HEIGHT_MAP: {
                        PointHeightType height = point.getHeightType();
                        Color color;
                        switch (height) {
                            case OCEAN:
                                color = new Color(32, 28, 152);
                                break;
                            case SEA:
                                color = new Color(1, 27, 152);
                                break;
                            case SEA_SHORE:
                                color = new Color(13, 126, 152);
                                break;
                            case LAND_SHORE:
                                color = new Color(204, 211, 12);
                                break;
                            case PLAIN:
                                color = new Color(0, 152, 41);
                                break;
                            case HILL:
                                color = new Color(152, 72, 0);
                                break;
                            case MOUNTAIN:
                                color = new Color(250, 255, 255);
                                break;
                            default:
                                color = Color.BLACK;
                                break;


                        }
                        ig2.setPaint(color);
                    }
                    break;
                    case WATER: {
                        Color color;
                        PointLandType pointLandType = point.getLandType();
                        switch (pointLandType) {
                            case OCEAN:
                                color = new Color(141, 0, 152);
                                break;
                            case LAND:
                                color = new Color(0, 152, 58);
                                break;
                            case RIVER:
                                color = new Color(1, 27, 152);
                                break;
                            case LAKE:
                                color = new Color(109, 145, 152);
                                break;
                            default:
                                color = Color.BLACK;
                                break;
                        }
                        ig2.setPaint(color);
                    }
                    break;
                }

                   /* if(height>100) {
                        if (height > 150) {
                            color = new Color(250, 255, 255);
                        } else if (height > 140) {
                            color = new Color(152, 72, 0);
                        } else if (height > 120) {
                            color = new Color(0, 152, 41);
                        } else {
                            color = new Color(204, 211, 12);
                        }
                    }else {
                        if (height > 70) {
                            color = new Color(13, 126, 152);
                        } else if (height > 50) {
                            color = new Color(1, 27, 152);
                        } else {

                        }
                    }*/

                float x,y;
                  /*  x = (point.getDrawX()+0.5f)*w;
                    y = (point.getDrawY()+1)*h;*/
                if(j%2==0){
                    //   ig2.setPaint(Color.white);
                    x= (i+1)*w;
                    y =((float)j)*h*3/4;


                }else{
                    // ig2.setPaint(Color.yellow);
                    x= (i+1)*w+w/2;
                    y = ((float)j)*h*3/4;

                }
                ig2.fill(makeHex(x,y,w,h));




                if(needFont) {
                    ig2.setPaint(Color.white);
                    ig2.drawString(point.getX() + "," + point.getY(), x, y);
                }
            }
            // break;
        }
    }

    public  void generate(){


        try {


            map =generator.generateHexMap(size,size);

            Thread.sleep(100);
            switch (tabbedPane.getSelectedIndex()){
                case 0:
                    drawMap(heightMap,MapType.HEIGHT_MAP);
                    break;
                case 1:
                    drawMap(waterMap,MapType.WATER);
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static GeneralPath makeHex(float x,float y, float w, float h){
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        6);
        polygon.moveTo(x, y-h/2);

        polygon.lineTo(x+w/2, y-h/4);

        polygon.lineTo(x+w/2, y+h/4);

        polygon.lineTo(x, y+h/2);

        polygon.lineTo(x-w/2, y+h/4);

        polygon.lineTo(x-w/2, y-h/4);

        polygon.lineTo(x, y -h/2);
        polygon.closePath();
        return polygon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        generate();
    }
}
