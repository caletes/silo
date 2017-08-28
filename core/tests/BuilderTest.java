import com.caletes.game.builders.Builder;
import junit.framework.TestCase;
import org.junit.Test;

public class BuilderTest extends TestCase {

    @Test
    public void testNextPowOf2() {
        assertEquals(1, Builder.nextPowOf2(0));
        assertEquals(8, Builder.nextPowOf2(8));
        assertEquals(256, Builder.nextPowOf2(200));
        assertEquals(512, Builder.nextPowOf2(450));
    }
}
