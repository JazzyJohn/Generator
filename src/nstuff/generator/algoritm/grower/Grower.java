package nstuff.generator.algoritm.grower;

import nstuff.generator.entity.Map;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.logic.LogicChanger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by vania_000 on 22.02.2015.
 */
public interface Grower {
    static Logger logger = LogManager.getLogger(Grower.class);

    public void setUp(int size, int startX, int startY);

    public void grow(Map map,LogicChanger changer);

    void setUp(int size, MapPoint center);
}
