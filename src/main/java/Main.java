import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
