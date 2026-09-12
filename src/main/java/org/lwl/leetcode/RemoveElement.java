package org.lwl.leetcode;

/**
 * LeetCode 27 - 移除元素
 * 首尾双指针：利用"顺序可以改变"，用尾部元素覆盖要删除的元素
 * 时间复杂度 O(n)，空间复杂度 O(1)
 */
public class RemoveElement {

    /**
     * 首尾双指针 —— 最少写入次数
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            if (nums[left] == val) {
                // 用尾部元素覆盖当前要删除的元素
                nums[left] = nums[right];
                right--;
            } else {
                // 当前元素保留，left 前进
                left++;
            }
        }

        // left 即是不等于 val 的元素个数
        return left;
    }

    /**
     * 快慢指针 —— 保持相对顺序（当题目要求顺序不变时使用）
     */
    public int removeElementKeepOrder(int[] nums, int val) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

    public static void main(String[] args) {
        RemoveElement solution = new RemoveElement();

        // 示例 1
        int[] nums1 = {3, 2, 2, 3};
        test(solution, nums1, 3, "示例1");

        // 示例 2
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        test(solution, nums2, 2, "示例2");

        // 边界：全部删除
        int[] nums3 = {5, 5, 5, 5};
        test(solution, nums3, 5, "全部删除");

        // 边界：没有要删的
        int[] nums4 = {1, 2, 3, 4};
        test(solution, nums4, 9, "无匹配");
    }

    private static void test(RemoveElement s, int[] nums, int val, String label) {
        System.out.println("=== " + label + " ===");
        int[] arr = nums.clone();
        int k = s.removeElement(arr, val);
        System.out.println("输入: " + arrayStr(nums) + ", val = " + val);
        System.out.println("k = " + k);
        System.out.println("前 " + k + " 位: " + arrayStr(arr, k));
        System.out.println();
    }

    private static String arrayStr(int[] arr) {
        return arrayStr(arr, arr.length);
    }

    private static String arrayStr(int[] arr, int k) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < k; i++) {
            sb.append(arr[i]);
            if (i < k - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
