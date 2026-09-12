package org.lwl.leetcode;

/**
 * 整数溢出检测演示
 *
 * 背景：LeetCode 7「整数反转」的辅助演示。
 * 演示"先计算再判断"无法检测 int 溢出——result * 10 + digit 在
 * int 范围内发生回绕，得到的是一个"合法"的 int 值，再与
 * Integer.MAX_VALUE 比较永远不成立。
 * 正确做法：在计算前用 Integer.MAX_VALUE / 10 与 result 先比较。
 */
public class OverflowDemo {
    public static void main(String[] args) {
        // 假设我们用直接判断的方式
        int result = 214748365;   // 这个值 ×10 肯定溢出
        int digit = 0;

        int calculated = result * 10 + digit;

        // "天真"地判断：算完再比
        if (calculated > Integer.MAX_VALUE) {
            System.out.println("检测到溢出！");
        }

        System.out.println("实际算出的值: " + calculated);
        System.out.println("MAX_VALUE:     " + Integer.MAX_VALUE);
        System.out.println("判断结果: " + calculated + " > " + Integer.MAX_VALUE
                + " ? " + (calculated > Integer.MAX_VALUE));
    }
}
