package org.lwl.leetcode;

/**
 * LeetCode 28 - 找出字符串中第一个匹配项的下标
 *
 * 方法一：朴素滑动窗口，时间复杂度 O(m*n)，空间复杂度 O(1)
 * 方法二：KMP 算法，时间复杂度 O(m+n)，适用于 needle 很长或有重复前缀的场景
 */
public class StrStr {

    /**
     * 朴素匹配 —— 逐字符滑动窗口
     */
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        // needle 为空字符串，按约定返回 0
        if (m == 0) return 0;

        // 窗口起点 i，最多到 n - m
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i; // 完全匹配
            }
        }
        return -1;
    }

    /**
     * KMP 算法（知识扩展）
     */
    public int strStrKMP(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;

        // 构建 next 数组（前缀表）
        int[] next = buildNext(needle);
        int j = 0; // needle 的指针

        for (int i = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1]; // 回退
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    private int[] buildNext(String pattern) {
        int m = pattern.length();
        int[] next = new int[m];
        int j = 0; // 前缀末尾
        for (int i = 1; i < m; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static void main(String[] args) {
        StrStr solution = new StrStr();

        // 示例 1
        test(solution, "sadbutsad", "sad");

        // 示例 2
        test(solution, "leetcode", "leeto");

        // 边界：needle 在末尾
        test(solution, "hello", "lo");

        // 边界：needle 为空
        System.out.println("needle=\"\" → " + solution.strStr("abc", ""));

        // 边界：haystack 和 needle 完全相同
        test(solution, "abc", "abc");

        // KMP 验证
        System.out.println("\n=== KMP 验证 ===");
        System.out.println("KMP(\"sadbutsad\", \"sad\") = " + solution.strStrKMP("sadbutsad", "sad"));
        System.out.println("KMP(\"leetcode\", \"leeto\") = " + solution.strStrKMP("leetcode", "leeto"));
    }

    private static void test(StrStr s, String haystack, String needle) {
        System.out.printf("haystack=\"%s\", needle=\"%s\" → %d%n",
                haystack, needle, s.strStr(haystack, needle));
    }
}
