package nstuff.generator.entity.biom;

import nstuff.generator.entity.AbstractRegion;
import nstuff.generator.entity.MapPoint;
import nstuff.generator.geography.ProtoBiome;

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

    @Override
    public String toString() {
        return protoBiome.toString();
    }
}
