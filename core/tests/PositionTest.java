import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.Position;
import junit.framework.TestCase;
import org.junit.Test;

public class PositionTest extends TestCase {
    @Test
    public void testPosition() {
        Position position = new Position(434.5f, 237.2f, 150.8f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(8f, chunkPosition.x);
        assertEquals(4f, chunkPosition.y);
        assertEquals(3f, chunkPosition.z);
        Vector3 worldPosition = position.getWorldPosition();
        assertEquals(434.5f, worldPosition.x);
        assertEquals(237.2f, worldPosition.y);
        assertEquals(150.8f, worldPosition.z);
        Vector3 itemPosition = position.getItemPosition();
        assertEquals(0.5f, itemPosition.x);
        assertEquals(0.2f, itemPosition.y);
        assertEquals(0.8f, itemPosition.z);
    }

    @Test
    public void testPositionZero() {
        Position position = new Position(0f, 0f, 0f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(0f, chunkPosition.x);
        assertEquals(0f, chunkPosition.y);
        assertEquals(0f, chunkPosition.z);
        Vector3 worldPosition = position.getWorldPosition();
        assertEquals(0f, worldPosition.x);
        assertEquals(0f, worldPosition.y);
        assertEquals(0f, worldPosition.z);
        Vector3 itemPosition = position.getItemPosition();
        assertEquals(0f, itemPosition.x);
        assertEquals(0f, itemPosition.y);
        assertEquals(0f, itemPosition.z);
    }


    @Test
    public void testPositionLowerThanOne() {
        Position position = new Position(0.2f, 0.5f, 0.9f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(0f, chunkPosition.x);
        assertEquals(0f, chunkPosition.y);
        assertEquals(0f, chunkPosition.z);
        Vector3 worldPosition = position.getWorldPosition();
        assertEquals(0.2f, worldPosition.x);
        assertEquals(0.5f, worldPosition.y);
        assertEquals(0.9f, worldPosition.z);
        Vector3 itemPosition = position.getItemPosition();
        assertEquals(0.2f, itemPosition.x);
        assertEquals(0.5f, itemPosition.y);
        assertEquals(0.9f, itemPosition.z);
    }
}
