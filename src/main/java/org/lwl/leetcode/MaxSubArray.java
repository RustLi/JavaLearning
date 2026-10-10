package org.lwl.leetcode;

/**
 * LeetCode 53 - 最大子数组和（连续子数组的最大和）
 *
 * 题目：给定一个整数数组 nums，找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 思路：动态规划 / Kadane 算法。
 *       dp[i] 表示「以 nums[i] 结尾」的连续子数组的最大和。
 *       转移方程：dp[i] = max(nums[i], dp[i-1] + nums[i])，
 *       要么把 nums[i] 续接到前面的最优子数组上，要么以 nums[i] 单独重新开始。
 *       答案取所有 dp[i] 的最大值。由于只依赖前一个状态，用滚动变量优化空间。
 * 时间复杂度 O(n)，空间复杂度 O(1)
 */
public class MaxSubArray {

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // prev: 以当前元素的前一个位置结尾的连续子数组最大和，即 dp[i-1]
        int prev = nums[0];
        // max: 全局最大和
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // 状态转移：续接 or 重新开始
            prev = Math.max(nums[i], prev + nums[i]);
            max = Math.max(max, prev);
        }

        return max;
    }

    int maxSubArray1(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
// 定义：dp[i] 记录以 nums[i] 为结尾的
//「
//        最⼤⼦数组和
//」
        int[] dp = new int[n];
// base case
// 第⼀个元素前⾯没有⼦数组
        dp[0] = nums[0];
// 状态转移⽅程
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }
// 得到 nums 的最⼤⼦数组
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 测试用例
    public static void main(String[] args) {
        MaxSubArray solution = new MaxSubArray();

        // 示例 1：最大连续子数组为 [4,-1,2,1]，和为 6
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("示例1: 最大子数组和 = " + solution.maxSubArray(nums1));

        // 示例 2：只有一个元素
        int[] nums2 = {1};
        System.out.println("示例2: 最大子数组和 = " + solution.maxSubArray(nums2));

        // 示例 3：全为正数，直接全部相加
        int[] nums3 = {5, 4, -1, 7, 8};
        System.out.println("示例3: 最大子数组和 = " + solution.maxSubArray(nums3));

        // 边界：全为负数，只能选最大的那个负数
        int[] nums4 = {-3, -1, -2};
        System.out.println("全为负数: 最大子数组和 = " + solution.maxSubArray(nums4));
    }
}
