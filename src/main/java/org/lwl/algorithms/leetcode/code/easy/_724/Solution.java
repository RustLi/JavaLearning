package org.lwl.algorithms.leetcode.code.easy._724;


public class Solution {
    public static void main(String[] args) {
        int[] nums = {1,2,3,3};
        Solution solution = new Solution();
        int num = solution.pivotIndex(nums);
        System.out.println(num);
    }

    private int pivotIndex(int[] nums) {
        int sum = 0, leftsum = 0;
        for (int x: nums) sum += x;
        for (int i = 0; i < nums.length; i++) {
            if (leftsum == sum - leftsum - nums[i]) return i;
            leftsum += nums[i];
        }
        return -1;
    }

}
