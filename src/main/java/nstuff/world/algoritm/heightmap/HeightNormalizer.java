package nstuff.world.algoritm.heightmap;

import nstuff.world.entity.Map;

/**
 * Created by vania_000 on 23.02.2015.
 */
public interface HeightNormalizer {
    void init();

    void normalize(Map map);
}
