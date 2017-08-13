import com.caletes.game.octree.Octree;
import junit.framework.TestCase;
import org.junit.Test;

public class OctreeTest extends TestCase {
    @Test
    public void testComputeExponent() {
        Octree<String> octree = new Octree<>(8);
        assertEquals(3, octree.getExponent());
        assertEquals(8, octree.getSize());

        Octree<String> octree2 = new Octree<>(256);
        assertEquals(8, octree2.getExponent());
        assertEquals(256, octree2.getSize());
    }

    @Test
    public void testSizeNotPowOf2ThrowException() {
        try {
            new Octree<>(5);
        } catch (IllegalArgumentException e) {
            assertEquals("Octree size (5) is not a power of 2", e.getMessage());
        }
    }

    @Test
    public void testGetIndex() {
        Octree<String> octree = new Octree<>(256);
        assertEquals(0, octree.getIndex(0, 0, 0));
        assertEquals(7, octree.getIndex(255, 255, 255));
    }

    @Test
    public void testPushAndGetObjects() {
        Octree<String> octree = new Octree<>(1024);
        octree.pushObjectAt("Item 1", 1, 3, 1);
        octree.pushObjectAt("Item 2", 4, 5, 7);
        octree.pushObjectAt("Item 3", 7, 7, 7);
        octree.pushObjectAt("Item 4", 4, 5, 5);
        octree.pushObjectAt("Item 5", 1023, 1023, 1023);

        assertEquals("Item 1", octree.getObjectAt(1, 3, 1));
        assertEquals("Item 2", octree.getObjectAt(4, 5, 7));
        assertEquals("Item 3", octree.getObjectAt(7, 7, 7));
        assertEquals("Item 4", octree.getObjectAt(4, 5, 5));
        assertEquals("Item 5", octree.getObjectAt(1023, 1023, 1023));
    }

    @Test
    public void testOneMillionObjects() {
        Octree<String> octree = new Octree(1024);
        int z = 1;
        for (int x = 0; x <= 1024; x++) {
            for (int y = 0; y <= 1024; y++) {
                octree.pushObjectAt("Item " + x + "," + y + "," + z, x, y, z);
            }
        }
        long startTime = System.currentTimeMillis();
        assertEquals("Item 1,3,1", octree.getObjectAt(1, 3, 1));
        assertEquals("Item 7,7,1", octree.getObjectAt(7, 7, 1));
        assertEquals("Item 1023,1023,1", octree.getObjectAt(1023, 1023, 1));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
    }

    @Test
    public void testPopObjectAt() {
        Octree<String> octree = new Octree(1024);
        octree.pushObjectAt("Item 1", 567, 52, 345);
        assertEquals("Item 1", octree.getObjectAt(567, 52, 345));
        assertEquals("Item 1", octree.popObjectAt(567, 52, 345));
        assertNull(octree.getObjectAt(567, 52, 345));
        assertNull(octree.popObjectAt(567, 52, 345));
    }
}
