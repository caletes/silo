import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.WorldPosition;
import junit.framework.TestCase;
import org.junit.Test;

public class WorldPositionTest extends TestCase {
    @Test
    public void testPosition() {
        WorldPosition position = new WorldPosition(434.5f, 237.2f, 150.8f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(8f, chunkPosition.x);
        assertEquals(4f, chunkPosition.y);
        assertEquals(3f, chunkPosition.z);
        Vector3 worldPosition = position.getPosition();
        assertEquals(434.5f, worldPosition.x);
        assertEquals(237.2f, worldPosition.y);
        assertEquals(150.8f, worldPosition.z);
        Vector3 inChunkPosition = position.getItemPositionInChunk(50);
        assertEquals(34.5f, inChunkPosition.x);
        // Ajout du delta pour compenser l'approximation du modulo
        assertEquals(37.2f, inChunkPosition.y, 0.1);
        assertEquals(0.8f, inChunkPosition.z, 0.1);
    }

    @Test
    public void testPositionZero() {
        WorldPosition position = new WorldPosition(0f, 0f, 0f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(0f, chunkPosition.x);
        assertEquals(0f, chunkPosition.y);
        assertEquals(0f, chunkPosition.z);
        Vector3 worldPosition = position.getPosition();
        assertEquals(0f, worldPosition.x);
        assertEquals(0f, worldPosition.y);
        assertEquals(0f, worldPosition.z);
        Vector3 inChunkPosition = position.getItemPositionInChunk(50);
        assertEquals(0f, inChunkPosition.x);
        assertEquals(0f, inChunkPosition.y);
        assertEquals(0f, inChunkPosition.z);
    }


    @Test
    public void testPositionLowerThanOne() {
        WorldPosition position = new WorldPosition(0.2f, 0.5f, 0.9f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(0f, chunkPosition.x);
        assertEquals(0f, chunkPosition.y);
        assertEquals(0f, chunkPosition.z);
        Vector3 worldPosition = position.getPosition();
        assertEquals(0.2f, worldPosition.x);
        assertEquals(0.5f, worldPosition.y);
        assertEquals(0.9f, worldPosition.z);
        Vector3 inChunkPosition = position.getItemPositionInChunk(50);
        assertEquals(0.2f, inChunkPosition.x);
        assertEquals(0.5f, inChunkPosition.y);
        assertEquals(0.9f, inChunkPosition.z);
    }

    @Test
    public void testPositionNegative() {
        WorldPosition position = new WorldPosition(20.4f, -10.8f, 0.5f);
        Vector3 chunkPosition = position.getChunkPosition(50);
        assertEquals(0f, chunkPosition.x);
        assertEquals(-1f, chunkPosition.y);
        assertEquals(0f, chunkPosition.z);
        Vector3 worldPosition = position.getPosition();
        assertEquals(20.4f, worldPosition.x);
        assertEquals(-10.8f, worldPosition.y);
        assertEquals(0.5f, worldPosition.z);
        Vector3 inChunkPosition = position.getItemPositionInChunk(50);
        assertEquals(20.4f, inChunkPosition.x);
        assertEquals(-10.8f, inChunkPosition.y);
        assertEquals(0.5f, inChunkPosition.z);
    }

    @Test
    public void testCreateFromChunkPosition() {
        WorldPosition position = WorldPosition.createFromChunkPosition(5, 12, 2, 50);
        Vector3 worldPosition = position.getPosition();
        assertEquals(250f, worldPosition.x);
        assertEquals(600f, worldPosition.y);
        assertEquals(100f, worldPosition.z);
        Vector3 inChunkPosition = position.getItemPositionInChunk(50);
        assertEquals(0f, inChunkPosition.x);
        assertEquals(0f, inChunkPosition.y);
        assertEquals(0f, inChunkPosition.z);
    }
}
