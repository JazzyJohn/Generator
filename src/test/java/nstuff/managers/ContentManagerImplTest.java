package nstuff.managers;

import nstuff.world.logic.generators.SimpleGrowerBiome;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public class ContentManagerImplTest {

    @Test
    public void initTest() throws Exception {
        ContentManagerInMemoryImpl biom = new ContentManagerInMemoryImpl();
        biom.init();

    }
}
