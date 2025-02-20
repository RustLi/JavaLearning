package org.lwl.algorithms.dp;

import java.util.Arrays;

/**
 *
 *@author lwl
 *@date 2025/2/6
 *@description  最长子序列问题
 *
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 *
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * 示例 2：
 *
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * 示例 3：
 *
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * 提示：
 *
 * 1 <= nums.length <= 2500
 * -104 <= nums[i] <= 104
 * 进阶：
 *
 * 你能将算法的时间复杂度降低到 O(n log(n)) 吗?
 *
 *
 *   dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
 *
 *  举例：
 *   nums = [1,4,3,4,2,3]
 *   dp = [1,2,2,3,2,?]
 *  已知dp[0..4]的值，要求求解dp[5]的值，dp[5]表示以num[5]=3结尾的最长子序列长度。
 *   找出小于nums[5]的元素为dp[0]和dp[4]，对比dp[0]和dp[4], dp[5]等于dp[0]或者dp[4]的较大值+1, 代码如下:
 *
 *   for (int j = 0; j < i; j++) {
 *     if (nums[i] > nums[j]) {
 *         dp[i] = Math.max(dp[i], dp[j] + 1);
 *     }
 *   }
 */
public class LongestSubSeq {

    public static void main(String[] args) {
        LongestSubSeq mm = new LongestSubSeq();
        int[] nums = {1,4,3,4,2,3};
        int ret = mm.lengthOfLIS(nums);
        System.out.println(ret);
    }

    /**
     * 标准解法，时间复杂度为o(n^2)
     **/
    public int lengthOfLIS(int[] nums) {
        // 定义：dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度
        int[] dp = new int[nums.length];
        // base case：dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int res = 0;
        for (int j : dp) {
            res = Math.max(res, j);
        }
        return res;
    }
}
