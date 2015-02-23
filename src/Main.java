import com.google.inject.Guice;
import com.google.inject.Injector;
import nstuff.generator.Generator;
import nstuff.generator.GeneratorModule;
import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.settings.SettingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class Main {


    static Logger logger = LogManager.getLogger(Main.class);



    public static void main(String[] args) {
        Drawer drawer = new Drawer();
        drawer.generate();
    }
}
