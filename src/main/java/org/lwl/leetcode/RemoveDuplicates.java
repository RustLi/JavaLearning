package org.lwl.leetcode;

/**
 * LeetCode 26 - 删除排序数组中的重复项
 * 时间复杂度 O(n)，空间复杂度 O(1)
 */
public class RemoveDuplicates {

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // slow: 下一个不重复元素应该放置的位置
        int slow = 1;

        // fast: 遍历数组，寻找下一个与前一元素不同的值
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    // 测试用例
    public static void main(String[] args) {
        RemoveDuplicates solution = new RemoveDuplicates();

        // 示例 1
        int[] nums1 = {1, 1, 2};
        int k1 = solution.removeDuplicates(nums1);
        System.out.println("示例1: k = " + k1);
        printArray(nums1, k1);

        // 示例 2
        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int k2 = solution.removeDuplicates(nums2);
        System.out.println("示例2: k = " + k2);
        printArray(nums2, k2);

        // 边界：全部相同
        int[] nums3 = {7, 7, 7, 7};
        int k3 = solution.removeDuplicates(nums3);
        System.out.println("全相同: k = " + k3);
        printArray(nums3, k3);

        // 边界：无重复
        int[] nums4 = {1, 2, 3, 4, 5};
        int k4 = solution.removeDuplicates(nums4);
        System.out.println("无重复: k = " + k4);
        printArray(nums4, k4);
    }

    private static void printArray(int[] nums, int k) {
        System.out.print("前 " + k + " 个元素: [");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i]);
            if (i < k - 1) System.out.print(", ");
        }
        System.out.println("]\n");
    }
}
