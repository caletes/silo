import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.caletes.game.HeightMap;
import junit.framework.TestCase;
import org.junit.Test;

public class HeightMapTest extends TestCase {

    @Test
    public void testLoad() {
        GdxNativesLoader.load();
        HeightMap heightMap = new HeightMap(new FileHandle("assets/heightmap2.jpg"), 4);
        assertEquals(0, heightMap.getElevation(0, 0));
        assertEquals(4, heightMap.getElevation(1, 0));
        assertEquals(0, heightMap.getElevation(2, 0));
        assertEquals(4, heightMap.getElevation(0, 1));
        assertEquals(3, heightMap.getElevation(1, 1));
        assertEquals(2, heightMap.getElevation(2, 1));
        assertEquals(1, heightMap.getElevation(3, 1));
        assertEquals(0, heightMap.getElevation(4, 1));
    }

    @Test
    public void testLoad2() {
        GdxNativesLoader.load();
        HeightMap heightMap = new HeightMap(new FileHandle("assets/heightmap2.jpg"), 8);
        int width = heightMap.getWidth();
        for (int x = 0; x < width; x++) {
            System.out.println(heightMap.getElevation(x, 1));
        }

    }
}
