

/**
 * Created by fenming.xue on 2020/7/28.
 */
public class Solution {

    public static int leapWindow(int[] arr,int count){
        if(arr == null || arr.length <= 0 || count <= 0){
            return 0;
        }

        if(arr.length <= count){
            return 0;
        }

        int currentToatl = 0;
        int index = 0;
        int totalSum=0;

        for (int i = 0; i < count; i++) {
            currentToatl += arr[i];
            totalSum = currentToatl;
        }

        for (int i = count; i < arr.length; i++) {

            int total = currentToatl - arr[index] + arr[i];
            if(total >= totalSum){
                totalSum = total;
            }
            currentToatl = total;
            index++;
        }

        return totalSum;
    }

    public static int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() <= 0){
            return 0;
        }

        int[] m = new int[128];
        int len = 0;
        for(int i = 0, j = 0; j < s.length(); j++){
            i = Math.max(m[s.charAt(j)], i);
            len = Math.max(len, j - i + 1);
            m[s.charAt(j)] = j + 1;
        }
        return len;
    }
}
