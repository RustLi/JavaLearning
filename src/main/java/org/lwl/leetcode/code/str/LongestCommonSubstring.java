package org.lwl.leetcode.code.str;

/**
 * @author: RustLi
 * @date: 2026/8/25
 * @description:
 *
 * 最长公共子串（Longest Common Substring）
 *
 * 题目：给定两个字符串 str1 和 str2，找出它们最长的公共子串的长度（以及该子串本身）。
 *
 * 示例 1:
 * 输入: str1 = "abcde", str2 = "abfde"
 * 输出: 2（公共子串 "ab" 或 "de"）
 * 解释: "ab" 长度为 2，"de" 长度为 2；"abcde" 与 "abfde" 最长公共子串为 "ab" / "de"。
 *
 * 示例 2:
 * 输入: str1 = "abcdxyz", str2 = "xyzabcd"
 * 输出: 4（公共子串 "abcd"）
 *
 * 示例 3:
 * 输入: str1 = "OldSite:GeeksforGeeks.org", str2 = "NewSite:GeeksQuiz.com"
 * 输出: 10（公共子串 "Site:Geeks"）
 *
 * 说明：
 * - 公共子串必须连续（区别于公共子序列 LCS，后者允许跳跃）。
 * - 本题与 LeetCode 5（最长回文子串）同源，都是「区间/双串 DP」的典型应用。
 *
 * 解法：动态规划
 * 定义 dp[i][j] 表示以 str1 的第 i-1 个字符结尾、以 str2 的第 j-1 个字符结尾的
 * 最长公共子串长度（下标从 1 开始是为了方便处理边界 i=0 / j=0，即空串）。
 * 状态转移：
 *   str1.charAt(i-1) == str2.charAt(j-1) 时，dp[i][j] = dp[i-1][j-1] + 1，否则 dp[i][j] = 0。
 * 遍历过程中记录全局最大值 maxLen 以及子串的结束位置，最后截取子串返回。
 *
 * 复杂度分析：
 * - 时间：O(m * n)，m、n 分别为两个字符串长度，两层循环。
 * - 空间：二维版本 O(m * n)；优化后仅用一行滚动数组，为 O(min(m, n))。
 **/
public class LongestCommonSubstring {

    /**
     * 二维 DP：直观易懂，返回最长公共子串本身。
     */
    public String longestCommonSubstring(String str1, String str2) {
        if (str1 == null || str2 == null || str1.isEmpty() || str2.isEmpty()) {
            return "";
        }
        int m = str1.length(), n = str2.length();
        // dp[i][j]：以 str1[i-1]、str2[j-1] 结尾的最长公共子串长度
        int[][] dp = new int[m + 1][n + 1];
        int maxLen = 0;          // 最长公共子串长度
        int endIdx = 0;          // 最长公共子串在 str1 中的结束位置（不含）
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        endIdx = i;
                    }
                }
                // 字符不等时 dp[i][j] 保持 0，最长公共子串要求连续，一旦断掉立即清零
            }
        }
        return maxLen == 0 ? "" : str1.substring(endIdx - maxLen, endIdx);
    }

    /**
     * 空间优化版：滚动数组，只用一行 dp，空间 O(min(m, n))。
     * 注意：dp[j] 依赖上一行同列 dp[j] 和上一行前一列 dp[j-1]，
     * 因此 j 必须从大到小遍历，避免新值覆盖旧值影响后续计算。
     */
    public String longestCommonSubstringOptimized(String str1, String str2) {
        if (str1 == null || str2 == null || str1.isEmpty() || str2.isEmpty()) {
            return "";
        }
        // 让 str2 作为列方向，保证空间取两者较小值
        if (str1.length() < str2.length()) {
            String tmp = str1;
            str1 = str2;
            str2 = tmp;
        }
        int m = str1.length(), n = str2.length();
        int[] dp = new int[n + 1];
        int maxLen = 0, endIdx = 0;
        for (int i = 1; i <= m; i++) {
            // 从大到小更新，保证 dp[j] 使用的是上一行（i-1 行）的数据
            for (int j = n; j >= 1; j--) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[j] = dp[j - 1] + 1;
                    if (dp[j] > maxLen) {
                        maxLen = dp[j];
                        endIdx = i;
                    }
                } else {
                    dp[j] = 0;
                }
            }
        }
        return maxLen == 0 ? "" : str1.substring(endIdx - maxLen, endIdx);
    }

    public static void main(String[] args) {
        LongestCommonSubstring lcs = new LongestCommonSubstring();
        String[][] tests = {
                {"abcde", "abfde"},
                {"abcdxyz", "xyzabcd"},
                {"OldSite:GeeksforGeeks.org", "NewSite:GeeksQuiz.com"},
                {"abc", "def"}
        };
        for (String[] t : tests) {
            String r1 = lcs.longestCommonSubstring(t[0], t[1]);
            String r2 = lcs.longestCommonSubstringOptimized(t[0], t[1]);
            System.out.println("str1=" + t[0] + ", str2=" + t[1]
                    + " => 二维版: \"" + r1 + "\"(len=" + r1.length()
                    + "), 滚动数组版: \"" + r2 + "\"(len=" + r2.length() + ")");
        }
    }
}
