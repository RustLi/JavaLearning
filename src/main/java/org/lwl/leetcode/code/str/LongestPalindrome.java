package org.lwl.leetcode.code.str;

/**
 * LeetCode 5. 最长回文子串
 *
 * @author: RustLi
 * @date: 2026/7/21
 * @description:
 *  给你一个字符串 s，找到 s 中最长的 回文 子串。
 *
 *  【题目示例】
 *  示例 1：输入 s = "babad" -> 输出 "bab"（"aba" 同样是符合题意的答案）
 *  示例 2：输入 s = "cbbd"  -> 输出 "bb"
 *  提示：1 <= s.length <= 1000，s 仅由数字和英文字母组成。
 *
 *  【回文串的性质】
 *  回文串正读、反读完全相同，关键在于它存在一个"对称中心"：
 *    - 奇数长度回文（如 "aba"）：对称中心是中间单个字符 'b'；
 *    - 偶数长度回文（如 "abba"）：对称中心是中间两字符之间（"bb" 之间的空隙）。
 *
 *  【解题思路 —— 中心扩散法】
 *  枚举每一个位置 i 作为回文的对称中心，向左右两侧同时扩散：
 *    1. 以 i 为单字符中心扩散，对应奇数回文（helper(s, i, i)）；
 *    2. 以 i 与 i+1 之间的空隙为中心扩散，对应偶数回文（helper(s, i, i + 1)）。
 *  只要 s[left] == s[right] 就继续向外扩展，一旦两侧不等或越界就停止，
 *  此时以该中心为轴的最长回文区间为 [left + 1, right - 1]。
 *  用成员变量 start、end 记录全局最长回文的起止下标，遍历完全部中心后截取返回。
 *
 *  【复杂度分析】
 *  每个中心向外扩散最多 O(n)，中心共有 2n 个（n 个奇数中心 + n-1 个偶数中心），
 *  故时间复杂度 O(n²)；仅使用常数额外空间，空间复杂度 O(1)。
 *  对比动态规划 O(n²) 空间的做法，中心扩散省去了二维数组，空间上更优。
 *
 *  【相关知识点】双指针 / 字符串 / 动态规划
 **/
public class LongestPalindrome {
    /** 记录当前已找到的最长回文子串的起止下标（闭区间 [start, end]） */
    int start, end;

    /**
     * 求 s 中最长的回文子串（中心扩散法入口）。
     *
     * 思路：遍历字符串的每一个位置 i，
     * 把 i 同时当作两种对称中心去向外扩散 ——
     *   helper(s, i, i)     处理奇数长度回文（中心为单个字符）；
     *   helper(s, i, i + 1) 处理偶数长度回文（中心为两字符之间的空隙）。
     * 每次扩散都会尝试刷新全局最优 [start, end]，最终用 substring 截取。
     *
     * 边界：s 为 null 或长度小于 2 时（0 或 1 个字符），它本身就是最长回文，直接返回。
     *
     * @param s 输入字符串（1 <= s.length <= 1000，由数字和英文字母组成）
     * @return s 中的最长回文子串
     */
    public String longestPalindrome(String s) {
        if(s == null || s.length() < 2){
            return s;
        }
        // start/end 是实例字段：重置为 [0, 0]，保证同一实例重复调用时不受上一次结果影响
        start = 0;
        end = 0;
        for (int i = 0; i < s.length(); i++) {
            helper(s, i, i);      // 以 i 为中心，找奇数长度回文
            helper(s, i, i + 1);  // 以 i 与 i+1 的间隙为中心，找偶数长度回文
        }
        return s.substring(start, end + 1);
    }

    /**
     * 中心扩散：以 (left, right) 为回文中心起点，向左右两侧同时扩张。
     *
     * 中心起点的两种传法对应两类回文：
     *   - left == right        ：单字符中心，扩散出奇数回文（如 "aba" 以 'b' 为中心）；
     *   - right == left + 1    ：双字符间隙中心，扩散出偶数回文（如 "abba" 以 "bb" 间隙为中心）。
     *
     * @param s     原字符串
     * @param left  中心左起点（含）
     * @param right 中心右起点（含）
     */
    private void helper(String s, int left, int right){
        // 两侧字符相等就继续外扩，直到越界或两侧字符不同，此时找到以该中心的最长回文
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        // 跳出循环后，真正构成回文的区间是 (left, right) 开区间内的 [left + 1, right - 1]，
        // 该回文长度为 right - left - 1。
        // 用它与当前已记录的最长回文比较，若更长则更新 [start, end]：
        // - 当前已记录长度 = end - start + 1
        // - 本次找到长度   = right - left - 1
        // - 需要更新即本次更长：right - left - 1 > end - start + 1
        // - 两边同减 1 得：right - left - 2 > end - start（即下方判断条件）
        if (end - start < right - left - 2){
            start = left + 1;
            end = right - 1;
        }
    }

    public static void main(String[] args) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        // 示例1：最长奇数回文 "bab"（或 "aba"）
        System.out.println("\"babad\" 的最长回文子串 = " + longestPalindrome.longestPalindrome("babad"));
        // 示例2：最长偶数回文 "bb"
        System.out.println("\"cbbd\"  的最长回文子串 = " + longestPalindrome.longestPalindrome("cbbd"));
        // 整个串本身就是回文，直接返回整串
        System.out.println("\"abba\"  的最长回文子串 = " + longestPalindrome.longestPalindrome("abba"));
        // 单字符：长度 < 2，直接原样返回
        System.out.println("\"a\"     的最长回文子串 = " + longestPalindrome.longestPalindrome("a"));
    }
}
