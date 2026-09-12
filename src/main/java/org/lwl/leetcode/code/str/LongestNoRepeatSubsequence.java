package org.lwl.leetcode.code.str;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: RustLi
 * @date: 2026/8/27
 * @description: 无重复字符的最长子序列（对比 LeetCode 3 的变式）
 *
 *  给定字符串 s，找出其中不含有重复字符的最长子序列长度。
 *  子序列不要求连续，只需保持相对顺序、允许跳过任意字符。
 *
 *  核心思想：子序列可以随意跳过字符，因此只要某字符在原串中出现过，
 *  就一定能把它选进子序列（挑它任意一次出现的位置）。
 *  所以最长无重复子序列 = 原串中所有"不同字符"各选一个，
 *  答案 = 不同字符的种类数，用 HashSet 去重统计即可。
 *
 *  对比同一个输入下两种定义的差异：
 *    s = "pwwkew"
 *      无重复最长【子串】（连续）  = "wke"，长度 3
 *      无重复最长【子序列】（不连续）= "pwke"，长度 4
 *  可见"连续"的限制让子串答案更小，跳过了约束的子序列直接取字符种类数。
 */
public class LongestNoRepeatSubsequence {

    public static void main(String[] args) {
        LongestNoRepeatSubsequence solution = new LongestNoRepeatSubsequence();

        // 对比：同一输入下，子串（连续）vs 子序列（不连续）的答案
        String[] tests = {"abcabcbb", "bbbbb", "pwwkew", "", "au"};
        LengthOfLongestSubstring sub = new LengthOfLongestSubstring();
        for (String s : tests) {
            System.out.printf("\"%s\"  子串长度 = %d，子序列长度 = %d%n",
                    s, sub.lengthOfLongestSubstring(s), solution.longestNoRepeatSubsequence(s));
        }
    }

    /**
     * des: 求无重复字符的最长子序列长度
     *
     * 每个字符最多选一次，且一定能被选进子序列，
     * 故答案 = 不同字符的种类数。
     */
    public int longestNoRepeatSubsequence(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }
        return set.size();
    }
}
