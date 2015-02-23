package nstuff.generator.logic.island;

        import nstuff.generator.entity.MapPoint;
        import nstuff.generator.logic.LogicChanger;
        import nstuff.generator.settings.SettingManager;

        import javax.inject.Inject;

/**
 * Created by vania_000 on 22.02.2015.
 */
public class LandMaker implements LogicChanger {
    @Inject
    SettingManager settings;

    int landLvl;
    @Override
    public void init() {
        landLvl =settings.getHeightMapSetting("landlvl",0,Integer.class);
    }

    @Override
    public void changePoint(MapPoint point) {
        point.makeLand();
        point.setHeight(landLvl);
    }
}
