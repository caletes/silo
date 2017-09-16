import com.caletes.game.Collider;
import com.caletes.game.IsoConverter;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.models.spritesheet.KenneyCubeSheet;
import org.junit.Assert;
import org.junit.Test;


public class ColliderTest extends GdxTest {

    @Test
    public void testCollide() {
        IsoConverter.createInstance(0,0);
        Collider collider = Collider.getInstance();
        CubeFactory cubeFactory = new CubeFactory(new KenneyCubeSheet());
        Cube cube1 = cubeFactory.createGrassCube(false, false);
        Cube cube2 = cubeFactory.createGrassCube(false, false);
        Cube cube3 = cubeFactory.createGrassCube(false, false);
        Cube cube4 = cubeFactory.createGrassCube(false, false);
        cube1.applyWorldPosition(new WorldPosition(1,1,1));
        cube2.applyWorldPosition(new WorldPosition(1.5f,1,1));
        cube3.applyWorldPosition(new WorldPosition(2f,1,1));
        cube4.applyWorldPosition(new WorldPosition(2.1f,1,1));
        Assert.assertTrue(collider.collide(cube1,cube2));
        Assert.assertTrue(collider.collide(cube1,cube3));
        Assert.assertFalse(collider.collide(cube1,cube4));
    }
}
