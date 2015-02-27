package nstuff.generator.algoritm.humidity;

import nstuff.generator.entity.Map;

/**
 * Created by Ivan.Ochincenko on 27.02.15.
 */
public interface HumidityCalculator {

    void init();

    void calculate(Map map);
}
