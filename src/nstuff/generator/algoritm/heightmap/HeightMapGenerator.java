package nstuff.generator.algoritm.heightmap;

import nstuff.generator.entity.HexMap;
import nstuff.generator.entity.Map;

/**
 * Created by vania_000 on 21.02.2015.
 */
public interface HeightMapGenerator {

    void generate(Map map) throws HeightMapException;
}
