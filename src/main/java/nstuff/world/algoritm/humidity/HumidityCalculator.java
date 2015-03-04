package nstuff.world.algoritm.humidity;

import nstuff.world.entity.Map;

/**
 * Created by Ivan.Ochincenko on 27.02.15.
 */
public interface HumidityCalculator {

    void init();

    void calculate(Map map);
}
