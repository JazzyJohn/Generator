package nstuff.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import nstuff.generator.algoritm.grower.CircleGrower;
import nstuff.generator.algoritm.grower.Grower;
import nstuff.generator.algoritm.heightmap.*;
import nstuff.generator.algoritm.rivers.RiverGenerator;
import nstuff.generator.algoritm.rivers.RunningDownRiver;
import nstuff.generator.logic.LogicChanger;
import nstuff.generator.logic.LogicFinder;
import nstuff.generator.logic.island.LandMaker;
import nstuff.generator.logic.water.WaterFinder;
import nstuff.generator.settings.SettingManager;

import java.util.AbstractMap;
import java.util.Random;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class GeneratorModule  extends AbstractModule{
    @Override
    protected void configure() {
        bind(SettingManager.class).in(Singleton.class);
        bind(HeightMapGenerator.class).to(DiamondSquareDisplacement.class).in(Singleton.class);
        bind(HeightNormalizer.class).to(HeightNormalizerImpl.class).in(Singleton.class);
        bind(RiverGenerator.class).to(RunningDownRiver.class).in(Singleton.class);
        bind(Grower.class).annotatedWith(Names.named("IslandGrower")).to(CircleGrower.class).in(Singleton.class);
        bind(LogicChanger.class).annotatedWith(Names.named("IslandMaker")).to(LandMaker.class).in(Singleton.class);
        bind(LogicFinder.class).annotatedWith(Names.named("WaterFinder")).to(WaterFinder.class).in(Singleton.class);
        bind(Random.class).in(Singleton.class);
        bind(Generator.class).in(Singleton.class);
    }
}
