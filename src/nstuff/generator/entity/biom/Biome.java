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

    public ProtoBiome protoBiome;

    public List<Modification> modifications = new ArrayList<Modification>();

    public Biome(ProtoBiome protoBiome, MapPoint center){
        this.protoBiome = protoBiome;
        points.add(center);
    }

    public void add(Modification mod){
        modifications.add(mod);
    }

}
