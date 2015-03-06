package nstuff.gameflow;

import com.google.inject.Inject;
import nstuff.managers.ContentManager;
import nstuff.world.entity.Map;
import nstuff.world.settings.SettingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan.Ochincenko on 06.03.15.
 */
public class GameFlow   implements Runnable {

    static Logger logger = LogManager.getLogger(GameFlow.class);

    private volatile GameState state = GameState.RUNNING;

    @Inject
    private ContentManager contentManager;

    public void setState(GameState state) {
        this.state = state;

    }
    private Map map;
    public void init(Map map){
        this.map = map;
        try {
            contentManager.init();
        } catch (SettingException e) {
            logger.error(e);
        }
    }

    @Override
    public void run() {
        while (state!=GameState.END){
            try{

            if(state==GameState.PAUSE){
                while(state==GameState.PAUSE){
                    Thread.currentThread().wait(1000);
                }
            }else{
                Thread.currentThread().wait(1000);
            }
            }catch (InterruptedException e){
                logger.error(e);
            }
        }
    }
}
