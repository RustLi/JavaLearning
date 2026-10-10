package org.lwl.leetcode;

/**
 * LeetCode 11 - 盛最多水的容器
 *
 * 题目：给定一个长度为 n 的整数数组 height。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i])。
 *       找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水，返回容器可以储存的最大水量。
 *
 * 思路：对撞双指针（左右夹逼）。
 *       两条线 i、j（i < j）构成容器的水量 = 宽度 × 有效高度 = (j - i) × min(height[i], height[j])，
 *       因为水会从较矮的那条线溢出，所以高度由较短的一边决定。
 *       初始时让 left、right 分别指向数组两端，此时宽度最大。
 *       每轮比较 height[left] 与 height[right]，**只移动较矮的那一根**：
 *         若移动较高的那根，宽度必然变小，而新高度受限于「没动的那根矮线」，不可能超过当前高度，
 *         因此水量一定不会变大，移动它没有任何收益；
 *         若移动较矮的那根，虽然宽度变小，但有可能遇到更高的线，从而让 min 变大，水量才有提升的可能。
 *       故每轮结束后挪动较矮的一端，并不断更新最大值，当两指针相遇时结束。
 *
 * 时间复杂度 O(n)：左右指针一共走 n-1 步，每步 O(1)
 * 空间复杂度 O(1)：只用了两个指针和两个变量
 */
public class MaxArea {

    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int max = 0;

        while (left < right) {
            // 宽度 × 较短边的高度 = 当前容器的容量
            int h = Math.min(height[left], height[right]);
            int area = (right - left) * h;
            max = Math.max(max, area);

            // 只移动较矮的一端，保留较高的一端作为「可能的边界」
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }

    // 暴力解法：枚举所有两条线的组合，用于对照验证
    public int maxAreaBruteForce(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = (j - i) * Math.min(height[i], height[j]);
                max = Math.max(max, area);
            }
        }
        return max;
    }

    // 测试用例
    public static void main(String[] args) {
        MaxArea solution = new MaxArea();

        // 示例 1：最多能容纳 49，由下标 1(8) 和下标 8(7) 构成，宽 7 × 高 7 = 49
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("示例1: 最大水量 = " + solution.maxArea(height1));

        // 示例 2：只有两根线，宽 1 × 高 1 = 1
        int[] height2 = {1, 1};
        System.out.println("示例2: 最大水量 = " + solution.maxArea(height2));

        // 边界：中间高两边低，最优解取中间两根
        int[] height3 = {1, 2, 1};
        System.out.println("示例3: 最大水量 = " + solution.maxArea(height3));

        // 边界：只有一根线，无法构成容器
        int[] height4 = {5};
        System.out.println("单根线: 最大水量 = " + solution.maxArea(height4));

        // 对照验证：双指针结果应与暴力解法一致
        System.out.println("暴力对照: " + solution.maxAreaBruteForce(height1)
                + " / 双指针: " + solution.maxArea(height1));
    }
}
