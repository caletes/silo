import com.caletes.game.Biome;
import junit.framework.TestCase;
import org.junit.Test;

public class BiomeTest extends TestCase {
    @Test
    public void testFindBiome() {
        assertEquals(Biome.OCEAN, Biome.find(0.00));
        assertEquals(Biome.OCEAN, Biome.find(0.20));
        assertEquals(Biome.BEACH, Biome.find(0.21));
        assertEquals(Biome.BEACH, Biome.find(0.23));
        assertEquals(Biome.GRASSLAND, Biome.find(0.24));
        assertEquals(Biome.GRASSLAND, Biome.find(0.50));
        assertEquals(Biome.STONE, Biome.find(0.51));
        assertEquals(Biome.STONE, Biome.find(0.80));
        assertEquals(Biome.SNOW, Biome.find(0.81));
        assertEquals(Biome.SNOW, Biome.find(1.00));
    }
}
