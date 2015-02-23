package nstuff.generator.algoritm.grower;

import nstuff.generator.entity.Map;
import nstuff.generator.logic.LogicChanger;

/**
 * Created by vania_000 on 22.02.2015.
 */
public interface Grower {

    public void setUp(int size, int startX, int startY);

    public void grow(Map map,LogicChanger changer);
}
