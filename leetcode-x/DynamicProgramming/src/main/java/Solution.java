/**
 * Created by fenming.xue on 2020/8/1.
 */
public class Solution {

    /**
     * 输入一个整型数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
     *
     * 要求时间复杂度为O(n)。
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        if(nums == null || nums.length <= 0){
            return 0;
        }

        int max = nums[0]; ;
        for (int i = 1; i < nums.length; i++) {
            nums[i] += Math.max(nums[i-1],0);
            max = Math.max(nums[i],max);
        }

        return max;
    }

}
