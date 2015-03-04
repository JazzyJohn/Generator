package nstuff.world.algoritm.temperature;

import nstuff.world.entity.Map;

/**
 * Created by Ivan.Ochincenko on 25.02.15.
 */
public interface TemperatureCalculator {

    void init();

    void calculateForMap(Map map);
}
