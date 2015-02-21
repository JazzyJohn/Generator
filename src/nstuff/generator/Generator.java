package nstuff.generator;

import nstuff.generator.algoritm.heightmap.HeightMapException;
import nstuff.generator.algoritm.heightmap.HeightMapGenerator;
import nstuff.generator.entity.Map;
import nstuff.generator.settings.SettingException;
import nstuff.generator.settings.SettingManager;

import javax.inject.Inject;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class Generator {

    @Inject
    private HeightMapGenerator heightMapGenerator;

    @Inject
    private SettingManager settingManager;

    public Map generate(int x,int y) throws GeneratorException {
        Map map = new Map(x,y);
        heightMapGenerator.generate(map);
        return  map;
    }

    public void init() throws GeneratorException {
         settingManager.init();
    }
}
