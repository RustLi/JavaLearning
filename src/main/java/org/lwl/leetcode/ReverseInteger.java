package org.lwl.leetcode;

/**
 * 整数反转
 * LeetCode 7 - Reverse Integer
 * https://leetcode.cn/problems/reverse-integer/
 */
public class ReverseInteger {

    public static int reverse(int x) {
        int result = 0;

        while (x != 0) {
            int digit = x % 10;          // 取出最低位数字
            x /= 10;                     // 去掉最低位

            // 正溢出检查：result * 10 + digit > Integer.MAX_VALUE ?
            if (result > Integer.MAX_VALUE / 10
                    || (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return 0;
            }
            // 负溢出检查：result * 10 + digit < Integer.MIN_VALUE ?
            if (result < Integer.MIN_VALUE / 10
                    || (result == Integer.MIN_VALUE / 10 && digit < Integer.MIN_VALUE % 10)) {
                return 0;
            }

            result = result * 10 + digit; // 追加到结果末尾
        }

        return result;
    }

    public static void main(String[] args) {
        // 测试用例
        int[][] tests = {
                { 123, 321 },
                { -123, -321 },
                { 120, 21 },
                { 0, 0 },
                { 1534236469, 0 },       // 反转后溢出
                { -2147483648, 0 },      // 反转后溢出
        };

        for (int[] t : tests) {
            int got = reverse(t[0]);
            System.out.printf("input: %d, output: %d, expected: %d %s%n",
                    t[0], got, t[1], got == t[1] ? "✓" : "✗ FAIL");
        }
    }
}
