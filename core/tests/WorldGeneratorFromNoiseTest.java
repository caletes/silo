import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.caletes.game.WorldGeneratorFromNoise;
import org.junit.Test;

import java.util.Random;

public class WorldGeneratorFromNoiseTest extends GdxTest {

    private static Random random = new Random();

    @Test
    public void testGenerateM() {
        long seed = random.nextLong();
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256, 256, seed, true);
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_m.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_m.png"), biomeMap);
    }

    @Test
    public void testGenerateXS() {

        long seed = random.nextLong();
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(8, 8, seed, true);
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_xs.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_xs.png"), biomeMap);
    }
}
