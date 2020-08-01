import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by fenming.xue on 2020/8/1.
 */
class SolutionTest {


    @Test
    void maxSubArray() {

        int[] nums = {1,2,3,4,5};

        assertEquals(15,Solution.maxSubArray(nums));

        nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};

        assertEquals(6,Solution.maxSubArray(nums));
    }
}