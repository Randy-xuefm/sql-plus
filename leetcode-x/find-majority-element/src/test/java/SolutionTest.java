import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by fenming.xue on 2020/7/4.
 */
class SolutionTest {

    @Test
    public void majorityElement() {
        int[] nums= {1,2,2,2,2,2,3,3,1};
        Assertions.assertEquals(2,Solution.majorityElement(nums));
    }

    @Test
    public void majorityElement1(){
        int[] nums= {1,2};
        Assertions.assertEquals(-1,Solution.majorityElement(nums));
    }

    @Test
    void majorityElement4moer() {
        int[] nums= {1,2,2,2,2,2,3,3,1};
        Assertions.assertEquals(2,Solution.majorityElement4moer(nums));
    }

    @Test
    void majorityElement4moer1() {
        int[] nums= {1,2};
        Assertions.assertEquals(-1,Solution.majorityElement4moer(nums));
    }
}