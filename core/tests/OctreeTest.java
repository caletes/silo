import com.caletes.game.octree.Octree;
import junit.framework.TestCase;
import org.junit.Test;

public class OctreeTest extends TestCase {
    @Test
    public void testComputeExponent() {
        Octree octree = new Octree(8);
        assertEquals(3f, octree.getExponent());
        Octree octree2 = new Octree(256);
        assertEquals(8f, octree2.getExponent());
    }
}
