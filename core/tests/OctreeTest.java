import com.caletes.game.octree.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Iterator;

public class OctreeTest extends TestCase {
    @Test
    public void testComputeExponent() {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 8);
        assertEquals(3, octree.getExponent());
        assertEquals(8, octree.getSize());

        Node<String> octree2 = OctreeFactory.createWithSize(String.class, 256);
        assertEquals(8, octree2.getExponent());
        assertEquals(256, octree2.getSize());
    }

    @Test
    public void testSizeNotPowOf2ThrowException() {
        try {
            OctreeFactory.createWithSize(String.class, 5);
        } catch (IllegalArgumentException e) {
            assertEquals("Octree size (5) is not a power of 2", e.getMessage());
        }
    }

    @Test
    public void testPushAndGetObjects() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 1024);
        octree.setObjectAt("Item 1", 1, 3, 1);
        octree.setObjectAt("Item 2", 4, 5, 7);
        octree.setObjectAt("Item 3", 7, 7, 7);
        octree.setObjectAt("Item 4", 4, 5, 5);
        octree.setObjectAt("Item 5", 1023, 1023, 1023);

        assertEquals("Item 1", octree.getObjectAt(1, 3, 1));
        assertEquals("Item 2", octree.getObjectAt(4, 5, 7));
        assertEquals("Item 3", octree.getObjectAt(7, 7, 7));
        assertEquals("Item 4", octree.getObjectAt(4, 5, 5));
        assertEquals("Item 5", octree.getObjectAt(1023, 1023, 1023));
    }

    @Test
    public void testOneMillionObjects() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 1024);
        int z = 1;
        for (int x = 0; x < 1024; x++) {
            for (int y = 0; y < 1024; y++) {
                octree.setObjectAt("Item " + x + "," + y + "," + z, x, y, z);
            }
        }

        assertEquals("Item 1,3,1", octree.getObjectAt(1, 3, 1));
        assertEquals("Item 7,7,1", octree.getObjectAt(7, 7, 1));
        assertEquals("Item 1023,1023,1", octree.getObjectAt(1023, 1023, 1));
    }

    @Test
    public void testPopObjectAt() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 1024);
        octree.setObjectAt("Item 1", 567, 52, 345);
        assertEquals("Item 1", octree.getObjectAt(567, 52, 345));
        assertEquals("Item 1", octree.clearObjectAt(567, 52, 345));
        assertNull(octree.getObjectAt(567, 52, 345));
        assertNull(octree.clearObjectAt(567, 52, 345));
    }

    @Test
    public void testIterator() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 128);
        octree.setObjectAt("Item 1", 0, 0, 0);
        octree.setObjectAt("Item 2", 0, 1, 0);
        octree.setObjectAt("Item 3", 0, 0, 1);
        octree.setObjectAt("Item 4", 1, 1, 1);
        octree.setObjectAt("Item 5", 127, 127, 127);
        Iterator<Node> it = octree.iterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.isFinalLeaf()) {
                System.out.println(node.getObject());
            }
        }
    }

    @Test
    public void testIteratorOrder() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 2);
        octree.setObjectAt("Item 0", 0, 0, 0);
        octree.setObjectAt("Item 1", 0, 0, 1);
        octree.setObjectAt("Item 2", 0, 1, 0);
        octree.setObjectAt("Item 3", 0, 1, 1);
        octree.setObjectAt("Item 4", 1, 0, 0);
        octree.setObjectAt("Item 5", 1, 0, 1);
        octree.setObjectAt("Item 6", 1, 1, 0);
        octree.setObjectAt("Item 7", 1, 1, 1);
        Iterator<Node> it = octree.iterator();
        int i = 0;
        while (it.hasNext()) {
            assertEquals("Item " + i, it.next().getObject());
            i++;
        }
    }

    @Test
    public void testGetPosition() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 256);
        octree.setObjectAt("Item 0", 0, 0, 0);
        octree.setObjectAt("Item 1", 124, 12, 235);
        NodeIterator it = octree.iterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.isFinalLeaf()) {
                MortonCode.Vector3 position = node.getPosition();
                System.out.println(position);
                assertEquals(it.getPosition(), position);
            }
        }
    }


    @Test
    public void testFindNeightboors() throws OctreeOutOfBoundsException {
        Node<String> octree = OctreeFactory.createWithSize(String.class, 4);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    octree.setObjectAt("Item " + x + y + z, x, y, z);
                }
            }
        }
        Node start = octree.getLeafAt(2, 2, 2);
        assertEquals("Item 111", start.getNextOn(Direction.BOTTOM_NORTH_WEST).getObject());
        assertEquals("Item 211", start.getNextOn(Direction.BOTTOM_NORTH).getObject());
        assertEquals("Item 311", start.getNextOn(Direction.BOTTOM_NORTH_EAST).getObject());
        assertEquals("Item 121", start.getNextOn(Direction.BOTTOM_WEST).getObject());
        assertEquals("Item 221", start.getNextOn(Direction.BOTTOM).getObject());
        assertEquals("Item 321", start.getNextOn(Direction.BOTTOM_EAST).getObject());
        assertEquals("Item 131", start.getNextOn(Direction.BOTTOM_SOUTH_WEST).getObject());
        assertEquals("Item 231", start.getNextOn(Direction.BOTTOM_SOUTH).getObject());
        assertEquals("Item 331", start.getNextOn(Direction.BOTTOM_SOUTH_EAST).getObject());
        assertEquals("Item 112", start.getNextOn(Direction.NORTH_WEST).getObject());
        assertEquals("Item 212", start.getNextOn(Direction.NORTH).getObject());
        assertEquals("Item 312", start.getNextOn(Direction.NORTH_EAST).getObject());
        assertEquals("Item 122", start.getNextOn(Direction.WEST).getObject());
        assertEquals("Item 222", start.getNextOn(Direction.NONE).getObject());
        assertEquals("Item 322", start.getNextOn(Direction.EAST).getObject());
        assertEquals("Item 132", start.getNextOn(Direction.SOUTH_WEST).getObject());
        assertEquals("Item 232", start.getNextOn(Direction.SOUTH).getObject());
        assertEquals("Item 332", start.getNextOn(Direction.SOUTH_EAST).getObject());
        assertEquals("Item 113", start.getNextOn(Direction.TOP_NORTH_WEST).getObject());
        assertEquals("Item 213", start.getNextOn(Direction.TOP_NORTH).getObject());
        assertEquals("Item 313", start.getNextOn(Direction.TOP_NORTH_EAST).getObject());
        assertEquals("Item 123", start.getNextOn(Direction.TOP_WEST).getObject());
        assertEquals("Item 223", start.getNextOn(Direction.TOP).getObject());
        assertEquals("Item 323", start.getNextOn(Direction.TOP_EAST).getObject());
        assertEquals("Item 133", start.getNextOn(Direction.TOP_SOUTH_WEST).getObject());
        assertEquals("Item 233", start.getNextOn(Direction.TOP_SOUTH).getObject());
        assertEquals("Item 333", start.getNextOn(Direction.TOP_SOUTH_EAST).getObject());

        Node end = start.getNextOn(Direction.NORTH).getNextOn(Direction.EAST).getNextOn(Direction.TOP).getNextOn(Direction.BOTTOM_NORTH_WEST).getNextOn(Direction.SOUTH);
        assertEquals("Item 212", end.getObject());

    }

}
