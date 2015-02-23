package nstuff.generator.algoritm.heightmap;

import nstuff.generator.entity.Map;

/**
 * Created by vania_000 on 23.02.2015.
 */
public interface HeightNormalizer {
    void init();

    void normalize(Map map);
}
