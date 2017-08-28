import com.caletes.game.IsoConverter;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.tilesheet.Cubesheet;
import com.caletes.game.models.tilesheet.KenneyCubesheet;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.OctreeOutOfBoundsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorldTest extends GdxTest {
    @Test
    public void testGetPeakNode() throws OctreeOutOfBoundsException {
        Cubesheet cubesheet = new KenneyCubesheet();
        IsoConverter isoConverter = new IsoConverter(cubesheet.getTileWidth(), cubesheet.getTileHeight());
        CubeFactory cubeFactory = new CubeFactory(cubesheet);
        World world = new World(32, isoConverter);
        for (int z = 0; z < 25; z++) {
            world.pushObjectAt(cubeFactory.createStoneCube(), 8, 8, z);
        }

        Node peak = world.getPeakNode(8, 8, 0);
        assertEquals(24, peak.getPosition().z);
    }
}
