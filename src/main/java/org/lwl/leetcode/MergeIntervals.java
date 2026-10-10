package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * LeetCode 56 - 合并区间（提交时将类名改为 Solution）
 * <p>
 * 题目：以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [start_i, end_i]。
 * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 * <p>
 * 关键理解一：什么叫「重叠」？
 * 两个区间 [a, b] 与 [c, d]（不妨设 a <= c），它们有交集当且仅当 c <= b。
 * 注意这里用的是「<=」而不是「<」：题目明确把 [1,4] 与 [4,5] 视为可合并的重叠区间，
 * 因为二者合起来恰好无缝覆盖 [1,5]，中间没有留下任何空隙。
 * 反过来，[1,2] 与 [3,4] 之间缺了 (2, 3) 这一整段，绝不能合并——
 * 「是否留下空隙」才是判断标准，端点相接不算空隙。
 * <p>
 * 关键理解二：为什么排序是这道题的钥匙？
 * 不排序的话，每来一个区间都得跟已打样的结果集逐一比对；更麻烦的是，
 * 合并出的新区间可能又和更早的区间产生了新的重叠，形成「传染」，
 * 只能反复扫描直到状态稳定，复杂度直逼 O(n^2) 乃至更差。
 * <p>
 * 而一旦按左端点升序排好序，左端点就单调不减了，于是冒出一条极强的性质：
 * <b>当前区间只会与结果集中「最后一个」区间重叠，绝不可能与更早的那些重叠。</b>
 * 论证很短：结果集里的区间是按合并顺序生成的、彼此不重叠且左端点递增，
 * 所以靠前的区间右端点一定小于最后一个区间的左端点；而当前区间的左端点又 >= 最后一个区间的左端点，
 * 于是当前区间的左端点天然落在所有靠前区间的右侧，想重叠都够不着。
 * <p>
 * 一句话概括：排序把「与全部已定型区间比较」降维成了「只与最后一个比较」，
 * 整个算法由此坍缩成一次线性扫描。
 * <p>
 * 核心思路：排序 + 一次扫描
 * 1. 按左端点升序排序；
 * 2. 顺序遍历，维护结果集中最后一段 [lastStart, lastEnd]：
 * - 若当前区间起点 start > lastEnd，说明中间断了，当前区间自成新的一段；
 * - 否则二者重叠，把 lastEnd 向右延伸到 max(lastEnd, end)。
 * <p>
 * 三个必须记住的易错点：
 * 1. 排序键只能是左端点。若改成按右端点排序，「只与最后一个比较」的前提当场失效，结果必错。
 * 2. 合并时右端点务必取 max，不能直接赋值。因为存在「完全被包含」的区间：
 * [[1,10],[2,3]] 合并后仍是 [1,10]，若写成 lastEnd = end 就会被缩短成 [1,3]。
 * 更隐蔽的连锁错误见 [[1,5],[2,3],[4,6]]——把 [1,5] 缩成 [1,3] 之后，
 * 后面的 [4,6] 会因为 4 > 3 而被误判为不重叠，正确答案 [1,6] 被拆成了两段。
 * 3. 判断条件用 start > lastEnd（即合并条件为 start <= lastEnd）。
 * 若写成 start >= lastEnd 才合并，[1,4] 与 [4,5] 会被拆开，结果集出现人为的断点。
 * <p>
 * 时间复杂度 O(n log n)：排序是唯一的瓶颈；此后的扫描是严格的 O(n)，每个区间只被查看一次。
 * 空间复杂度 O(log n)（排序递归栈）或 O(n)（结果集本身，按 LeetCode 惯例不计入辅助空间）。
 * <p>
 * 举一反三：区间类问题的共同套路是「先排序定序，再一趟扫描」，但排序键的选择因目标而异，
 * 这正是最容易混淆、也最见功力的地方——
 * - LeetCode 435（无重叠区间）/ 452（用最少数量的箭引爆气球）：同样在排序，
 *   却必须<b>按右端点排序</b>。因为它们是「活动选择」型贪心，每次要挑结束最早的那个，
 *   才能给后面腾出最多空间；按左端点排序会掉进 [1,10],[2,3],[3,4] 这类陷阱，选不到最优解。
 *   同一个「排序」动作，56 用左端点、435 用右端点，目标决定键——这是区间专题最值得玩味的一处对比；
 * - LeetCode 57（插入区间）：输入本身已有序且互不重叠，于是连排序都省了，直接一趟线性扫描 O(n)；
 * - LeetCode 1288（删除被覆盖区间）：排序后统计「被已合并区间完全包含」的个数，是本题的一个变体；
 * - LeetCode 986（区间列表的交集）：重叠判断公式与本题同源，只是求交集而非并集，改用双指针推进；
 * - LeetCode 763（划分字母区间）：把每个字母的首次、末次出现当作一个区间，再套本题的合并逻辑；
 * - LeetCode 252 / 253（会议室）：判断是否存在重叠 / 求最大同时重叠数，后者正是「扫描线」的直接应用；
 * - 若坐标取值范围有限，扫描线还能进一步用「差分数组」把 O(n log n) 压到 O(n + range)，
 *   如 LeetCode 1094（拼车）、1109（航班预订统计），这是扫描线思想最实用的落地形态。
 */
public class MergeIntervals {

    // ============ 方法一：排序 + 一次扫描（推荐，标准解法） ============

    /**
     * des: 按左端点排序后，只需把当前区间与结果集的最后一段比较，重叠就向右延伸、断开就另起一段
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][];
        }

        // 按左端点升序排序，这是整个解法成立的前提。
        // 用 Integer.compare 而非 a[0] - b[0]，避免两数相减时的整型溢出
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();
        for (int[] cur : intervals) {
            if (merged.isEmpty()) {
                merged.add(cur);
                continue;
            }

            // 只看结果集的最后一段即可——排序保证了更早的区间不可能与 cur 重叠
            int[] last = merged.get(merged.size() - 1);
            if (cur[0] > last[1]) {
                // 起点超过了上一段的终点，中间留有空隙，cur 自成新的一段
                merged.add(cur);
            } else {
                // 有交集：右端点取较大值，以应对 cur 被 last 完全包含的情况
                last[1] = Math.max(last[1], cur[1]);
            }
        }

        return merged.toArray(new int[0][]);
    }

    // ============ 方法二：排序 + 原地回写（同为 O(n log n)，进一步省去 List） ============

    /**
     * des: 与方法一逻辑完全一致，只是把结果直接写回原数组的前 k 个位置，省掉 List 的装箱与扩容开销
     * <p>
     * 关键在于 k 这个游标：它既表示「已定型的区间个数」，又表示「下一个结果的写入位置」。
     * 由于 k 始终不超过当前遍历下标，回写只会覆盖那些已经处理过、不会再被访问的位置，因而绝对安全。
     */
    public int[][] mergeInPlace(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][];
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int k = 0;
        for (int[] cur : intervals) {
            // intervals[k - 1] 就是结果集的最后一段
            if (k > 0 && cur[0] <= intervals[k - 1][1]) {
                intervals[k - 1][1] = Math.max(intervals[k - 1][1], cur[1]);
            } else {
                // 写入新的一段；此处 intervals[k] 若位于 cur 之后，二者会指向同一个对象，无碍
                intervals[k++] = cur;
            }
        }

        // 只截取前 k 个已定型的区间，其余位置是收缩后残留的垃圾数据
        return Arrays.copyOf(intervals, k);
    }

    // ============ 方法三：扫描线 / 事件点（思路完全不同，用于对照） ============

    /**
     * des: 不看区间，改看数轴上的每一个坐标点——统计各点被多少个区间覆盖，覆盖数由 0 变正即一段之始、由正归 0 即一段之末
     * <p>
     * 事件表里，区间起点记 +1、终点记 -1，TreeMap 负责按坐标排序并累加同坐标上的净变化量。
     * 「净变化量」这个设计一箭双雕：坐标 4 上若同时有一个区间结束、另一个区间开始，
     * 净变化量恰为 0，覆盖深度不会掉到 0，[1,4] 与 [4,5] 便自然合并成了 [1,5]，
     * 无需任何额外的端点特判——端点相接的语义被坐标系本身消化掉了。
     * <p>
     * 时间 O(n log n)，空间 O(n)。相比排序法，它的优势在于思想更通用：
     * 求「最大同时重叠数」（LeetCode 253）等排序法无能为力的问题，扫描线只需记录一个 depth 的最大值即可。
     */
    public int[][] mergeBySweepLine(int[][] intervals) {
        // 坐标 -> 净变化量：起点 +1、终点 -1，同坐标自动累加
        TreeMap<Integer, Integer> events = new TreeMap<>();
        for (int[] iv : intervals) {
            events.merge(iv[0], 1, Integer::sum);
            events.merge(iv[1], -1, Integer::sum);
        }

        List<int[]> merged = new ArrayList<>();
        int depth = 0;   // 当前坐标被多少个区间覆盖
        int start = 0;   // 正在累积的合并段的起点
        for (Map.Entry<Integer, Integer> event : events.entrySet()) {
            if (depth == 0) {
                // 覆盖深度即将从 0 变正，一段新的合并区间在此开始
                start = event.getKey();
            }
            depth += event.getValue();
            if (depth == 0) {
                // 覆盖深度归零，这一段到此闭合
                merged.add(new int[]{start, event.getKey()});
            }
        }

        return merged.toArray(new int[0][]);
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，中间断了两处 ----------
        check(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}},
                new int[][]{{1, 6}, {8, 10}, {15, 18}});

        // ---------- 用例 2：官方示例，端点恰好相接，应合并而非断开 ----------
        check(new int[][]{{1, 4}, {4, 5}},
                new int[][]{{1, 5}});

        // ---------- 用例 3：只有一个区间，原样返回 ----------
        check(new int[][]{{1, 4}},
                new int[][]{{1, 4}});

        // ---------- 用例 4：后一区间被前一区间完全包住，验证右端点必须取 max ----------
        check(new int[][]{{1, 10}, {2, 3}},
                new int[][]{{1, 10}});

        // ---------- 用例 5：右端点不取 max 就会连锁出错的经典反例 ----------
        // 错误实现会把 [1,5] 缩成 [1,3]，进而误判 [4,6] 不重叠，正确答案 [1,6] 被拆成两段
        check(new int[][]{{1, 5}, {2, 3}, {4, 6}},
                new int[][]{{1, 6}});

        // ---------- 用例 6：被包含的区间不该影响与后续区间的合并 ----------
        check(new int[][]{{1, 4}, {2, 3}, {5, 7}},
                new int[][]{{1, 4}, {5, 7}});

        // ---------- 用例 7：输入逆序，验证排序确实生效 ----------
        check(new int[][]{{2, 3}, {1, 10}},
                new int[][]{{1, 10}});

        // ---------- 用例 8：完全分离，一个都不该合并 ----------
        check(new int[][]{{1, 2}, {3, 4}, {5, 6}},
                new int[][]{{1, 2}, {3, 4}, {5, 6}});

        // ---------- 用例 9：乱序输入，验证排序后仍能正确分断 ----------
        check(new int[][]{{15, 18}, {1, 3}, {8, 10}, {2, 6}},
                new int[][]{{1, 6}, {8, 10}, {15, 18}});

        // ---------- 用例 10：完全相同的区间，重复项应被吸收 ----------
        check(new int[][]{{1, 4}, {1, 4}},
                new int[][]{{1, 4}});

        // ---------- 用例 11：零长度区间与正常区间相接 ----------
        check(new int[][]{{1, 1}, {1, 2}},
                new int[][]{{1, 2}});

        // ---------- 用例 12：负数区间，验证坐标平移不影响逻辑 ----------
        check(new int[][]{{-5, -1}, {-3, 2}},
                new int[][]{{-5, 2}});

        // ---------- 用例 13：链式合并，一环扣一环最终串成一整条 ----------
        check(new int[][]{{1, 2}, {2, 3}, {3, 4}},
                new int[][]{{1, 4}});

        // ---------- 用例 14：500 个区间首尾相接，应全部串成一条 ----------
        int m = 500;
        int[][] chained = new int[m][2];
        for (int i = 0; i < m; i++) {
            chained[i][0] = i * 2;
            chained[i][1] = i * 2 + 2;   // 终点恰为下一区间起点，验证「相接即重叠」
        }
        check(chained, new int[][]{{0, (m - 1) * 2 + 2}});

        // ---------- 用例 15：500 个互相分离的区间，一个都不该被合并 ----------
        int[][] scattered = new int[m][2];
        for (int i = 0; i < m; i++) {
            scattered[i][0] = i * 10;
            scattered[i][1] = i * 10 + 2;   // 与下一区间之间空出 8 个单位
        }
        check(scattered, copy(scattered));
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[][] intervals, int[][] expected) {
        MergeIntervals solution = new MergeIntervals();

        // merge 与 mergeInPlace 都会改写二维数组，故各自传入深拷贝，避免相互污染
        int[][] r1 = solution.merge(copy(intervals));
        int[][] r2 = solution.mergeInPlace(copy(intervals));
        int[][] r3 = solution.mergeBySweepLine(intervals);

        boolean pass = Arrays.deepEquals(r1, expected)
                && Arrays.deepEquals(r2, expected)
                && Arrays.deepEquals(r3, expected);

        System.out.printf("intervals = %s%n", brief(intervals));
        System.out.printf("  期望值     : %s%n", brief(expected));
        System.out.printf("  排序扫描   : %s%s%n", brief(r1), Arrays.deepEquals(r1, expected) ? "  ✅" : "  ❌");
        System.out.printf("  原地回写   : %s%s%n", brief(r2), Arrays.deepEquals(r2, expected) ? "  ✅" : "  ❌");
        System.out.printf("  扫描线     : %s%s%n", brief(r3), Arrays.deepEquals(r3, expected) ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 二维数组深拷贝，内层也要复制，否则会被原地解法改写 */
    private static int[][] copy(int[][] src) {
        int[][] dst = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dst;
    }

    /** des: 区间过多时只打印前 5 个，避免测试输出刷屏 */
    private static String brief(int[][] intervals) {
        if (intervals.length <= 10) {
            return Arrays.deepToString(intervals);
        }
        return Arrays.deepToString(Arrays.copyOf(intervals, 5))
                + " ... 共 " + intervals.length + " 个区间";
    }
}
