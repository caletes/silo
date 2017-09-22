import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepthFirstIterationTest extends TestCase {

    @Test
    public void testDepthFirstIteration() {
        final int size = 3;
        final int n = size - 1;
        final int rowsCount = 2 * n;

        List<String> coos = new ArrayList<>();
        for (int row = 0; row <= rowsCount; row++) {
            int colStart = Math.max(0, row - n);
            int colEnd = colStart + Math.min(row, rowsCount - row);
            for (int x = colStart; x <= colEnd; x++) {
                int y = row - x;
                coos.add(x + "," + y);
            }
        }

        List<String> expected = Arrays.asList(new String[]{"0,0", "0,1", "1,0", "0,2", "1,1", "2,0", "1,2", "2,1", "2,2"});
        assertEquals(expected, coos);
    }

}
