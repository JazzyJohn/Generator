import com.google.inject.Guice;
import com.google.inject.Injector;
import nstuff.generator.Generator;
import nstuff.generator.GeneratorException;
import nstuff.generator.GeneratorModule;
import nstuff.generator.entity.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class Main {


    static Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args){
        Injector injector = Guice.createInjector(new GeneratorModule());
        Generator generator =injector.getInstance(Generator.class);


        try {
            generator.init();
            Map map =generator.generate(65,65);
            float h = 24;
            float w = 24;
            BufferedImage image = new BufferedImage(Math.round(map.getWidth()*w),Math.round(map.getHeight()*h*3/4),BufferedImage.TYPE_INT_RGB);
            Graphics2D ig2 = image.createGraphics();

            for(int i=1; i<map.getWidth();i++){
                for(int j=1;j<map.getHeight();j++){
                    int height =map.getPoint(i,j).getHeight();
                    Color color;
                    if(height>200){
                        color = new Color(250, 255, 255);
                    }else if( height>170){
                        color = new Color(152, 72, 0);
                    }else if( height>150){
                        color = new Color(0, 152, 41);
                    }else if( height>120){
                        color = new Color(15, 152, 126);
                    }else if( height>100){
                        color = new Color(13, 126, 152);
                    }else if( height>70){
                        color = new Color(1, 27, 152);
                    }else{
                        color = new Color(32, 28, 152);
                    }
                    ig2.setPaint(color);
                    if(j%2==0){
                     //   ig2.setPaint(Color.white);
                        ig2.fill(makeHex(i*w,((float)j)*h*3/4,w,h));

                    }else{
                       // ig2.setPaint(Color.yellow);
                        ig2.fill(makeHex(i*w-w/2,((float)j)*h*3/4   ,w,h));
                    }
                }
               // break;
            }
            File outputfile = new File("saved.png");
            ImageIO.write(image, "png", outputfile);
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
}
