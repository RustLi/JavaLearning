package org.lwl.leetcode;

/**
 * LeetCode 70 - 爬楼梯
 * <p>
 * 题目：假设你正在爬楼梯，需要 n 阶才能到达楼顶。每次你可以爬 1 或 2 个台阶，
 * 问有多少种不同的方法可以爬到楼顶。
 * <p>
 * 核心思路：动态规划（本质是斐波那契数列）
 * 到达第 n 阶的最后一步只有两种可能，且互斥完备：
 * - 从第 n-1 阶跨 1 阶上来；
 * - 从第 n-2 阶跨 2 阶上来。
 * 故递推式：f(n) = f(n-1) + f(n-2)，边界 f(0) = 1，f(1) = 1。
 * <p>
 * 朴素递归存在大量重叠子问题（调用树近似满二叉树），复杂度 O(2^n)；
 * 用 DP 数组降为 O(n)/O(n)；观察到 f(n) 只依赖前两项，可进一步用
 * 滚动变量把空间压到 O(1)。
 * <p>
 * 时间复杂度 O(n)：单层循环，每个状态只计算一次；
 * 空间复杂度 O(1)：仅用常数个变量（DP 数组版为 O(n)）。
 * <p>
 * 注意：n = 45 时结果为 1836311903，仍在 int 范围内；若 n 更大需改用 long 或取模。
 */
public class ClimbStairs {

    /**
     * 推荐解法：滚动变量（空间最优 O(1)）
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n; // n=1 返回 1，n=2 返回 2（同时也是边界 f(1)、f(2) 的值）
        }

        // prev2 代表 f(i-2)，prev1 代表 f(i-1)
        int prev2 = 1; // f(1) = 1
        int prev1 = 2; // f(2) = 2

        for (int i = 3; i <= n; i++) {
            int cur = prev1 + prev2; // f(i) = f(i-1) + f(i-2)
            prev2 = prev1;           // 向前滚动
            prev1 = cur;
        }

        return prev1;
    }

    /**
     * 标准解法：DP 数组（便于理解递推过程，也方便打印整张表）
     */
    public int climbStairsByDp(int n) {
        if (n <= 2) {
            return n;
        }

        // dp[i] 表示爬到第 i 阶的方法数
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * 朴素递归（反面教材）—— 存在大量重叠子问题，复杂度 O(2^n)
     * 仅用于对比说明，n > 40 时会明显变慢
     */
    public int climbStairsByRecursion(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsByRecursion(n - 1) + climbStairsByRecursion(n - 2);
    }

    public static void main(String[] args) {
        ClimbStairs solution = new ClimbStairs();

        // 基础用例
        for (int n = 1; n <= 10; n++) {
            System.out.printf("n = %-2d -> 滚动变量 = %-4d | DP数组 = %-4d%n",
                    n, solution.climbStairs(n), solution.climbStairsByDp(n));
        }

        // 边界：n = 45（题目上限，结果仍在 int 范围内）
        System.out.println();
        System.out.println("n = 45 -> " + solution.climbStairs(45));

        // 打印 n=6 的 DP 表，直观展示递推过程
        System.out.println();
        System.out.println("n = 6 的 DP 表（f(n) = f(n-1) + f(n-2)）：");
        int n = 6;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        for (int i = 1; i <= n; i++) {
            String formula = (i <= 2) ? "边界" : dp[i - 1] + " + " + dp[i - 2];
            System.out.printf("  f(%d) = %-2d   <- %s%n", i, dp[i], formula);
        }
    }
}
