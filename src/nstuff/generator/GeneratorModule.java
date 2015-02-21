package nstuff.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import nstuff.generator.algoritm.heightmap.DiamondSquareDisplacement;
import nstuff.generator.algoritm.heightmap.HeightMapGenerator;
import nstuff.generator.algoritm.heightmap.MiddlePointDisplacement;
import nstuff.generator.settings.SettingManager;

import java.util.AbstractMap;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class GeneratorModule  extends AbstractModule{
    @Override
    protected void configure() {
        bind(SettingManager.class).in(Singleton.class);
        bind(HeightMapGenerator.class).to(DiamondSquareDisplacement.class).in(Singleton.class);
        bind(Generator.class).in(Singleton.class);
    }
}
