package nstuff.world.logic.generators;

import nstuff.world.entity.Map;
import nstuff.world.settings.SettingException;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public interface ProtoBiomeGenerator {

    void init() throws SettingException;

    void generate(Map map);
}
