package nstuff.world;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import nstuff.gameflow.GameFlow;
import nstuff.managers.ContentManager;
import nstuff.managers.ContentManagerInMemoryImpl;
import nstuff.world.algoritm.grower.CircleGrower;
import nstuff.world.algoritm.grower.Grower;
import nstuff.world.algoritm.grower.SpiralGrower;
import nstuff.world.algoritm.heightmap.*;
import nstuff.world.algoritm.humidity.HumidityCalculator;
import nstuff.world.algoritm.humidity.SimpleHumidityCalculator;
import nstuff.world.algoritm.rivers.RiverGenerator;
import nstuff.world.algoritm.rivers.RunningDownRiver;
import nstuff.world.algoritm.temperature.SimpleTemperatureImpl;
import nstuff.world.algoritm.temperature.TemperatureCalculator;
import nstuff.world.logic.LogicChanger;
import nstuff.world.logic.LogicFinder;
import nstuff.world.logic.generators.ProtoBiomeGenerator;
import nstuff.world.logic.generators.SimpleGrowerBiome;
import nstuff.world.logic.island.LandMaker;
import nstuff.world.logic.water.OceanFinder;
import nstuff.world.logic.water.WaterFinder;
import nstuff.world.settings.SettingManager;

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
        bind(TemperatureCalculator.class).to(SimpleTemperatureImpl.class).in(Singleton.class);
        bind(HumidityCalculator.class).to(SimpleHumidityCalculator.class).in(Singleton.class);
        bind(ProtoBiomeGenerator.class).to(SimpleGrowerBiome.class).in(Singleton.class);
        bind(RiverGenerator.class).to(RunningDownRiver.class).in(Singleton.class);
        bind(Grower.class).annotatedWith(Names.named("IslandGrower")).to(CircleGrower.class).in(Singleton.class);
        bind(Grower.class).annotatedWith(Names.named("BiomeGrower")).to(SpiralGrower.class).in(Singleton.class);
        bind(LogicChanger.class).annotatedWith(Names.named("IslandMaker")).to(LandMaker.class).in(Singleton.class);
        bind(LogicFinder.class).annotatedWith(Names.named("OceanFinder")).to(OceanFinder.class).in(Singleton.class);
        bind(LogicFinder.class).annotatedWith(Names.named("WaterFinder")).to(WaterFinder.class).in(Singleton.class);
        bind(Random.class).in(Singleton.class);
        bind(Generator.class).in(Singleton.class);
        bind(GameFlow.class).in(Singleton.class);
        bind(ContentManager.class).to(ContentManagerInMemoryImpl.class).in(Singleton.class);
    }
}
