import com.badlogic.gdx.math.Vector2;
import com.caletes.game.IsoConverter;
import junit.framework.TestCase;
import org.junit.Test;

public class IsoConverterTest extends TestCase {

    @Test
    public void testConversion() {
        IsoConverter converter = new IsoConverter(128, 64);
        Vector2 sPosition = converter.toScreen(4, 3, 0);
        assertEquals(64, (int) sPosition.x);
        assertEquals(-224, (int) sPosition.y);
        Vector2 wPosition = converter.toWorld(64, -224);
        assertEquals(4, (int) wPosition.x);
        assertEquals(3, (int) wPosition.y);
    }

    @Test
    public void testdoubleConversion3D() {
        doubleConversion3D(2, 5, 0);
        doubleConversion3D(3, 4, 0);
        doubleConversion3D(2, 1, 0);
        doubleConversion3D(7, 4, 0);
    }

    private void doubleConversion3D(int x, int y, int z) {
        IsoConverter converter = new IsoConverter(128, 64);
        Vector2 position2D = converter.toScreen(x, y, z);
        Vector2 position3D = converter.toWorld(position2D.x, position2D.y);
        assertEquals(x, (int) position3D.x);
        assertEquals(y, (int) position3D.y);
    }

}
