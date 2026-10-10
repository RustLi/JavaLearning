package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 49 - 字母异位词分组（提交时将类名改为 Solution）
 * <p>
 * 题目：给你一个字符串数组 strs，请你将「字母异位词」组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词是指由相同字母、相同数量、仅排列顺序不同构成的字符串（例如 "eat" 与 "tea"、"ate"）。
 * <p>
 * 关键理解一：本题的敌人是「顺序」这个噪声，破题点是给每个字符串算一个「与顺序无关的签名」。
 * 异位词之间唯一的区别就是排列顺序，那么只要找到一种「把顺序抹掉」的变换 f，
 * 使得「互为异位词」等价于「f(s) 相等」，问题就退化成最朴素的「按 f(s) 分组」——
 * 一次遍历 + 一张哈希表即可，与 LeetCode 1「两数之和」里用哈希表把「找配对」降成 O(1) 是同一个套路。
 * 所以本题真正要设计的不是遍历逻辑，而是这个签名函数 f 本身。
 * <p>
 * 关键理解二：签名必须「无歧义」，否则会静默地错误合并分组。
 * 一个常见的错误直觉是拿「字符的 ASCII 码之和」当签名，因为它同样与顺序无关，看起来完全够用。
 * 但它是错的：'a' + 'd' = 97 + 100 = 197，而 'b' + 'c' = 98 + 99 = 197，
 * 于是 "ad" 与 "bc" 会被错误地分到同一组——它们显然不是异位词。同理，字符异或也不行：
 * "aa" 与 "bb" 的异或结果都是 0，同样会被误合并。
 * 教训是：签名必须是「完整信息」的单射（不同等价类必得到不同签名），
 * 任何「压缩成一个数」的做法都要先自问一句——会不会把不同的字母分布压成同一个值？
 * <p>
 * 关键理解三：签名的选法直接决定复杂度。
 * - 排序签名：把字符串排序后作为键。异位词排序后必然完全相同，思路最直白，代价是每次排序 O(k log k)。
 * - 计数签名：用一个长度 26 的数组记录各字母出现次数，再拼成键。避开了排序，是 O(k)，本题的效率最优解。
 * - 质数乘积签名：给 26 个字母各分配一个质数，按键相乘。理论上由唯一分解定理保证无冲突，
 * 但乘积会溢出 long 而静默出错（见方法三注释），属于「看着聪明、实际不能用」的典型。
 * <p>
 * 核心思路（计数签名，推荐）：为每个字符串生成计数键，用哈希表把键相同的字符串聚在一起。
 * 1. 遍历 strs，对每个字符串统计 26 个字母的出现次数；
 * 2. 把计数数组序列化成一个字符串键（注意必须带分隔符，理由见方法二注释）；
 * 3. 以该键在哈希表中查组，没有就新建一个空组，然后把原字符串放进该组；
 * 4. 遍历结束后，哈希表所有 value 就是全部异位词组，直接收拢返回。
 * <p>
 * 时间复杂度 O(n·k)：n 为字符串个数，k 为字符串的最大长度。
 * 每个字符串只需扫一遍统计计数（O(k)），再拼一个长度固定为 O(26) = O(1) 的键，
 * 哈希查表均摊 O(1)，故总时间为 O(n·k)。对比排序法，省掉了每串一次排序的 log k 因子。
 * 空间复杂度 O(n·k)：哈希表需保存全部 n 个字符串的内容，总计 O(n·k)；
 * 此外每个字符串临时的计数数组为 O(26) = O(1)，可忽略。
 * <p>
 * 举一反三：
 * 1. 「提取顺序无关的签名」是字符串分组题的通法。LeetCode 242「有效的字母异位词」不过是本题退化为两串比较的版本，
 *    同样可以用计数数组在 O(k) 内判定；若限定字符集为小写字母，26 长度数组是标准配套工具。
 * 2. 若把「异位词」的定义放宽成「字符可重排后构成回文」（LeetCode 266 / 剑指 Offer II 018），
 *    签名就从「计数数组」退化为「出现奇数次的字母个数是否 ≤ 1」，是同一思想的变体。
 * 3. 更一般地，凡是「把一堆对象按某个等价关系归类」的题，都可以套用同一范式：
 *    找到一个能唯一标识等价类的规范形式（canonical form）作键，再用哈希表聚合。
 *    例如 LeetCode 249「移位字符串分组」的签名是「相邻字符差值的序列」，
 *    LeetCode 128「最长连续序列」用哈希集合替代分组，本质相通。
 */
public class GroupAnagrams {

    // ============ 方法一：排序签名（时间 O(n·k log k)，最容易想到） ============

    /**
     * des: 把每个字符串的字符排序，排序结果相同的即为异位词
     * <p>
     * 为什么排序能抹掉顺序？因为异位词之间是同一个「字母多重集合」的不同排列，
     * 排序是多重集合的规范形式——同一个多重集合排序后必然得到唯一相同的字符串。
     * 反过来说，排序结果相同就意味着字母种类和数量都相同，即互为异位词。等价关系成立，签名有效。
     * <p>
     * 时间 O(n·k log k)，空间 O(n·k)。代码最短，是面试时最稳妥的第一版答案；
     * 缺点是每串都要排一次序，当 k 较大时不如方法二的线性计数。
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String str : strs) {
            // 把字符串拆成字符数组后排序，得到与原始顺序无关的规范形式
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            // 该签名首次出现则新建一个分组；无论新旧，最后都把原串（注意是原串，不是排序后的串）加入分组
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        // 哈希表的 values 即全部分组，题目允许任意顺序，故直接收拢返回
        return new ArrayList<>(groups.values());
    }

    // ============ 方法二：计数签名（推荐，时间 O(n·k) 空间 O(n·k)） ============

    /**
     * des: 用 26 位计数数组刻画字母分布，序列化成键，避免排序开销
     * <p>
     * 计数数组是「字母多重集合」的另一种规范形式，它比排序后的字符串携带的信息更完整、更显式，
     * 且构造过程只需一次线性扫描。因为本题明确规定字符串仅由小写英文字母组成，
     * 所以数组长度固定为 26，下标 c - 'a' 即字母的编号，天然可枚举。
     * <p>
     * 拼接键时务必带分隔符，这不是洁癖而是正确性要求。若直接拼接数字，
     * 计数 [1, 11, 0, ...] 与 [11, 1, 0, ...] 都会得到 "1110..."，
     * 于是「1 个 a + 11 个 b」和「11 个 a + 1 个 b」这两个毫无关系的字符串会被错误地并成一组。
     * 加上 '#' 分隔后分别是 "1#11#0#..." 与 "11#1#0#..."，歧义消失。
     * 这与方法一注释里提到的「字符和签名」是同一类错误：签名一旦有歧义，分组就会静默出错。
     * <p>
     * 时间 O(n·k)，空间 O(n·k)，是本题的最优解。
     */
    public List<List<String>> groupAnagramsByCount(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String str : strs) {
            // 统计每个小写字母在本串中的出现次数
            int[] count = new int[26];
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }

            // 用分隔符把计数串起来，保证不同计数数组绝不会拼出同一个键
            StringBuilder keyBuilder = new StringBuilder();
            for (int times : count) {
                keyBuilder.append(times).append('#');
            }

            groups.computeIfAbsent(keyBuilder.toString(), k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(groups.values());
    }

    // ============ 方法三：质数乘积签名（仅供对照，存在溢出缺陷，面试建议主动指出） ============

    /** 26 个字母对应的互不相同质数，'a' -> 2，'b' -> 3，依此类推 */
    private static final int[] PRIMES = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101
    };

    /**
     * des: 把字符串映射为各字母所对应质数的乘积，靠算术基本定理保证签名唯一
     * <p>
     * 这个思路很漂亮：由「唯一分解定理」，两个乘积相等当且仅当两边的质因数分解完全相同，
     * 即两个字符串的字母种类与数量完全相同——正是异位词的定义，且签名天然是一个数字而不是字符串。
     * <p>
     * 但它有一个致命缺陷：乘积会溢出。26 个质数中最大的是 101，
     * 只要字符串稍长（例如 20 个 'z'），101^20 就远超 long 的表示范围，乘法会静默回绕成错误的值。
     * 溢出不抛异常、不报错，只会让两个本不相干的字符串算出同一个键，
     * 最终给出「结构看起来完全正常、内容却是错的」分组结果——这类静默失败在线上最难排查。
     * 因此，只有在能事先保证字符串极短（乘积不溢出）时才可用，本题不满足该条件。
     * 写在这里是为了说明：签名不仅要「无歧义」，还要「在数据范围内可安全计算」。
     */
    public List<List<String>> groupAnagramsByPrime(String[] strs) {
        Map<Long, List<String>> groups = new HashMap<>();

        for (String str : strs) {
            long key = 1L;
            for (char c : str.toCharArray()) {
                key *= PRIMES[c - 'a'];
            }
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(groups.values());
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();

        // ---------- 用例 1：官方示例，三组异位词 ----------
        check(solution, new String[]{"eat", "tea", "tan", "ate", "nat", "bat"},
                Arrays.asList(
                        Arrays.asList("ate", "eat", "tea"),
                        Arrays.asList("nat", "tan"),
                        Arrays.asList("bat")));

        // ---------- 用例 2：官方示例，只有一个空串，空串之间互为异位词 ----------
        check(solution, new String[]{""}, Collections.singletonList(Collections.singletonList("")));

        // ---------- 用例 3：官方示例，只有一个字符 ----------
        check(solution, new String[]{"a"}, Collections.singletonList(Collections.singletonList("a")));

        // ---------- 用例 4：空数组，应返回空列表而非 null ----------
        check(solution, new String[]{}, new ArrayList<>());

        // ---------- 用例 5：多个空串应归为一组，验证空串不会把键构造逻辑搞崩 ----------
        check(solution, new String[]{"", ""}, Collections.singletonList(Arrays.asList("", "")));

        // ---------- 用例 6：全部互为异位词，最终只有一组 ----------
        check(solution, new String[]{"abc", "bca", "cab", "acb", "bac", "cba"},
                Collections.singletonList(Arrays.asList("abc", "acb", "bac", "bca", "cab", "cba")));

        // ---------- 用例 7：两两都不是异位词，应分成 3 组 ----------
        check(solution, new String[]{"abc", "def", "ghi"},
                Arrays.asList(
                        Collections.singletonList("abc"),
                        Collections.singletonList("def"),
                        Collections.singletonList("ghi")));

        // ---------- 用例 8：重复出现的相同字符串属于同一组，验证重复项被正确累加 ----------
        check(solution, new String[]{"abc", "abc"},
                Collections.singletonList(Arrays.asList("abc", "abc")));

        // ---------- 用例 9：关键反例——"ad" 与 "bc" 的 ASCII 之和同为 197，但并非异位词 ----------
        // 这一组用来验证：若有人用「字符和」当签名，这里必然把两者错误合并
        check(solution, new String[]{"ad", "bc", "da", "cb"},
                Arrays.asList(Arrays.asList("ad", "da"), Arrays.asList("bc", "cb")));

        // ---------- 用例 10：关键反例——"aa" 与 "bb" 的字符异或同为 0，但并非异位词 ----------
        check(solution, new String[]{"aa", "bb"},
                Arrays.asList(Collections.singletonList("aa"), Collections.singletonList("bb")));

        // ---------- 用例 11：验证计数键的分隔符确实必要 ----------
        // "a" + 11 个 "b"（键 1#11#...）与 11 个 "a" + "b"（键 11#1#...），不加分隔符会拼出同一个键
        check(solution, new String[]{"abbbbbbbbbbb", "aaaaaaaaaaab"},
                Arrays.asList(
                        Collections.singletonList("abbbbbbbbbbb"),
                        Collections.singletonList("aaaaaaaaaaab")));

        // ---------- 用例 12：字母个数分布相同但字母不同，验证签名比较的是分布而非长度 ----------
        check(solution, new String[]{"aabb", "abab", "bbaa", "ccdd"},
                Arrays.asList(Arrays.asList("aabb", "abab", "bbaa"), Collections.singletonList("ccdd")));

        // ---------- 用例 13：含重复字母且长度较长，压力验证计数法在 k 较大时依然正确 ----------
        check(solution, new String[]{"aaaaaaaaaaaaaaaaaaaab", "baaaaaaaaaaaaaaaaaaaa"},
                Collections.singletonList(Arrays.asList("aaaaaaaaaaaaaaaaaaaab", "baaaaaaaaaaaaaaaaaaaa")));

        // ---------- 方法一演示：排序签名逐步展开 ----------
        demonstrateSortKey();

        // ---------- 陷阱演示：质数乘积签名为何不能用 ----------
        demonstratePrimeOverflow();
    }

    /** des: 两种正规解法跑同一组用例并比对结果，分组顺序不限，故先归一化再比较 */
    private static void check(GroupAnagrams solution, String[] strs, List<List<String>> expected) {
        List<List<String>> r1 = solution.groupAnagrams(strs);
        List<List<String>> r2 = solution.groupAnagramsByCount(strs);

        String exp = normalize(expected);
        String got1 = normalize(r1);
        String got2 = normalize(r2);

        // 空数组与「只含一个空串的数组」用 toString 打印出来都是 []，故补上元素个数加以区分
        System.out.printf("strs = %s（共 %d 个字符串）%n", Arrays.toString(strs), strs.length);
        System.out.printf("  期望分组     : %s%n", exp);
        System.out.printf("  排序签名     : %s%s%n", got1, got1.equals(exp) ? "  ✅" : "  ❌");
        System.out.printf("  计数签名     : %s%s%n", got2, got2.equals(exp) ? "  ✅" : "  ❌");
        System.out.println(got1.equals(exp) && got2.equals(exp) ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /**
     * des: 把分组结果归一化成「组内有序、组间有序」的字符串，消除顺序差异后再比对
     * <p>
     * 题目明确允许任意顺序返回，直接比较 List 会因为哈希表遍历顺序不同而误判失败。
     */
    private static String normalize(List<List<String>> groups) {
        List<List<String>> copy = new ArrayList<>();
        for (List<String> group : groups) {
            List<String> sorted = new ArrayList<>(group);
            Collections.sort(sorted);
            copy.add(sorted);
        }
        copy.sort(Comparator.comparing(Object::toString));
        return copy.toString();
    }

    /** des: 把方法一的排序签名逐步打印出来，直观展示「顺序」是如何被抹掉的 */
    private static void demonstrateSortKey() {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        GroupAnagrams solution = new GroupAnagrams();

        System.out.println("---------- 方法一演示：排序签名 ----------");
        System.out.printf("%-10s %-20s %-12s %s%n", "原字符串", "原始字符数组", "排序后", "签名（键）");
        System.out.println("-----------------------------------------------------------");

        Map<String, List<String>> groups = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            String beforeSort = Arrays.toString(chars);

            Arrays.sort(chars);
            String key = new String(chars);

            System.out.printf("%-10s %-20s %-12s %s%n", "\"" + str + "\"", beforeSort, "\"" + key + "\"", "\"" + key + "\"");

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println("相同签名的字符串被折叠进同一组：");
        for (Map.Entry<String, List<String>> entry : groups.entrySet()) {
            System.out.printf("  键 %-6s -> %s%n", "\"" + entry.getKey() + "\"", entry.getValue());
        }
        System.out.printf("最终分组（共 %d 组）: %s%n",
                groups.size(), normalize(solution.groupAnagrams(strs)));
        System.out.println();
    }

    /** des: 演示质数乘积签名的溢出问题——它不是慢，而是会静默算错 */
    private static void demonstratePrimeOverflow() {
        System.out.println("---------- 陷阱演示：质数乘积签名的溢出 ----------");

        // 构造一个 20 个 'z' 的字符串（用循环拼接而不是字面量，避免手数字符个数出错）
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append('z');
        }
        String str = sb.toString();

        long key = 1L;
        for (char c : str.toCharArray()) {
            key *= PRIMES[c - 'a'];
        }

        System.out.printf("字符串 \"%s\"（20 个 'z'）%n", str);
        System.out.printf("  质数乘积签名 = %d%n", key);
        System.out.println("  该值已溢出 long 并回绕，无法反推出原始字母分布。");
        System.out.println("  一旦两个不同的字符串溢出到同一个值，就会被错误地并入同一组，且不报任何错。");
        System.out.println("  结论：质数乘积签名只在字符串极短时成立，本题不适用。");
        System.out.println();
    }
}
