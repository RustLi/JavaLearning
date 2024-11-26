package org.lwl.algorithms.leetcode.code.str;

import java.util.Arrays;

/**
 *
 *@author  lwl
 *@date 2024/11/12
 *@description
 *
 * 双指针
 * 基本概念
 * 双指针：使用两个指针来遍历数组或链表，通常一个指针在前，一个指针在后，通过调整指针的位置来解决问题。
 * 特点：双指针可以有效地减少时间复杂度，特别是对于有序数组或链表的操作。
 * 应用场景
 * 两数之和：在一个有序数组中找到两个数，使它们的和等于目标值。
 * 移除元素：从数组中移除指定的元素。
 * 示例代码
 * 在一个有序数组中找到两个数，使它们的和等于目标值：
 */
public class TwoPointers {

    public static int[] twoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6};
        int target = 7;
        int[] result = twoSum(nums, target);
        System.out.println("找到的两个数的索引: " + Arrays.toString(result));
    }
}
