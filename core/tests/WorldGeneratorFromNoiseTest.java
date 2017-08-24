import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.caletes.game.WorldGeneratorFromNoise;
import org.junit.Test;

public class WorldGeneratorFromNoiseTest extends GdxTest{

    @Test
    public void testGenerateElevations(){
        WorldGeneratorFromNoise generator = new WorldGeneratorFromNoise(256,256);
        Pixmap pixmap = generator.toPixmap();
        PixmapIO.writePNG(new FileHandle("assets/heightmaps/_generated.png"), pixmap);
    }
}
