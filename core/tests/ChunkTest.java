import com.caletes.game.models.Chunk;
import junit.framework.TestCase;
import org.junit.Test;

public class ChunkTest extends TestCase {

    @Test
    public void testNextPowOf2() {
        assertEquals(1, Chunk.nextPowOf2(0));
        assertEquals(8, Chunk.nextPowOf2(8));
        assertEquals(256, Chunk.nextPowOf2(200));
        assertEquals(512, Chunk.nextPowOf2(450));
    }
}
