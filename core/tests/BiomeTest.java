import com.caletes.game.Biome;
import junit.framework.TestCase;
import org.junit.Test;

public class BiomeTest  extends TestCase{
    @Test
    public void testFindBiome(){
        assertEquals(Biome.OCEAN, Biome.find(0.0));
        assertEquals(Biome.OCEAN, Biome.find(0.1));
        assertEquals(Biome.BEACH, Biome.find(0.11));
        assertEquals(Biome.BEACH, Biome.find(0.13));
        assertEquals(Biome.GRASSLAND, Biome.find(0.14));
        assertEquals(Biome.GRASSLAND, Biome.find(0.3));
        assertEquals(Biome.STONE, Biome.find(0.31));
        assertEquals(Biome.STONE, Biome.find(0.50));
        assertEquals(Biome.SNOW, Biome.find(0.51));
        assertEquals(Biome.SNOW, Biome.find(1));
    }
}
