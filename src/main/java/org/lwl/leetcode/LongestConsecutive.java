package org.lwl.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 128 - 最长连续序列（提交时将类名改为 Solution）
 * <p>
 * 题目：给定一个未排序的整数数组 nums，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 关键理解：先把「题目到底在问什么」拆开，否则极容易做错方向。
 * 1. 「数字连续」指的是数值上连续（x, x+1, x+2 ...），不是下标连续，也不是原数组里的顺序连续。
 *    所以 [100, 4, 200, 1, 3, 2] 的答案是 4，对应子序列 {1, 2, 3, 4}——它们在原数组里被拆得七零八落，但数值上连成一片。
 * 2. 「不要求元素在原数组中连续」这句话是在明确授权：可以任意挑元素、任意重排，只关心值域。
 *    既然如此，原数组的「顺序」就是纯粹的无用信息（这正是可以丢开下标、只看数值的前提）。
 * 3. 重复元素不影响结果：{1, 1, 2} 的最长连续序列仍是 2（{1, 2}），序列是「集合」语义而非「多重集」语义。
 *    这一点决定了后面可以用 HashSet 去重，而不会改变答案。
 * 4. 空数组返回 0；单个元素返回 1（它自己就是一个长度为 1 的连续序列）。
 * <p>
 * 难点在哪：为什么题目要专门强调 O(n)？
 * 因为存在一个「想都不用想」的答案——排序后扫一遍，时间 O(n log n)。
 * 这个解法完全正确、也足够简单，但它不是线性。
 * 题目要求 O(n)，本质上是在说：不许排序，你得靠「哈希表 O(1) 查找」把 log n 那一项消掉。
 * 而哈希表本身只能告诉你「某个数在不在」，它不会告诉你「谁和谁挨着」——
 * 所以全部技巧都集中在：怎么在不排序的前提下，把散落的数字「串」成序列，且只串一遍。
 * <p>
 * 核心思路：HashSet + 「只从序列起点展开」（推荐，O(n) / O(n)）
 * <p>
 * 第一步：把所有元素丢进 HashSet。这一步同时完成两件事——去重（重复元素无意义），以及把「查找某个数是否存在」降到 O(1)。
 * <p>
 * 第二步（关键）：遍历集合时，对每个数 num 先问一句「num - 1 在不在集合里？」
 * - 若在，说明 num 不是它所在序列的开头（前面还有人接着），直接跳过，什么都不做；
 * - 若不在，说明 num 就是这条序列的起点，此时才向后一路探测 num+1、num+2 ……直到断开，得到这条序列的长度。
 * <p>
 * 为什么这样就是 O(n)？这是全题最需要想通的一点，它靠的是「均摊」而非「每一步都快」：
 * 内层 while 看起来是双层循环，但对同一个连续段（例如 1,2,3,4），只有它的起点 1 会触发扩展，
 * 而 2、3、4 因为各自的前驱都在集合里，一律被跳过。
 * 更精确的界是「每个元素至多被访问两次」：一次是作为 num 被判起点（那次跳过或触发），
 * 一次是作为某个 num+1 被内层 while 查到。于是总操作数 ≤ 2n，整体线性。
 * <p>
 * 反过来，如果省掉「num - 1 在不在」这一步判断，对每个元素都盲目向后扩展，
 * 那么 {1,2,3,...,n} 这样的输入会被扫描 1 + 2 + ... + n 次，退化成 O(n²)（见方法三）。
 * 所以那一行看似不起眼的判断，正是 O(n) 与 O(n²) 的分水岭——它是这道题真正的题眼。
 * <p>
 * 一个容易被忽略的 Java 细节：num - 1 在 num 取 Integer.MIN_VALUE 时会溢出成 Integer.MAX_VALUE。
 * 如果数组里恰好同时有 MIN_VALUE、MIN_VALUE+1 和 MAX_VALUE，MIN_VALUE 会被误判成「有前驱」而被跳过，
 * 它那条长度为 2 的序列就丢了。所以要么显式挡掉 MIN_VALUE，要么改用 long 运算，二选一，不能不处理。
 * <p>
 * 时间复杂度 O(n)：建集合一次遍历 O(n)；外层遍历集合 O(n)；内层 while 的总步数被「每个元素至多进入一次」所限制，
 * 均摊到外层也是 O(n)。三者相加仍是 O(n)。（哈希表操作为均摊 O(1)，极端哈希冲突下会退化，属理论常数之外的工程话题。）
 * 空间复杂度 O(n)：HashSet 需要存下所有不重复的元素，这是 O(n) 空间换 O(n) 时间的经典交易。
 * <p>
 * 举一反三：这道题的价值不在答案本身，而在它示范的两套通用思维。
 * 1. 「面试官给的时间上限，往往就是让你淘汰某个直觉解法的信号」。
 *    凡是看到「要求 O(n)」而题面又涉及「查找某元素是否存在」的，第一反应就该是哈希表；
 *    看到「要求 O(log n)」而数组有序的，第一反应就该是二分。时间要求是解法方向的强提示，不要忽略它。
 * 2. 「双层循环不等于 O(n²)，要看内层是否被均摊掉」。
 *    同款套路见于：滑动窗口（右指针只进不退）、KMP（主串指针不回退）、单调栈（每个元素至多入栈出栈一次）、
 *    并查集路径压缩（均摊 O(α(n))）。识别「每个元素至多被处理常数次」这一性质，是写出线性算法的通用钥匙。
 * <p>
 * 变体延伸：
 * - 若要求返回「具体是哪个序列」而非长度：把内层 while 的结束值记下来即可，返回 [start, end]，改动极小。
 * - 若要求序列元素在原数组中「下标也递增」（即最长连续递增子序列，LeetCode 674）：
 *   题目的约束从「值域」变成了「顺序」，哈希表立刻失效，必须退回线性扫描（见本包中的 FindLengthOfLCIS）。
 *   对比这两题能很好地体会：约束改了，数据结构就得跟着换。
 * - 若数据规模极大、内存放不下整个哈希表：可先按值域分桶（外部排序思想）再逐桶处理，用时间换空间。
 */
public class LongestConsecutive {


    public int longestConsecutive1(int[] nums) {
        // 转化成哈希集合，方便快速查找是否存在某个元素
        HashSet<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 0;

        for (int num : set) {
            if (set.contains(num - 1)) {
                // num 不是连续子序列的第一个，跳过
                continue;
            }
            // num 是连续子序列的第一个，开始向上计算连续子序列的长度
            int curNum = num;
            int curLen = 1;

            while (set.contains(curNum + 1)) {
                curNum += 1;
                curLen += 1;
            }
            // 更新最长连续序列的长度
            res = Math.max(res, curLen);
        }

        return res;
    }


    // ============ 方法一：HashSet + 只从序列起点展开（推荐，满足题目 O(n) 要求） ============

    /**
     * des: 用 HashSet 换取 O(1) 查找，并借助「前驱是否存在」筛出真正的序列起点，使每个元素至多被访问两次
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            // 空数组没有元素，自然不存在连续序列
            return 0;
        }

        // 去重 + 建立 O(1) 查找能力，容量按负载因子 0.75 预留，避免中途扩容
        Set<Integer> set = new HashSet<>(Math.max(16, nums.length * 2));
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        // 遍历 set 而不是 nums：重复元素已在去重时被消化，这里不会再做无用功
        for (int num : set) {
            // 若 num - 1 存在，说明 num 只是某条序列的中间节点，必定已被它的起点覆盖，直接跳过
            // 注意：num 为 Integer.MIN_VALUE 时 num - 1 会溢出成 Integer.MAX_VALUE，故先挡掉
            if (num != Integer.MIN_VALUE && set.contains(num - 1)) {
                continue;
            }

            // 走到这里说明 num 是本条序列的最小值，从它开始向后一路探测
            int cur = num;
            // cur != Integer.MAX_VALUE 的守卫用于防止 cur + 1 溢出
            while (cur != Integer.MAX_VALUE && set.contains(cur + 1)) {
                cur++;
            }

            // 序列为 [num, cur]，长度为 cur - num + 1（cur >= num，不会溢出）
            longest = Math.max(longest, cur - num + 1);
        }

        return longest;
    }

    // ============ 方法二：排序后一次扫描（O(n log n)，最直观，用于对照理解） ============

    /**
     * des: 排序后连续数字必然相邻，一次遍历统计连续段长度，重复元素跳过即可
     * <p>
     * 时间 O(n log n)，空间 O(n)（此处为不改动入参做了拷贝；若允许原地排序则额外空间为 O(1)）。
     * 它不满足 O(n) 的要求，但是最贴近直觉、也最不容易写错的写法，适合作为理解题意的起点，
     * 同时它的输出可以用来交叉验证方法一。
     */
    public int longestConsecutiveBySort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 拷贝一份，避免排序污染调用方的原始数组
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);

        // 至少有一个元素，故最长长度初值为 1
        int longest = 1;
        // 当前连续段的长度，从第一个元素起算
        int curLen = 1;

        for (int i = 1; i < copy.length; i++) {
            if (copy[i] == copy[i - 1]) {
                // 重复元素不打断连续性，也不增加长度，直接跳过
                continue;
            }

            if (copy[i] == copy[i - 1] + 1) {
                // 与前一个数恰好差 1，当前段延长
                curLen++;
            } else {
                // 出现断层，结算当前段并重新开始
                longest = Math.max(longest, curLen);
                curLen = 1;
            }
        }

        // 收尾：最后一段可能一直延伸到数组末尾，循环里没有被结算过
        return Math.max(longest, curLen);
    }

    // ============ 方法三：HashSet 暴力扩展（不去判断起点，用于说明优化点在哪） ============

    /**
     * des: 对每个元素都盲目向后扩展，省掉了「前驱是否存在」的判断
     * <p>
     * 结果依然正确，但复杂度退化：面对 {1, 2, 3, ..., n} 这类整体连续的输入，
     * n 会被扩展 n-1 次、n-1 会被扩展 n-2 次……总操作数约 n²/2，即最坏 O(n²)。
     * <p>
     * 保留它的意义在于对照：方法一与方法三只差一行「if 前驱存在则 continue」，
     * 正是这一行把同一段连续区间从「被反复完整扫描」变成「只由起点扫描一次」，
     * 值不值得写出来一目了然。这也是「均摊分析」最朴素的一个例子。
     */
    public int longestConsecutiveByBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;
        for (int num : set) {
            int cur = num;
            while (cur != Integer.MAX_VALUE && set.contains(cur + 1)) {
                cur++;
            }
            longest = Math.max(longest, cur - num + 1);
        }

        return longest;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，序列 {1,2,3,4} 在原数组中被打散 ----------
        check(new int[]{100, 4, 200, 1, 3, 2}, 4);

        // ---------- 用例 2：官方示例，最长序列为 {0,1,2,3,4}，注意起点是 0 ----------
        check(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, 9);

        // ---------- 用例 3：官方示例，元素两两不相邻，答案退化为 1 ----------
        check(new int[]{1, 0, 1, 2}, 3);

        // ---------- 用例 4：空数组 ----------
        check(new int[]{}, 0);

        // ---------- 用例 5：单元素，自己构成长度为 1 的序列 ----------
        check(new int[]{5}, 1);

        // ---------- 用例 6：含重复元素，验证去重不会影响答案 ----------
        check(new int[]{1, 1, 2, 2, 3}, 3);

        // ---------- 用例 7：含负数，验证负值区间也能正确串联 ----------
        check(new int[]{-3, -2, -1, 1, 2, 3, 4}, 4);

        // ---------- 用例 8：全负数且整体连续 ----------
        check(new int[]{-5, -4, -3, -2, -1}, 5);

        // ---------- 用例 9：跨越 0 的连续段，验证正负交界处不断裂 ----------
        check(new int[]{2, -1, 0, 1, 5, 6}, 4);

        // ---------- 用例 10：整体连续的最长升序，是方法三退化到 O(n²) 的最坏输入 ----------
        check(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 10);

        // ---------- 用例 11：完全离散，最长序列只能是单个元素 ----------
        check(new int[]{10, 30, 50, 70}, 1);

        // ---------- 用例 12：Integer 边界值，验证 num - 1 / cur + 1 的溢出处理 ----------
        // MIN_VALUE 与 MIN_VALUE+1 本应构成长度 2 的序列，而 MAX_VALUE 是孤立点
        check(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MAX_VALUE}, 2);

        // ---------- 用例 13：最大值附近的连续段，验证 cur + 1 不会溢出成负数 ----------
        check(new int[]{Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE}, 3);

        // ---------- 用例 14：规模稍大的乱序连续段，交叉验证三种解法的一致性 ----------
        // 去重后为 {-1,0,1} 与 {3,4,5,6,7,8,9}，两条段中取较长者为 7
        check(new int[]{9, 1, 4, 7, 3, -1, 0, 5, 8, -1, 6}, 7);
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, int expected) {
        LongestConsecutive solution = new LongestConsecutive();
        int r1 = solution.longestConsecutive(nums);
        int r2 = solution.longestConsecutiveBySort(nums);
        int r3 = solution.longestConsecutiveByBruteForce(nums);

        boolean pass = r1 == expected && r2 == expected && r3 == expected;
        System.out.printf("nums = %s%n", Arrays.toString(nums));
        System.out.printf("  期望值       : %d%n", expected);
        System.out.printf("  HashSet 起点 : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  排序扫描     : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  暴力扩展     : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }
}
