import com.caletes.game.Biome;
import junit.framework.TestCase;
import org.junit.Test;

public class BiomeTest  extends TestCase{
    @Test
    public void testFindBiome(){
        assertEquals(Biome.OCEAN, Biome.find(0.0));
        assertEquals(Biome.OCEAN, Biome.find(0.1));
        assertEquals(Biome.BEACH, Biome.find(0.11));
        assertEquals(Biome.BEACH, Biome.find(0.15));
        assertEquals(Biome.GRASSLAND, Biome.find(0.16));
        assertEquals(Biome.GRASSLAND, Biome.find(0.4));
        assertEquals(Biome.STONE, Biome.find(0.41));
        assertEquals(Biome.STONE, Biome.find(0.60));
        assertEquals(Biome.SNOW, Biome.find(0.61));
        assertEquals(Biome.SNOW, Biome.find(1));
    }
}
