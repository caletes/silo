import com.caletes.game.octree.Direction;
import junit.framework.TestCase;
import org.junit.Test;

public class DirectionTest extends TestCase {

    @Test
    public void testGetFromDeltas() {
        assertEquals(Direction.BOTTOM_NORTH_WEST, Direction.getFromDeltas(-1, -1,1));
        assertEquals(Direction.BOTTOM_NORTH, Direction.getFromDeltas(0, -1,1));
        assertEquals(Direction.BOTTOM_NORTH_EAST, Direction.getFromDeltas(1, -1,1));
        assertEquals(Direction.BOTTOM_WEST, Direction.getFromDeltas(-1, 0,1));
        assertEquals(Direction.BOTTOM, Direction.getFromDeltas(0, 0,1));
        assertEquals(Direction.BOTTOM_EAST, Direction.getFromDeltas(1, 0,1));
        assertEquals(Direction.BOTTOM_SOUTH_WEST, Direction.getFromDeltas(-1, 1,1));
        assertEquals(Direction.BOTTOM_SOUTH, Direction.getFromDeltas(0, 1,1));
        assertEquals(Direction.BOTTOM_SOUTH_EAST, Direction.getFromDeltas(1, 1,1));
        assertEquals(Direction.NORTH_WEST, Direction.getFromDeltas(-1, -1,0));
        assertEquals(Direction.NORTH, Direction.getFromDeltas(0, -1,0));
        assertEquals(Direction.NORTH_EAST, Direction.getFromDeltas(1, -1,0));
        assertEquals(Direction.WEST, Direction.getFromDeltas(-1, 0,0));
        assertEquals(Direction.NONE, Direction.getFromDeltas(0, 0,0));
        assertEquals(Direction.EAST, Direction.getFromDeltas(1, 0,0));
        assertEquals(Direction.SOUTH_WEST, Direction.getFromDeltas(-1, 1,0));
        assertEquals(Direction.SOUTH, Direction.getFromDeltas(0, 1,0));
        assertEquals(Direction.SOUTH_EAST, Direction.getFromDeltas(1, 1,0));
        assertEquals(Direction.TOP_NORTH_WEST, Direction.getFromDeltas(-1, -1,-1));
        assertEquals(Direction.TOP_NORTH, Direction.getFromDeltas(0, -1,-1));
        assertEquals(Direction.TOP_NORTH_EAST, Direction.getFromDeltas(1, -1,-1));
        assertEquals(Direction.TOP_WEST, Direction.getFromDeltas(-1, 0,-1));
        assertEquals(Direction.TOP, Direction.getFromDeltas(0, 0,-1));
        assertEquals(Direction.TOP_EAST, Direction.getFromDeltas(1, 0,-1));
        assertEquals(Direction.TOP_SOUTH_WEST, Direction.getFromDeltas(-1, 1,-1));
        assertEquals(Direction.TOP_SOUTH, Direction.getFromDeltas(0, 1,-1));
        assertEquals(Direction.TOP_SOUTH_EAST, Direction.getFromDeltas(1, 1,-1));
    }
}
