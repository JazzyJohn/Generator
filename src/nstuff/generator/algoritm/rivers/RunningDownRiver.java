package nstuff.generator.algoritm.rivers;

import nstuff.generator.entity.Map;
import nstuff.generator.settings.SettingManager;

import javax.inject.Inject;

/**
 * Created by vania_000 on 23.02.2015.
 */
public class RunningDownRiver implements RiverGenerator {

    public int riverCnt;

    public int riverDistance;

    @Inject
    SettingManager settings;


    @Override
    public void init() {
        riverCnt = settings.getIslandSetting("riverCnt",30,Integer.class);
        riverDistance  = 
    }

    @Override
    public void generate(Map map) {

    }
}
