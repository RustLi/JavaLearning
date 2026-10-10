package org.lwl.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 3 - 无重复字符的最长子串
 *
 * 题目：给定一个字符串 s，请找出其中不含有重复字符的「最长子串」的长度。
 *       注意区分「子串」与「子序列」：
 *         子串（substring）必须连续，例如 "abc" 的子串有 "a"、"ab"、"abc"、"bc" 等；
 *         子序列（subsequence）可以不连续，本题求的是**连续**的子串。
 *
 * 思路：滑动窗口（同向双指针）。
 *       把 left、right 之间的 [left, right] 想象成一个窗口，窗口内始终保持「无重复字符」这一性质。
 *       每一步让 right 向右扩一位，把新字符纳入窗口：
 *         1) 若该字符之前没出现过 —— 窗口依然合法，用当前窗口长度更新答案；
 *         2) 若该字符之前出现过，且出现位置 p 落在窗口内（p >= left）——
 *            说明窗口内有两个相同字符，需要把 left 直接跳到 p + 1，把旧的重复字符连同它左边的内容一起丢掉；
 *         3) 若该字符之前出现过，但位置 p 在窗口左边（p < left）—— 那是窗口外的历史记录，不影响当前窗口，
 *            left 不能回退，所以取 Math.max(left, p + 1) 保证 left 只前进不后退。
 *       每次用当前字符的下标覆盖 map 中的记录，保证 map 里存的永远是「最近一次出现的位置」。
 *
 *       窗口的两端都只会向右移动，总共走 2n 步，因此是 O(n) 的线性算法。
 *
 * 时间复杂度 O(n)：right 与 left 各自最多走 n 步，map 的存取均为 O(1)
 * 空间复杂度 O(k)：k 为字符集大小（窗口内不同字符的个数），最坏 O(n)
 */
public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // key: 字符, value: 该字符最近一次出现的下标
        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0;
        int max = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (lastIndex.containsKey(c)) {
                // 出现重复：left 跳到重复字符的下一位；用 max 防止 left 回退
                // （若上次出现在窗口左边，那是过期记录，不能把 left 拉回去）
                left = Math.max(left, lastIndex.get(c) + 1);
            }

            lastIndex.put(c, right);
            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    // 暴力解法：枚举所有子串并逐一判断是否无重复，用于对照验证答案
    public int lengthOfLongestSubstringBruteForce(String s) {
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (hasNoDuplicate(s, i, j)) {
                    max = Math.max(max, j - i + 1);
                } else {
                    // 以 i 开头的子串一旦出现重复，再往右只会继续重复，可直接跳出内层循环
                    break;
                }
            }
        }
        return max;
    }

    private boolean hasNoDuplicate(String s, int start, int end) {
        java.util.Set<Character> seen = new java.util.HashSet<>();
        for (int i = start; i <= end; i++) {
            if (!seen.add(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 测试用例
    public static void main(String[] args) {
        LengthOfLongestSubstring solution = new LengthOfLongestSubstring();

        // 示例 1："abcabcbb" -> "abc"，长度 3
        System.out.println("示例1: " + solution.lengthOfLongestSubstring("abcabcbb"));

        // 示例 2："bbbbb" -> "b"，长度 1
        System.out.println("示例2: " + solution.lengthOfLongestSubstring("bbbbb"));

        // 示例 3："pwwkew" -> "wke"，长度 3（注意 "pwke" 是子序列而非子串）
        System.out.println("示例3: " + solution.lengthOfLongestSubstring("pwwkew"));

        // 边界：空字符串
        System.out.println("空串: " + solution.lengthOfLongestSubstring(""));

        // 边界：单个字符
        System.out.println("单字符: " + solution.lengthOfLongestSubstring("a"));

        // 边界：全部不重复
        System.out.println("全不重复: " + solution.lengthOfLongestSubstring("abcdefg"));

        // 关键用例：重复字符出现在窗口左边，left 不能回退
        // "abba"：right 走到 3 时，a 上次出现在下标 0，若直接 left = 0+1 = 1 就错了，答案应为 2
        System.out.println("abba: " + solution.lengthOfLongestSubstring("abba"));

        // 对照验证：滑动窗口结果应与暴力解法一致
        String[] cases = {"abcabcbb", "bbbbb", "pwwkew", "abba", "dvdf", "tmmzuxt"};
        for (String str : cases) {
            int fast = solution.lengthOfLongestSubstring(str);
            int slow = solution.lengthOfLongestSubstringBruteForce(str);
            System.out.println("对照 [" + str + "] 滑动窗口=" + fast + " 暴力=" + slow
                    + (fast == slow ? " 一致" : " 不一致!"));
        }
    }
}
