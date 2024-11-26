package org.lwl.algorithms.leetcode.code.str;

/**
 *
 *@author  lwl
 *@date 2024/11/12
 *@description
 *
 * 滑动窗口和双指针是两种常用的算法技巧，它们在解决特定类型的问题时非常有效。下面分别介绍这两种技巧的基本概念、应用场景和示例代码。
 * 滑动窗口
 * 基本概念
 * 滑动窗口：滑动窗口是一种优化的暴力搜索方法，通常用于数组或字符串中寻找满足某些条件的子数组或子字符串。
 * 特点：通过维护一个窗口来逐步移动并调整窗口的大小，避免重复计算，从而提高效率。
 * 应用场景
 * 固定长度的子数组：例如，找到长度为 k 的子数组的最大和。
 * 可变长度的子数组：例如，找到和为给定值的最小子数组长度。
 * 示例代码
 * 找到长度为 k 的子数组的最大和：
 */
public class SlidingWindow {
    public static int maxSubArraySum(int[] nums, int k) {
        if (nums == null || k <= 0 || k > nums.length) {
            return 0;
        }
        
        int maxSum = 0;
        int windowSum = 0;
        
        // 初始化窗口
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        maxSum = windowSum;
        
        // 滑动窗口
        for (int i = k; i < nums.length; i++) {
            windowSum = windowSum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6};
        int k = 3;
        int result = maxSubArraySum(nums, k);
        System.out.println("长度为 " + k + " 的子数组的最大和: " + result);
    }
}
