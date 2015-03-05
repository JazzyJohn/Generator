package nstuff.world.entity.biom;

import nstuff.world.entity.AbstractRegion;
import nstuff.world.geography.ProtoBiome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public class Biome  extends AbstractRegion {

    private ProtoBiome protoBiome;

    private List<Modification> modifications = new ArrayList<Modification>();

    public Biome(ProtoBiome protoBiome){
        this.protoBiome = protoBiome;

    }

    public void add(Modification mod){
        modifications.add(mod);
    }

    public ProtoBiome getProtoBiome() {
        return protoBiome;
    }

    @Override
    public String toString() {
        return protoBiome.toString();
    }

    public String getName() {
        return protoBiome.getName();

    }
}
