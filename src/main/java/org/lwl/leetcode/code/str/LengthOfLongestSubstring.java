package org.lwl.leetcode.code.str;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: RustLi
 * @date: 2026/8/27
 * @description: 无重复字符的最长子串（LeetCode 3）
 *
 *  给定一个字符串 s，找出其中不含有重复字符的 最长子串 的长度。
 *  注意：要求的是连续"子串"，不是子序列。
 *
 *  核心思想：滑动窗口 + 哈希表。
 *  维护窗口 [l, r]，保证窗口内字符互不重复；右指针 r 不断右扩，
 *  一旦遇到重复字符，就把左指针 l 直接跳到该字符上一次出现位置的下一位，
 *  窗口永远保持无重复，每次右扩后取窗口长度 r - l + 1 更新答案。
 *
 *  demo 示例1：s = "abcabcbb" -> 最长子串 "abc"，长度 3
 *  demo 示例2：s = "bbbbb"    -> 最长子串 "b"，长度 1
 *  demo 示例3：s = "pwwkew"   -> 最长子串 "wke"（或 "kew"），长度 3
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        LengthOfLongestSubstring solution = new LengthOfLongestSubstring();

        // 例1：经典重复，"abc" 后遇到 a 重复，答案 3
        System.out.println("\"abcabcbb\" -> " + solution.lengthOfLongestSubstring("abcabcbb") + "（期望 3）");
        // 例2：全相同字符，答案 1
        System.out.println("\"bbbbb\"    -> " + solution.lengthOfLongestSubstring("bbbbb") + "（期望 1）");
        // 例3：重复字符在窗口内不同位置，答案 3
        System.out.println("\"pwwkew\"   -> " + solution.lengthOfLongestSubstring("pwwkew") + "（期望 3）");
        // 例4：空串，答案 0
        System.out.println("\"\"         -> " + solution.lengthOfLongestSubstring("") + "（期望 0）");
        // 例5：单字符，答案 1
        System.out.println("\"au\"       -> " + solution.lengthOfLongestSubstring("au") + "（期望 2）");
    }

    /**
     * des: 求无重复字符的最长子串长度
     *
     * map 保存每个字符最近一次出现的下标；l 为窗口左边界，r 为右边界。
     * 当当前字符 c 已在窗口内（map.get(c) >= l）时，说明 [l, r] 出现重复，
     * 左边界直接跳到 c 上次出现位置的下一位，保证窗口始终无重复。
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int l = 0, ret = 0;
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            Integer lastIndex = map.get(c);
            // 该字符上一次出现的位置还在窗口内，说明产生了重复，收缩左边界
            if (lastIndex != null && lastIndex >= l) {
                l = lastIndex + 1;
            }
            map.put(c, r);
            // 当前窗口 [l, r] 必为无重复子串，更新最大长度
            ret = Math.max(ret, r - l + 1);
        }
        return ret;
    }
}
