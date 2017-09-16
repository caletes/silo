import com.caletes.game.octree.MortonCode;
import junit.framework.TestCase;
import org.junit.Test;

public class MortonCodeTest extends TestCase {
    @Test
    public void testPack() {
        assertEquals(1287, MortonCode.pack(5, 9, 1));
        assertEquals(17958273, MortonCode.pack(100, 12, 257));
        assertEquals(1073712640, MortonCode.pack(1000, 1000, 1000));
        assertEquals(4565499904L, MortonCode.pack(1024, 512, 128));
        assertEquals(2216623912222749L, MortonCode.pack(100001, 100002, 100003));
        assertEquals(1266652052358349L, MortonCode.pack(100001, 12, 4007));
    }

    @Test
    public void testUnpack() {
        assertEquals("5,9,1", MortonCode.unpack(1287).toString());
        assertEquals("100,12,257", MortonCode.unpack(17958273).toString());
        assertEquals("1000,1000,1000", MortonCode.unpack(1073712640).toString());
        assertEquals("1024,512,128", MortonCode.unpack(4565499904L).toString());
        assertEquals("100001,100002,100003", MortonCode.unpack(2216623912222749L).toString());
        assertEquals("100001,12,4007", MortonCode.unpack(1266652052358349L).toString());
    }

    @Test
    public void testNeighboors() {
        System.out.println(MortonCode.pack(1, 0, 0));
        System.out.println(MortonCode.pack(2, 0, 0));
        System.out.println(MortonCode.pack(3, 0, 0));
        System.out.println(MortonCode.pack(1, 1, 0));
        System.out.println(MortonCode.pack(2, 1, 0));
        System.out.println(MortonCode.pack(3, 1, 0));
        System.out.println(MortonCode.pack(1, 2, 0));
        System.out.println(MortonCode.pack(2, 2, 0));
        System.out.println(MortonCode.pack(3, 2, 0));
    }

    @Test
    public void testAdd() {
        long morton1 = MortonCode.pack(4, 12, 54);
        long morton2 = MortonCode.pack(5, 2, 3);
        long result = MortonCode.add(morton1, morton2);
        MortonCode.Vector3 vector = MortonCode.unpack(result);
        assertEquals(9, vector.x);
        assertEquals(14, vector.y);
        assertEquals(57, vector.z);
    }

    @Test
    public void testSubstract() {
        long morton1 = MortonCode.pack(4, 12, 54);
        long morton2 = MortonCode.pack(2, 2, 3);
        long result = MortonCode.substract(morton1, morton2);
        MortonCode.Vector3 vector = MortonCode.unpack(result);
        assertEquals(2, vector.x);
        assertEquals(10, vector.y);
        assertEquals(51, vector.z);
    }

    @Test
    public void testInc() {
        long morton = MortonCode.pack(4, 14, 54);
        assertEquals(5, MortonCode.unpack(MortonCode.incX(morton)).x);
        assertEquals(15, MortonCode.unpack(MortonCode.incY(morton)).y);
        assertEquals(55, MortonCode.unpack(MortonCode.incZ(morton)).z);
    }

    @Test
    public void testReset() {
        long morton = MortonCode.pack(4, 14, 54);
        morton = MortonCode.setX(morton, 0);
        assertEquals(0, MortonCode.unpack(morton).x);
        assertEquals(14, MortonCode.unpack(morton).y);
        assertEquals(54, MortonCode.unpack(morton).z);
    }

    @Test
    public void testIncAll() {
        int size = 3;
        int sizeSquare = size * size;
        long morton = 0;
        long i = 0;
        for (int z = 0; z < size; z++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    i++;
                    System.out.println(MortonCode.unpack(morton));
                    morton = i % size == 0 ? MortonCode.setX(morton, 0) : MortonCode.incX(morton);
                }
                morton = i % sizeSquare == 0 ? MortonCode.setY(morton, 0) : MortonCode.incY(morton);
            }
            morton = MortonCode.incZ(morton);
        }
    }
}
