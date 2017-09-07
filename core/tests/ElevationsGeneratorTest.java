import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.caletes.game.generators.ElevationsGenerator;
import org.junit.Test;

public class ElevationsGeneratorTest extends GdxTest {

    //@Test
    public void testGenerateL() {
        ElevationsGenerator generator = new ElevationsGenerator(5096, 0);
        generator.setDebug(true);
        generator.generate();
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_l.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_l.png"), biomeMap);
    }

    //@Test
    public void testGenerateM() {
        ElevationsGenerator generator = new ElevationsGenerator(256, 0);
        generator.setDebug(true);
        generator.generate();
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_m.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_m.png"), biomeMap);
    }

    @Test
    public void testGenerateXS() {
        ElevationsGenerator generator = new ElevationsGenerator(8, 0);
        generator.setDebug(true);
        generator.generate();
        Pixmap heightmap = generator.toHeightMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_heightmap_xs.png"), heightmap);
        Pixmap biomeMap = generator.toBiomeMap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated_biomemap_xs.png"), biomeMap);
    }
}
