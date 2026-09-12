package org.lwl.leetcode;

/**
 * LeetCode 29 - 两数相除（最终优化版）
 *
 * 纯 32 位 int · 单遍 O(log n) · 负数域运算 · 溢出守卫
 */
public class DivideTwoIntegers {

    public int divide(int dividend, int divisor) {
        // 唯一溢出：INT_MIN / -1 = 2^31 → 超出 INT_MAX
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 结果符号：同号为正
        boolean positive = (dividend < 0) == (divisor < 0);

        // 全转负数：负数的值域比正数多 1，可以安全容纳 INT_MIN
        if (dividend > 0) dividend = -dividend;
        if (divisor > 0) divisor = -divisor;

        int quotient = 0;

        // 从高位到低位逐位判定：quotient 的 bit i 能不能置 1？
        for (int i = 31; i >= 0; i--) {
            // 溢出守卫：divisor << i 必须在 int 范围内且不溢出
            if (i > 0 && divisor < Integer.MIN_VALUE >> i) {
                continue;
            }
            int shifted = divisor << i;

            // shifted >= dividend 意味着可以从 dividend 中安全减去 divisor * 2^i
            // （shifted 更靠近 0，减法不会让 dividend "穿越" 0 变成正数）
            if (shifted >= dividend) {
                dividend -= shifted;
                quotient |= 1 << i;
            }
        }

        return positive ? quotient : -quotient;
    }

    // ==================== 测试 ====================
    public static void main(String[] args) {
        DivideTwoIntegers s = new DivideTwoIntegers();

        int[][] cases = {
            {10, 3}, {7, -3}, {0, 5}, {1, 2}, {-1, -2},
            {100, 7}, {-100, 7}, {-100, -7},
            {Integer.MAX_VALUE, 1}, {Integer.MAX_VALUE, -1},
            {Integer.MIN_VALUE, 1}, {Integer.MIN_VALUE, -1},
            {Integer.MIN_VALUE, 2}, {Integer.MIN_VALUE, Integer.MIN_VALUE},
        };

        int pass = 0;
        for (int[] c : cases) {
            int result = s.divide(c[0], c[1]);
            int expected;
            if (c[0] == Integer.MIN_VALUE && c[1] == -1) {
                expected = Integer.MAX_VALUE;
            } else {
                expected = c[0] / c[1];
            }
            boolean ok = result == expected;
            if (ok) pass++;
            System.out.printf("%s %11d / %11d = %11d (期望 %d)%n",
                    ok ? "✓" : "✗", c[0], c[1], result, expected);
        }
        System.out.println("\n通过: " + pass + "/" + cases.length);
    }
}
