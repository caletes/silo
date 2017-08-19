import com.caletes.game.IsoConverter;
import junit.framework.TestCase;
import org.junit.Test;

public class IsoConverterTest extends TestCase {

    @Test
    public void testConversion() {
        int[] position2D = IsoConverter.toScreen(4, 3, 0);
        assertEquals(64, position2D[0]);
        assertEquals(-224, position2D[1]);
        int[] position3D = IsoConverter.toWorld(64, -224);
        assertEquals(4, position3D[0]);
        assertEquals(3, position3D[1]);
    }

    @Test
    public void testdoubleConversion3D() {
        doubleConversion3D(2, 5, 0);
        doubleConversion3D(3, 4, 0);
        doubleConversion3D(2, 1, 0);
        doubleConversion3D(7, 4, 0);
    }

    private void doubleConversion3D(int x, int y, int z) {
        int[] position2D = IsoConverter.toScreen(x, y, z);
        int[] position3D = IsoConverter.toWorld(position2D[0], position2D[1]);
        assertEquals(x, position3D[0]);
        assertEquals(y, position3D[1]);
    }

}
