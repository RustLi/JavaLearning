package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 438 - 找到字符串中所有字母异位词（提交时将类名改为 Solution）
 * <p>
 * 题目：给定两个字符串 s 和 p，找到 s 中所有 p 的「异位词」的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 异位词是指由相同字母、相同数量、仅排列顺序不同构成的字符串（例如 "abc" 与 "cba"、"bac"）。
 * 注意求的是**子串**（连续），而不是子序列（可不连续）。
 * <p>
 * 关键理解一：本题是 LeetCode 49「字母异位词分组」的「在线版」，签名思想直接复用，但用法必须换。
 * 分组题里我们可以慢悠悠地给每个字符串排序再分组；本题则要求我们在 s 上「找出所有」异位词子串，
 * 若对每个起点都重新排序，代价是 O(n·m log m)，白白浪费了「相邻窗口高度重叠」这一宝贵结构。
 * 所以签名依然用「计数数组」，但对象从「整串」变成「一个长度为 m 的窗口」，且必须让相邻窗口之间能**增量更新**。
 * <p>
 * 关键理解二：窗口长度是固定的，这决定了用「定长滑动窗口」而不是普通的伸缩窗口。
 * 因为 p 的长度为 m，任何与 p 互为异位词的子串，长度必然也是 m——长度对不上直接出局。
 * 于是搜索空间从「所有 O(n²) 个子串」坍缩为「O(n) 个长度为 m 的窗口」，每个窗口的起点 i 从 0 一路走到 n-m。
 * 更进一步，窗口从 i 滑到 i+1 时，只有「左端一个字符出、右端一个字符进」两处变化，
 * 因此窗口的计数信息可以用 O(1) 的时间增量维护，而不必每次重建——这是滑窗类题目的通用红利。
 * <p>
 * 关键理解三：不要每次拿计数数组逐位比对，那样会平白多出一个 26 的常数因子；用「差异计数」把它压成 O(1)。
 * 最直白的写法是维护 need[26] 与 window[26]，每步用 Arrays.equals 比对，正确但每步 O(26)。
 * 更好的做法是只维护一个数组 diff[26]（含义为「窗口计数 - p 计数」），再额外维护一个整数 diffCnt，
 * 表示 diff 中「非零元素的个数」。于是判定的充要条件被压缩成一句极廉价的比较：
 *         窗口是 p 的异位词  ⟺  diff 全部为 0  ⟺  diffCnt == 0
 * 因为每次滑动只改动 diff 中的两个位置（左端字符 -1、右端字符 +1），diffCnt 也只需跟着这两个位置做 ±1 的微调，
 * 单步成本恒为 O(1)，与字符集大小无关。这正是「维护一个可增量更新的判定量」这一常见优化套路。
 * <p>
 * 核心思路（定长滑窗 + 差异计数，推荐）：
 * 1. 若 s 比 p 短，直接返回空列表（连一个窗口都放不下）；
 * 2. 构造 diff：把 p 的每个字符计数**减去**、把 s 首个窗口 s[0..m-1] 的每个字符计数**加上**，两者同处一个 26 长度数组；
 * 3. 数出 diff 中非零元素的个数 diffCnt，若为 0 则下标 0 是答案；
 * 4. i 从 m 遍历到 n-1：左端字符 s[i-m] 滑出（diff 对应位 -1），右端字符 s[i] 滑入（diff 对应位 +1），
 *    每次改动后同步修正 diffCnt，若 diffCnt == 0 则 i-m+1 是答案；
 * 5. 返回收集到的全部起始下标（天然按从小到大有序）。
 * <p>
 * 时间复杂度 O(n + m)：构造初始 diff 与 diffCnt 各 O(m) 与 O(26) = O(1)，
 * 之后每个窗口仅做常数次数组读写与整数比较，共 n-m 步，故整体 O(n)（26 视为常数）。
 * 相比方法一，省掉了每步一次 O(26) 的数组比较。
 * 空间复杂度 O(1)：只有 diff[26]、diffCnt 等固定大小的辅助量，与输入规模无关（返回值不计入）。
 * <p>
 * 举一反三：
 * 1. 「定长窗口 + 增量维护判定量」是一大类题的骨架。LeetCode 567「字符串的排列」问的是「s2 是否含 s1 的异位词子串」，
 *    把本题的「收集全部下标」改成「命中第一个即返回 true」即可，代码几乎不动；
 *    LeetCode 76「最小覆盖子串」则是把它推广成「窗口长度可变、判定条件为覆盖」，需要额外维护一个 valid 计数，
 *    但「增量维护 + O(1) 判定」的骨架完全一致。
 * 2. 判定量的选择往往决定常数大小。本题用「非零元素个数」，LeetCode 76 用「已满足需求的字符种类数」，
 *    LeetCode 3「无重复字符的最长子串」用「重复字符的位置表」——都是把冗长的整体比较压缩成一个可增量更新的标量。
 *    遇到「每步都要判断某个整体状态是否成立」的题，先问自己：这个状态能不能用一两个整数近似地代表？
 * 3. 字符串计数类问题中，若字符集是固定的（如仅小写字母），26 长度的 int 数组是标准工具，
 *    它比 HashMap 更快也更省内存；只有字符集未知或极大（如 Unicode）时才退回哈希表。
 *    这与 LeetCode 49 中「计数签名优于排序签名」的取舍是同一个道理。
 */
public class FindAnagrams {

    // ============ 方法一：定长滑窗 + 计数数组逐位比较（时间 O(n·26)，最直观） ============

    /**
     * des: 维护 need（p 的计数）与 window（当前窗口的计数），每步用 Arrays.equals 比对
     * <p>
     * 这是本题最容易想到、也最不容易写错的版本：窗口的含义一目了然，判定条件就是两个数组相等。
     * 代价是每一步都要比较 26 个位置，即便绝大多数位置从头到尾都没变过。
     * 放在这里作为方法二的「基线」，用来对照验证优化版的正确性。
     * <p>
     * 时间 O(n·26)（记作 O(n) 但常数更大），空间 O(1)。
     */
    public List<Integer> findAnagramsByCount(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int m = p.length();

        // 窗口都放不下，不可能存在异位词子串
        if (n < m) {
            return result;
        }

        // need 记录 p 中各字母的出现次数，是窗口需要匹配的「目标分布」
        int[] need = new int[26];
        for (int i = 0; i < m; i++) {
            need[p.charAt(i) - 'a']++;
        }

        // 首个窗口 s[0..m-1] 的分布
        int[] window = new int[26];
        for (int i = 0; i < m; i++) {
            window[s.charAt(i) - 'a']++;
        }
        if (Arrays.equals(need, window)) {
            result.add(0);
        }

        // 窗口整体右移一格：右边进一个字符，左边出一个字符
        for (int i = m; i < n; i++) {
            window[s.charAt(i) - 'a']++;
            window[s.charAt(i - m) - 'a']--;
            if (Arrays.equals(need, window)) {
                result.add(i - m + 1);
            }
        }

        return result;
    }

    // ============ 方法二：定长滑窗 + 差异计数（推荐，时间 O(n) 空间 O(1)） ============

    /**
     * des: 用单个 diff 数组 + 非零计数 diffCnt，把每步判定压成「diffCnt == 0」这一次整数比较
     * <p>
     * diff[c] 的定义是「当前窗口中字符 c 的个数」减去「p 中字符 c 的个数」。
     * 这个定义的好处是：判定条件直接就是「diff 全为 0」，不需要再准备第二个数组做逐位相减。
     * 而 diffCnt 记录 diff 中非零位置的个数，把「全为 0」翻译成「diffCnt 为 0」，一次比较就能判定。
     * <p>
     * 维护 diffCnt 的关键在于「先看变化前、再看变化后」这个固定套路（见 apply 方法）：
     * 一个位置的值从 0 变成非 0，说明非零位置多了一个，diffCnt 加一；
     * 从非 0 变成 0，说明非零位置少了一个，diffCnt 减一。
     * 其余情况（非 0 变非 0）不改变非零位置的个数，diffCnt 不动。
     * 只要 delta 恒为 ±1、不会跨过 0 以外的值，这个 O(1) 的维护就是精确的。
     * <p>
     * 时间 O(n)，空间 O(1)，是本题的最优解。
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int m = p.length();

        if (n < m) {
            return result;
        }

        // diff[c] = 窗口中 c 的个数 - p 中 c 的个数，全为 0 即窗口是 p 的异位词
        int[] diff = new int[26];
        for (int i = 0; i < m; i++) {
            diff[s.charAt(i) - 'a']++;   // 首个窗口的字符计入
            diff[p.charAt(i) - 'a']--;   // p 的字符作为被减数
        }

        // 数出 diff 中非零元素的个数，它就是后续每步唯一需要盯住的判定量
        int diffCnt = 0;
        for (int v : diff) {
            if (v != 0) {
                diffCnt++;
            }
        }
        if (diffCnt == 0) {
            result.add(0);
        }

        // 窗口右移一格：左端 s.charAt(i - m) 出，右端 s.charAt(i) 进
        for (int i = m; i < n; i++) {
            diffCnt = apply(diff, s.charAt(i - m) - 'a', -1, diffCnt);
            diffCnt = apply(diff, s.charAt(i) - 'a', 1, diffCnt);

            if (diffCnt == 0) {
                result.add(i - m + 1);
            }
        }

        return result;
    }

    /**
     * des: 把 diff[idx] 加上 delta，并同步维护「非零元素个数」diffCnt
     * <p>
     * 维护规则只有两条，覆盖全部情形：
     * 1) 变化前为 0 —— 该位置即将离开「零值」，非零个数 +1；
     * 2) 变化后为 0 —— 该位置回到了「零值」，非零个数 -1。
     * 当 delta == ±1 时，一个位置不可能既「变化前为 0」又「变化后为 0」（那要求 delta == 0），
     * 所以两条规则不会同时触发，也就不存在计重或漏计。
     * <p>
     * 注意此处绝不能写成「变化后为 0 就 -1，否则 +1」的简化版——
     * 那么「非 0 变非 0」的情形（例如 1 变成 2）会被误认为非零个数增加了，diffCnt 会一路虚高。
     */
    private int apply(int[] diff, int idx, int delta, int diffCnt) {
        if (diff[idx] == 0) {
            diffCnt++;
        }
        diff[idx] += delta;
        if (diff[idx] == 0) {
            diffCnt--;
        }
        return diffCnt;
    }

    // ============ 方法三：暴力枚举 + 排序（时间 O(n·m log m)，仅供对照验证） ============

    /**
     * des: 枚举每个长度为 m 的子串，排序后与排序后的 p 比较
     * <p>
     * 思路最朴素，也最不容易出错，适合当作「人肉对拍」的参照答案。
     * 但它完全无视了相邻窗口共享绝大部分字符这一事实，
     * 每个窗口都要重新取子串、重新排序，因此是本题最慢的写法，不应作为正式提交。
     * <p>
     * 时间 O(n·m log m)，空间 O(m)（每轮排序的临时数组）。
     */
    public List<Integer> findAnagramsBruteForce(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int m = p.length();

        if (n < m) {
            return result;
        }

        // 把 p 排序成规范形式，异位词排序后必然与它完全相同
        char[] pSorted = p.toCharArray();
        Arrays.sort(pSorted);

        for (int i = 0; i + m <= n; i++) {
            char[] window = s.substring(i, i + m).toCharArray();
            Arrays.sort(window);
            if (Arrays.equals(pSorted, window)) {
                result.add(i);
            }
        }

        return result;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        FindAnagrams solution = new FindAnagrams();

        // ---------- 官方示例 1：s = "cbaebabacd", p = "abc" ----------
        // 下标 0 的子串 "cba"、下标 6 的子串 "bac" 都是 "abc" 的异位词
        check(solution, "cbaebabacd", "abc", Arrays.asList(0, 6));

        // ---------- 官方示例 2：s = "abab", p = "ab" ----------
        // "ab"、"ba"、"ab" 三个窗口全部命中，答案 [0, 1, 2]
        check(solution, "abab", "ab", Arrays.asList(0, 1, 2));

        // ---------- 边界：s 比 p 短，一个窗口都放不下 ----------
        check(solution, "a", "ab", new ArrayList<>());

        // ---------- 边界：s 与 p 完全相同，唯一窗口即答案 ----------
        check(solution, "abc", "abc", Arrays.asList(0));

        // ---------- 边界：p 长度为 1，逐字符匹配 ----------
        check(solution, "abacb", "a", Arrays.asList(0, 2));

        // ---------- 边界：p 长度也为 1 且在 s 中一次都没出现 ----------
        check(solution, "aaaa", "b", new ArrayList<>());

        // ---------- 全同字符：每个窗口都命中，考验滑动时的增量维护 ----------
        check(solution, "aaaa", "aa", Arrays.asList(0, 1, 2));

        // ---------- 无解：字符集毫无交集 ----------
        check(solution, "abc", "def", new ArrayList<>());

        // ---------- 关键用例：窗口「擦肩而过」——差异只差一个字符，考验 diffCnt 的精确性 ----------
        // "abcde" 与 "abfde" 仅第三位不同，只有下标 0 命中
        check(solution, "abcde", "abfde", new ArrayList<>());

        // ---------- 关键用例：字母个数分布相同但字母不同，绝不能被误判为异位词 ----------
        // "aabb"（2 个 a + 2 个 b）与 p = "ccdd" 分布形状相同，但字母不同，不应命中
        check(solution, "aabb", "ccdd", new ArrayList<>());

        // ---------- 关键用例：p 自身含重复字符，且前三个窗口命中、后三个不命中 ----------
        // 窗口依次为 "aab"、"aba"、"baa"、"aac"、"aca"、"caa"，只有前三个是 "aab" 的异位词
        check(solution, "aabaacaa", "aab", Arrays.asList(0, 1, 2));

        // ---------- 关键用例：命中与不命中交替出现，考验滑动过程中 diffCnt 的反复归零 ----------
        // 窗口依次为 "aa"、"ab"、"ba"、"aa"，第 0 与第 3 个命中
        check(solution, "aabaa", "aa", Arrays.asList(0, 3));

        // ---------- 关键用例：答案只落在窗口序列的中部 ----------
        // 窗口长 4，只有下标 5 的 "abac" 是 "aabc" 的异位词
        check(solution, "cbaebabacd", "aabc", Arrays.asList(5));

        // ---------- 演示：把一个真实用例的每一步窗口状态打印出来 ----------
        demonstrateWindow("cbaebabacd", "abc");
    }

    /** des: 三种解法跑同一组用例并逐一比对，三者结果必须完全一致 */
    private static void check(FindAnagrams solution, String s, String p, List<Integer> expected) {
        List<Integer> fast = solution.findAnagrams(s, p);
        List<Integer> count = solution.findAnagramsByCount(s, p);
        List<Integer> slow = solution.findAnagramsBruteForce(s, p);

        boolean ok = fast.equals(expected) && count.equals(expected) && slow.equals(expected);

        System.out.printf("s = \"%s\", p = \"%s\"%n", s, p);
        System.out.printf("  期望       : %s%n", expected);
        System.out.printf("  差异计数法 : %s%s%n", fast, fast.equals(expected) ? "  ✅" : "  ❌");
        System.out.printf("  计数数组法 : %s%s%n", count, count.equals(expected) ? "  ✅" : "  ❌");
        System.out.printf("  暴力排序法 : %s%s%n", slow, slow.equals(expected) ? "  ✅" : "  ❌");
        System.out.println(ok ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /**
     * des: 以 s = "cbaebabacd", p = "abc" 为例，逐步打印窗口内容与 diffCnt，直观展示滑动窗口的增量更新
     */
    private static void demonstrateWindow(String s, String p) {
        int n = s.length();
        int m = p.length();

        System.out.println("---------- 演示：定长滑动窗口的每一步（s = \"" + s + "\", p = \"" + p + "\"） ----------");
        System.out.printf("%-8s %-14s %-10s %s%n", "起始下标", "窗口内容", "diffCnt", "是否异位词");
        System.out.println("-----------------------------------------------------");

        int[] diff = new int[26];
        for (int i = 0; i < m; i++) {
            diff[s.charAt(i) - 'a']++;
            diff[p.charAt(i) - 'a']--;
        }

        int diffCnt = 0;
        for (int v : diff) {
            if (v != 0) {
                diffCnt++;
            }
        }
        printWindowRow(s, 0, m, diffCnt);

        for (int i = m; i < n; i++) {
            diffCnt = applyStatic(diff, s.charAt(i - m) - 'a', -1, diffCnt);
            diffCnt = applyStatic(diff, s.charAt(i) - 'a', 1, diffCnt);
            printWindowRow(s, i - m + 1, m, diffCnt);
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("只有 diffCnt 归零的窗口才是 p 的异位词，其余窗口不论「长得像不像」都不算数。");
        System.out.println();
    }

    /** des: 打印某一步的窗口信息，与实例方法 apply 逻辑一致，仅用于演示 */
    private static void printWindowRow(String s, int start, int m, int diffCnt) {
        String window = s.substring(start, start + m);
        System.out.printf("%-8d %-14s %-10d %s%n", start, "\"" + window + "\"", diffCnt,
                diffCnt == 0 ? "✅ 命中" : "❌");
    }

    /** des: 与实例方法 apply 完全同构的静态版本，供演示方法调用 */
    private static int applyStatic(int[] diff, int idx, int delta, int diffCnt) {
        if (diff[idx] == 0) {
            diffCnt++;
        }
        diff[idx] += delta;
        if (diff[idx] == 0) {
            diffCnt--;
        }
        return diffCnt;
    }
}
