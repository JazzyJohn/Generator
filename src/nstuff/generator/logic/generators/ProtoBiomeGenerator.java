package nstuff.generator.logic.generators;

import nstuff.generator.entity.Map;
import nstuff.generator.settings.SettingException;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public interface ProtoBiomeGenerator {

    void init() throws SettingException;

    void generate(Map map);
}
