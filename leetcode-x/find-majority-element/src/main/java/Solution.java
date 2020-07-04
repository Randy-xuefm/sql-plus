import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenming.xue on 2020/7/4.
 */
public class Solution {

    /**
     * 数组中占比超过一半的元素称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        if(nums == null || nums.length <= 0){
            return -1;
        }
        Map<Integer,Integer> countMap = new HashMap<>(nums.length,1);
        for (int num : nums) {
            Integer count = countMap.get(num);
            if(count == null){
                countMap.put(num,1);
            }else{
                countMap.put(num,++count);
            }
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() > nums.length/2){
                return entry.getKey();
            }
        }
        return -1;
    }
}
