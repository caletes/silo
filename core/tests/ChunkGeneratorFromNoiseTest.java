import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.caletes.game.generators.WorldGeneratorFromNoise;
import org.junit.Test;

import java.util.Random;

public class ChunkGeneratorFromNoiseTest extends GdxTest {

    private static Random random = new Random();

    @Test
    public void testGenerateL() {
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(5096, 5096, 0);
        generator.setDebug(true);
        generator.generate();
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_l.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_l.png"), biomeMap);
    }

    @Test
    public void testGenerateM() {
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256, 256, 0);
        generator.setDebug(true);
        generator.generate();
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_m.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_m.png"), biomeMap);
    }

    @Test
    public void testGenerateXS() {
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(8, 8, 0);
        generator.setDebug(true);
        generator.generate();
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_xs.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_xs.png"), biomeMap);
    }
}
