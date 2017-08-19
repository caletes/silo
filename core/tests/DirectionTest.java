import com.caletes.game.Direction;
import junit.framework.TestCase;
import org.junit.Test;

public class DirectionTest extends TestCase {

    @Test
    public void testGetFromDeltas() {
        assertNull(Direction.getFromDeltas(0, 0));

        assertEquals(Direction.NORTH, Direction.getFromDeltas(0, 1));
        assertEquals(Direction.EAST, Direction.getFromDeltas(1, 0));
        assertEquals(Direction.SOUTH, Direction.getFromDeltas(0, -1));
        assertEquals(Direction.WEST, Direction.getFromDeltas(-1, 0));

        assertEquals(Direction.NORTH_EAST, Direction.getFromDeltas(1, 1));
        assertEquals(Direction.SOUTH_EAST, Direction.getFromDeltas(1, -1));
        assertEquals(Direction.SOUTH_WEST, Direction.getFromDeltas(-1, -1));
        assertEquals(Direction.NORTH_WEST, Direction.getFromDeltas(-1, 1));

    }
}
