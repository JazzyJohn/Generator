package nstuff.world.logic.generators;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public class SimpleGrowerBiomeTest {


    public void initTest() throws Exception {
        SimpleGrowerBiome biom = new SimpleGrowerBiome();
        biom.init();
        assertThat(biom.allBiomes.size(),equalTo(2));
        assertThat(biom.maxBiomeSize,equalTo(8));
    }
}
