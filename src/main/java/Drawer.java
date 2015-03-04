import com.google.inject.Guice;
import com.google.inject.Injector;
import nstuff.world.Generator;
import nstuff.world.GeneratorException;
import nstuff.world.GeneratorModule;
import nstuff.world.entity.Map;
import nstuff.world.entity.MapPoint;
import nstuff.world.entity.biom.Biome;
import nstuff.world.geography.PointHeightType;
import nstuff.world.geography.PointHumidityType;
import nstuff.world.geography.PointLandType;
import nstuff.world.geography.PointTemperatureType;
import nstuff.world.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by vania_000 on 23.02.2015.
 */
public class Drawer implements ActionListener,MouseListener {



    enum MapType{
        HEIGHT_MAP,
        ALL_IN,
        WATER,
        TEMPERATURE,
        HUMIDITY,
        BIOME;
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

    JPanel allinMap;

    JPanel temperatureMap;

    JPanel humidityMap;

    JPanel biomMap;
    Map map;

    public static final int W_OFFSET= 1;

    public static final int H_OFFSET= 4;

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

        JFrame frame = new JFrame("Generator");
        tabbedPane = new JTabbedPane();

        allinMap =new JPanel();
        tabbedPane.addTab("All Map",  allinMap );
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                draw();
            }
        });
        JButton button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        allinMap.add(button);
        allinMap.addMouseListener(this);

        heightMap =new JPanel();
        tabbedPane.addTab("Height Map",  heightMap );
        button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        heightMap.add(button);
        heightMap.addMouseListener(this);

        waterMap =new JPanel();
        tabbedPane.addTab("Water Map",  waterMap );
        button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        waterMap.add(button);
        waterMap.addMouseListener(this);

        temperatureMap =new JPanel();
        tabbedPane.addTab("Temperature Map",  temperatureMap );
        button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        temperatureMap.add(button);
        temperatureMap.addMouseListener(this);

        humidityMap =new JPanel();
        tabbedPane.addTab("Humidity Map",  humidityMap );
        button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        humidityMap.add(button);
        humidityMap.addMouseListener(this);

        biomMap =new JPanel();
        tabbedPane.addTab("Biom Map",  biomMap );
        button = new JButton("Regenerate");
        button.addActionListener(this);
        button.setSize(10, 10);
        biomMap.add(button);
        biomMap.addMouseListener(this);


        frame.add(tabbedPane);
        frame.setSize(Math.round((size+10)*w),Math.round((size+15)*h*3/4));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
               System.out.println(e.getKeyCode());
                if(e.getKeyCode()==10){

                    switch (tabbedPane.getSelectedIndex()){
                        case 0:
                            save(MapType.ALL_IN);
                        break;
                        case 1:
                            save(MapType.HEIGHT_MAP);
                            break;
                        case 2:
                            save(MapType.WATER);
                            break;
                        case 3:
                            save(MapType.TEMPERATURE);
                            break;
                        case 4:

                            save(MapType.HUMIDITY);
                            break;
                        case 5:
                            save(MapType.BIOME);
                            break;
                    }
                }
                if(e.getKeyCode()==116){
                    draw();
                }
            }
            public void keyReleased(KeyEvent e) {  }

            public void keyTyped(KeyEvent e) {  }
        });
        frame.setFocusable(true);

    }

    public void drawMap(JPanel panel,MapType type ){
        Graphics2D ig2 = (Graphics2D) panel.getGraphics();
        ig2.translate(w*W_OFFSET,h*H_OFFSET);
        drawMap(ig2,type);
    }

    public void save(MapType type ){

        try {
            BufferedImage image = new BufferedImage(Math.round((size+2)*w),Math.round((size+2)*h*3/4),BufferedImage.TYPE_INT_RGB);
            Graphics2D ig2 = image.createGraphics();
            ig2.translate(w*1,h*1);
            drawMap(ig2,type);

            String name ="saved.png";
            switch (type){
                case TEMPERATURE:
                    name ="temperature.png";
                    break;
                case ALL_IN:
                    name ="allin.png";
                    break;
                case HEIGHT_MAP:
                    name ="height.png";
                    break;
                case WATER:
                    name ="water.png";
                    break;
                case HUMIDITY:
                    name ="humidity.png";
                    break;
                case BIOME:
                    name ="biome.png";
                    break;
            }
            File outputfile = new File(name);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void drawMap(Graphics2D ig2,MapType type ){

        int fontSize = settingManager.getMainSetting("font",12,Integer.class);
        boolean needFont = settingManager.getMainSetting("fontNeed",false,Boolean.class);
        Font font = new Font("TimesRoman", Font.BOLD, fontSize);
        ig2.setFont(font);

        for(int i=0; i<map.getWidth();i++){
            for(int j=0;j<map.getHeight();j++){
                MapPoint point = map.getPoint(i, j);
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
                switch(type){
                    case ALL_IN: {
                        PointHeightType height = point.getHeightType();
                        Color color;
                        PointLandType pointLandType = point.getLandType();
                        switch (pointLandType) {
                            case LAKE:
                                color = new Color(13, 126, 152);
                                break;
                            case RIVER:
                                color = new Color(0, 28, 152);
                                break;

                            default:
                                switch (height) {
                                    case OCEAN:
                                        color = new Color(114, 126, 140);
                                        break;
                                    case SHOAL:
                                        color = new Color(128, 173, 214);
                                        break;
                                    case SEA_SHORE:
                                        color = new Color(122, 204, 138);
                                        break;
                                    case LOWLAND:
                                        color = new Color(131, 255, 159);
                                        break;
                                    case HILL:
                                        color = new Color(254, 255, 117);
                                        break;
                                    case MOUNTAIN:
                                        color = new Color(237, 184, 116);
                                        break;
                                    case MOUNTAIN_PEAK:
                                        color = new Color(207, 115, 118);
                                        break;
                                    default:
                                        color = Color.BLACK;
                                        break;


                                }
                                break;
                        }

                        ig2.setPaint(color);

                    }
                    break;
                    case HEIGHT_MAP: {
                        PointHeightType height = point.getHeightType();
                        Color color;

                        switch (height) {
                            case OCEAN:
                                color = new Color(32, 28, 152);
                                break;
                            case SHOAL:
                                color = new Color(1, 27, 152);
                                break;
                            case SEA_SHORE:
                                color = new Color(13, 126, 152);
                                break;
                            case LOWLAND:
                                color = new Color(204, 211, 12);
                                break;
                            case HILL:
                                color = new Color(0, 152, 41);
                                break;
                            case MOUNTAIN:
                                color = new Color(152, 72, 0);
                                break;
                            case MOUNTAIN_PEAK:
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
                    case TEMPERATURE: {
                        Color color;
                        PointTemperatureType  pointTemperatureType = point.getTemperatureType();
                        switch (pointTemperatureType) {
                            case ARCTIC:
                                color = new Color(114, 126, 140);
                                break;
                            case SUB_ARCTIC:
                                color = new Color(128, 173, 214);
                                break;
                            case TEMPERATE:
                                color = new Color(122, 204, 138);
                                break;
                            case SUB_TROPICAL:
                                color = new Color(131, 255, 159);
                                break;
                            case TROPICAL:
                                color = new Color(136, 152, 7);
                                break;
                            case SUB_EQUATORIAL:
                                color = new Color(237, 184, 116);
                                break;
                            case EQUATORIAL:
                                color = new Color(207, 115, 118);
                                break;
                            default:
                                color = Color.BLACK;
                                break;
                        }
                        ig2.setPaint(color);
                    }
                    break;
                    case HUMIDITY: {
                        Color color;
                        PointHumidityType pointHumidityType = point.getHumidityType();
                        switch (pointHumidityType) {
                            case VERY_LOW:
                                color = new Color(199, 198, 23);
                                break;
                            case LOW:
                                color = new Color(126, 199, 24);
                                break;
                            case NORMAL:
                                color = new Color(65, 214, 40);
                                break;
                            case HIGH:
                                color = new Color(109, 253, 255);
                                break;
                            case VERY_HIGH:
                                color = new Color(0, 24, 204);
                                break;
                            default:
                                color = Color.BLACK;
                                break;
                        }
                        ig2.setPaint(color);
                    }
                    break;
                    case BIOME: {
                        Color color;
                        Biome biome = point.getBiome();
                        if(biome==null){
                            color = Color.BLACK;
                        }else{
                            switch (biome.getProtoBiome()) {
                                case GLACIER:
                                    color = new Color(161, 203, 216);
                                    break;
                                case OCEAN:
                                    color = new Color(49, 134, 208);
                                    break;
                                case SHOAL:
                                    color = new Color(120, 166, 214);
                                    break;
                                case DESERT_SAND:
                                    color = new Color(254, 255, 117);
                                    break;
                                case GRASSLAND:
                                    color = new Color(98, 204, 145);
                                    break;
                                case SAVANNA:
                                    color = new Color(225, 255, 105);
                                    break;
                                case SWAMP:
                                    color = new Color(93, 147, 110);
                                    break;
                                case SHRUBLAND:
                                    color = new Color(181, 204, 114);
                                    break;
                                case TUNDRA:
                                    color = new Color(224, 255, 151);
                                    break;
                                case FOREST_BROADLEAF:
                                    color = new Color(131, 255, 159);
                                    break;
                                case FOREST_CONIFER:
                                    color = new Color(76, 190, 29);
                                    break;
                                case MOUNTAIN:
                                    color = new Color(237, 184, 116);
                                    break;
                                case MOUNTAIN_PEAK:
                                    color = new Color(207, 115, 118);
                                    break;
                                case DESERT_ROCK:
                                    color = new Color(182, 151, 65);
                                    break;
                                case BADLANDS:
                                    color = new Color(140, 120, 100);
                                    break;
                                case JUNGLE:
                                    color = new Color(88, 222, 178);
                                    break;
                                default:
                                    color = Color.BLACK;
                                    break;
                            }
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


                if(i==markQ&&j==markR){
                    ig2.setPaint(Color.red);
                }


                ig2.fill(makeHex(x,y,w,h));




                if(needFont) {
                    ig2.setPaint(Color.white);
                    ig2.drawString(point.getX() + "," + point.getY(), x, y);
                }else{
                   if(type==MapType.ALL_IN||type==MapType.BIOME) {
                       y+=h/3;
                       x-=w/3;
                       if (point.getBiome() != null) {
                           ig2.setPaint(new Color(237, 0, 255));
                           ig2.drawString(point.getBiome().toString(), x, y);

                       } else {
                           ig2.setPaint(new Color(237, 0, 255));
                           ig2.drawString("N", x, y);

                       }

                   }
                }
            }
            // break;
        }
    }
    void draw(){


        switch (tabbedPane.getSelectedIndex()){
            case 0:
                drawMap(heightMap,MapType.ALL_IN);
                break;
            case 1:
                drawMap(waterMap,MapType.HEIGHT_MAP);
                break;
            case 2:
                drawMap(waterMap,MapType.WATER);
                break;
            case 3:
                drawMap(temperatureMap,MapType.TEMPERATURE);
                break;
            case 4:
                drawMap(humidityMap,MapType.HUMIDITY);
                break;
            case 5:
                drawMap(humidityMap,MapType.BIOME);
                break;
        }
    }
    public  void generate(){


        try {


            map =generator.generateHexMap(size,size);

            Thread.sleep(100);

            draw();

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

    int markR=-1;
    int markQ=-1;
    @Override
    public void mouseClicked(MouseEvent e) {
        double x =e.getX()-w*W_OFFSET;
        double y = e.getY()-h*H_OFFSET;
      //  logger.debug(x+" "+y);
        int r= (int) Math.round(y*4/3/h);
        int q;
        if(r%2==0){
             q =(int) Math.round(x/w-0.5);
        }else{
             q =(int) Math.round(x/w-1);
        }
        markR = r;
        markQ = q;
     //   logger.debug(r+" "+q);
        showInfo(map.getPoint(q,r));
        draw();
    }
    public void showInfo(MapPoint point){
        JFrame frame = new JFrame(point.toString());
        JPanel panel =new JPanel();
        panel.add(new Label("X:" + point.getX()+" Y:"+point.getY()+" height:"+point.getHeight()));
        panel.add(new Label("Height type " +point.getHeightType()));
        panel.add(new Label("Land type "+point.getLandType()));
        panel.add(new Label("Temperature type "+point.getTemperatureType()));
        panel.add(new Label("Temperature "+point.getTemperature()));
        panel.add(new Label("Humidity type "+point.getHumidityType()));
        if(point.getBiome()!=null) {
            panel.add(new Label("Biome  " + point.getBiome().toString()));
        }
        frame.setSize(200,400);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
