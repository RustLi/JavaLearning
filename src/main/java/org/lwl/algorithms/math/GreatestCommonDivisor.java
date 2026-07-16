package org.lwl.algorithms.math;

/**
 * @date: 2026/7/16
 * @author: lwl
 * @description:
 *
 * 最大公约数（Greatest Common Divisor）
 *
 * 辗转相除法（欧几里得算法）：
 * 两个正整数 a 和 b（a > b），它们的最大公约数等于 b 和 a % b 的最大公约数。
 *
 * 例如：gcd(12, 8)
 *   12 % 8 = 4  -> gcd(8, 4)
 *   8 % 4 = 0   -> gcd(4, 0) -> 返回 4
 *
 * 时间复杂度：O(log(min(a, b)))
 * 空间复杂度：递归 O(log(min(a, b)))，迭代 O(1)
 */
public class GreatestCommonDivisor {

    public static void main(String[] args) {
        GreatestCommonDivisor gcd = new GreatestCommonDivisor();
        System.out.println(gcd.gcd(12, 8));
        System.out.println(gcd.gcd(48, 18));
        System.out.println(gcd.gcd(100, 75));
        System.out.println(gcd.gcdIterative(12, 8));
        System.out.println(gcd.gcd(0, 5));
        System.out.println(gcd.lcm(12, 8));
    }

    /**
     * 递归实现，辗转相除法
     * gcd(a, b) = gcd(b, a % b)
     * 当 b == 0 时，返回 a
     */
    public int gcd(int a, int b) {
        if (a < 0) a = -a;
        if (b < 0) b = -b;
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    /**
     * 迭代实现，辗转相除法
     * 空间复杂度 O(1)
     */
    public int gcdIterative(int a, int b) {
        if (a < 0) a = -a;
        if (b < 0) b = -b;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * 最小公倍数（Least Common Multiple）
     * lcm(a, b) = a * b / gcd(a, b)
     */
    public int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a / gcd(a, b) * b);
    }
}
