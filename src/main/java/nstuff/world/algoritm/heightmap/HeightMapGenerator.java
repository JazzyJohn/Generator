package nstuff.world.algoritm.heightmap;

import nstuff.world.entity.Map;

/**
 * Created by vania_000 on 21.02.2015.
 */
public interface HeightMapGenerator {

    void generate(Map map) throws HeightMapException;
}
