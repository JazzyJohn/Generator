package nstuff.world.logic.island;

        import nstuff.world.entity.MapPoint;
        import nstuff.world.logic.LogicChanger;
        import nstuff.world.settings.SettingManager;

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
    public boolean changePoint(MapPoint point) {
        point.makeLand();
        point.setHeight(landLvl);
        return true;
    }
}
