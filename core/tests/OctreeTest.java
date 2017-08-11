import com.caletes.game.octree.Octree;
import junit.framework.TestCase;
import org.junit.Test;

public class OctreeTest extends TestCase {
    @Test
    public void testComputeExponent() {
        Octree octree = new Octree(8);
        assertEquals(3, octree.getExponent());
        assertEquals(8, octree.getSize());

        Octree octree2 = new Octree(256);
        assertEquals(8, octree2.getExponent());
        assertEquals(256, octree2.getSize());
    }

    @Test
    public void testSizeNotPowOf2ThrowException() {
        try {
            new Octree(5);
        } catch (IllegalArgumentException e) {
            assertEquals("Octree size must be a power of 2", e.getMessage());
        }
    }

    @Test
    public void testPositionOutOfBoundsThrowException() {
        Octree octree = new Octree(8);
        try {
            octree.getIndex(0, 0, 8);
            fail();
        } catch (Octree.PositionOutOfBounds e) {
            assertEquals("point(0,0,8) is out of bounds", e.getMessage());
        }

    }

    @Test
    public void testGetIndex() throws Octree.PositionOutOfBounds {
        Octree octree = new Octree(256);
        assertEquals(0, octree.getIndex(0, 0, 0));
        assertEquals(7, octree.getIndex(255, 255, 255));
    }
}
