import com.google.inject.Guice;
import com.google.inject.Injector;
import nstuff.gameflow.GameFlow;
import nstuff.world.GeneratorModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class Main {


    static Logger logger = LogManager.getLogger(Main.class);



    public static void main(String[] args) {
        Injector injector =        Guice.createInjector(new GeneratorModule());
        Drawer drawer = new Drawer(injector);
        drawer.generate();
        GameFlow flow = injector.getInstance(GameFlow.class);
        flow.init(drawer.getMap());

    }
}
