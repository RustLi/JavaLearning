package org.lwl.algorithms.presum;

/**
 *
 *@author lwl
 *@date 2025/2/21
 *@description  前缀和技巧
 *
 * 适用场景：原始数组不改变，同时需要频繁查询某个区间的累加和
 *
 *  【-2，0，3，-5，2，-1】 0,2
 *  return -2 + 0 + 3
 */
public class PreSumTest {

    private int[] preSum;

    public PreSumTest(int[] nums) {
        this.preSum = new int[nums.length + 1];
        for (int i = 0; i < preSum.length; i++) {
            System.out.println(preSum[i]);
        }

        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        System.out.println("=======================");

        for (int i = 0; i < preSum.length; i++) {
            System.out.println(preSum[i]);
        }
    }

    public int sumRange(int left, int right){
        return preSum[right + 1] - preSum[left];
    }

    public static void main(String[] args) {
        PreSumTest test = new PreSumTest(new int[]{-2, 0, 3, -5, 2, -1});
        System.out.println("=======================");
        System.out.println(test.sumRange(0, 2));
    }
}
