import com.caletes.game.octree.Item;
import com.caletes.game.octree.Octree;
import junit.framework.TestCase;
import org.junit.Test;

public class OctreeTest extends TestCase {
    @Test
    public void testComputeExponent() {
        Octree octree = Octree.create(8);
        assertEquals(3, octree.getExponent());
        assertEquals(8, octree.getSize());

        Octree octree2 = Octree.create(256);
        assertEquals(8, octree2.getExponent());
        assertEquals(256, octree2.getSize());
    }

    @Test
    public void testSizeNotPowOf2ThrowException() {
        try {
            Octree.create(5);
        } catch (IllegalArgumentException e) {
            assertEquals("Octree size (5) is not a power of 2", e.getMessage());
        }
    }

    @Test
    public void testGetIndex() {
        Octree octree = Octree.create(256);
        assertEquals(0, octree.getIndex(0, 0, 0));
        assertEquals(7, octree.getIndex(255, 255, 255));
    }

    @Test
    public void testAddObject() {
        Octree octree = Octree.create(8);
        Item item = new Item("Item 1", 4, 5, 7);
        octree.addObject(item);
        assertEquals(1, octree.getObjects().size());

    }

    @Test
    public void testSplit() {
        Octree octree = Octree.create(1024, 1);
        Item item1 = new Item("Item 1", 1, 3, 1);
        Item item2 = new Item("Item 2", 4, 5, 7);
        Item item3 = new Item("Item 3", 7, 7, 7);
        Item item4 = new Item("Item 4", 4, 5, 5);
        Item item5 = new Item("Item 5", 1023, 1023, 1023);
        octree.addObject(item1);
        octree.addObject(item2);
        octree.addObject(item3);
        octree.addObject(item4);
        octree.addObject(item5);
        assertEquals(0, octree.getObjects().size());
        assertEquals(1, octree.findObjectsNear(1, 3, 1).size());
        assertEquals(1, octree.findObjectsNear(4, 5, 7).size());
        assertEquals(1, octree.findObjectsNear(4, 5, 5).size());
        assertEquals(1, octree.findObjectsNear(7, 7, 7).size());
        assertEquals(1, octree.findObjectsNear(1023, 1023, 1023).size());
        assertEquals(0, octree.findObjectsNear(500, 500, 500).size());

        assertEquals("Item 1", ((Item)octree.findObjectsNear(1, 3, 1).get(0)).getName());
        assertEquals("Item 3", ((Item)octree.findObjectsNear(7, 7, 7).get(0)).getName());
        assertEquals("Item 5", ((Item)octree.findObjectsNear(1023, 1023, 1023).get(0)).getName());
    }
}
