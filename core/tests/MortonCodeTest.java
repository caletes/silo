import com.caletes.game.octree.MortonCode;
import junit.framework.TestCase;
import org.junit.Test;

public class MortonCodeTest extends TestCase {
    @Test
    public void testPack() {
        assertEquals(1095, MortonCode.pack(5, 9, 1));
        assertEquals(67404996, MortonCode.pack(100, 12, 257));
        assertEquals(1073712640, MortonCode.pack(1000, 1000, 1000));
        assertEquals(1350565888, MortonCode.pack(1024, 512, 128));
        assertEquals(2216623912222773L, MortonCode.pack(100001, 100002, 100003));
        assertEquals(316699826095525L, MortonCode.pack(100001, 12, 4007));
    }

    @Test
    public void testUnpack() {
        assertEquals("5,9,1", MortonCode.unpack(1095).toString());
        assertEquals("100,12,257", MortonCode.unpack(67404996).toString());
        assertEquals("1000,1000,1000", MortonCode.unpack(1073712640).toString());
        assertEquals("1024,512,128", MortonCode.unpack(1350565888).toString());
        assertEquals("100001,100002,100003", MortonCode.unpack(2216623912222773L).toString());
        assertEquals("100001,12,4007", MortonCode.unpack(316699826095525L).toString());
    }

    @Test
    public void testNeighboors(){
        System.out.println(MortonCode.pack(1,0,0));
        System.out.println(MortonCode.pack(2,0,0));
        System.out.println(MortonCode.pack(3,0,0));
        System.out.println(MortonCode.pack(1,1,0));
        System.out.println(MortonCode.pack(2,1,0));
        System.out.println(MortonCode.pack(3,1,0));
        System.out.println(MortonCode.pack(1,2,0));
        System.out.println(MortonCode.pack(2,2,0));
        System.out.println(MortonCode.pack(3,2,0));
    }
}
