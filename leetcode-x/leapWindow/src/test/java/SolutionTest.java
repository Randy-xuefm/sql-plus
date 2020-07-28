import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by fenming.xue on 2020/7/28.
 */
class SolutionTest {

    @Test
    void leapWindow() {
        int[] arr = {1,2,3,4};

        assertEquals(7,Solution.leapWindow(arr,2));

        arr = new int[]{-1, 4, 7, -3, 8, 5, -2, 6};

        assertEquals(12,Solution.leapWindow(arr,3));
    }
}