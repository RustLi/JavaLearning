package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 15 - 三数之和（提交时将类名改为 Solution）
 * <p>
 * 题目：给你一个整数数组 nums，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0。请你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 关键理解一：题目里「不重复」到底约束的是什么？
 * 不是下标不重复（i != j != k 已经说过了），而是「三元组的取值组合不能重复」。
 * 也就是说 [-1, 0, 1]、[0, -1, 1]、[1, -1, 0] 在本题眼里是同一个答案，只能输出其中一个。
 * 由于返回的是 List&lt;List&lt;Integer&gt;&gt;，List 的 equals 是「顺序敏感」的——
 * 如果放任不管，同一个组合会以不同排列反复出现，答案集立刻臃肿且被判错。
 * 这一点是本题的全部难度所在：寻找和为 0 的三元组本身不难，难的是「既要不漏，又要不重」。
 * <p>
 * 关键理解二：为什么必须先排序？
 * 排序一次，白拿三样东西：
 * 1. 把「重复」从「排列级」降成「相邻级」。排序后相同的数必然挨在一起，
 * 于是去重从「两两比对是否同组合」这种 O(答案数²) 的苦差事，
 * 变成了「跟前一个数比一比」这种 O(1) 的举手之劳——这是排序带来的最大红利。
 * 2. 让双指针成为可能。有序数组上，左指针右移必然使和变大、右指针左移必然使和变小，
 * 于是「和偏小就左移、和偏大就右移」成为有向的、单调的调整，一次扫描即可穷尽所有组合。
 * 3. 白送一条剪枝：排序后若 nums[i] > 0，则后面全是正数，三者之和不可能为 0，直接结束外层循环。
 * <p>
 * 关键理解三：双指针为什么不会漏解？
 * 固定 i 之后，问题退化成「在 nums[i+1..n-1] 中找两数之和等于 -nums[i]」，即 LeetCode 167。
 * 该子问题的最优解区间是连续收缩的：设左右指针为 l、r，
 * - 若 nums[l] + nums[r] 偏小，说明以当前 l 为准时，连最大的 r 都不够大，故 l 与任何更小的 r 都凑不出来，l 可以安全右移；
 * - 若偏大，同理 r 可以安全左移；
 * - 相等则记录一组，然后 l 右移、r 左移，继续在同一层寻找其他配对。
 * 每一步都排除掉了一个「确定无解」的指针位置，所以收缩过程既不多走、也不遗漏。
 * <p>
 * 核心思路：排序 + 固定一个数 + 双指针（推荐，O(n²) / O(1) 额外空间）
 * 1. 数组升序排序；
 * 2. 外层枚举第一个数下标 i（只枚举到 n-3，因为后面要留两个位置给 l 和 r）；
 * - 剪枝：nums[i] > 0 直接 break；
 * - 去重：i > 0 且 nums[i] == nums[i-1] 则跳过（同一取值作为首数只需处理一次）；
 * 3. 内层双指针 l = i + 1、r = n - 1，按 sum 与 0 的大小关系收缩；
 * 4. sum == 0 时记录 [nums[i], nums[l], nums[r]]，然后跳过后继的重复值，再让 l++、r--。
 * <p>
 * 三个必须记住的易错点：
 * 1. 去重只做在「产生结果」的位置。外层的 i 去重放在循环开头，内层的 l / r 去重放在「记录完一组答案之后」。
 * 若在内层每次移动指针时都去重，虽然结果也对，但会平白多出判断；
 * 而在别的时机去重（例如记录答案前判重）则会把合法答案一起滤掉。
 * 2. 内层跳过重复时的边界必须是 l &lt; r。写成 l &lt;= r 会让指针越界，或让 l 与 r 指向同一个数，
 * 后者在 [-1, -1, 2] 这类用例上会凑出下标重复的「假解」。
 * 3. 外层循环上界是 n - 3 而非 n - 1。虽然 l = i+1、r = n-1 的初值本身能防住越界，
 * 但把边界写准可以省掉无意义的空转。同理，外层循环里必须重新把 l、r 初始化到 i 的两侧，
 * 若沿用上一轮的指针，漏解是必然的。
 * <p>
 * 时间复杂度的推导：排序 O(n log n)；外层枚举 i 共 O(n) 次，每次内层双指针收缩 O(n)，
 * 合计 O(n²)。由于 n log n 被 n² 吸收，整体 O(n²)。
 * 空间复杂度：不计返回结果时为 O(1)（排序若用递归实现，额外栈空间 O(log n)）；
 * 结果集本身最多 O(n²) 个三元组，按 LeetCode 惯例不计入辅助空间。
 * <p>
 * 举一反三：本题是「k 数之和」家族的中间站，沿着这条线能一路推下去。
 * - LeetCode 1（两数之和）：哈希一遍 O(n)，但只找一组解，不需要处理重复；
 *   一旦要求「返回所有不重复组合」，哈希法就必须配合排序去重，优势随之消失——这正是本题方法二的意义。
 * - LeetCode 167（两数之和 II，输入有序）：直接双指针 O(n)，是本题内层循环的原型，务必先吃透它。
 * - LeetCode 16（最接近的三数之和）：同样固定 i + 双指针，只是把「相等即返回」换成「持续更新最接近值」，
 *   并且因为要处理「最接近」而非「恰好等于」，剪枝条件要放宽。
 * - LeetCode 18（四数之和）：在本题外面再套一层循环，O(n³)，去重逻辑原样复制到新的一层即可；
 *   通用模板是「排序 + 递归固定前 k-2 个数 + 双指针收尾」。
 * - LeetCode 259（较小的三数之和）：把「等于 0」换成「小于 target」，
 *   此时命中条件变成 nums[l] + nums[r] &lt; target 则一次性累加 r - l 组答案，再左移 l，效率更高。
 * - LeetCode 611（有效三角形的个数）：与 259 同构，条件是两小边之和大于第三边，套同一套双指针骨架。
 * <p>
 * 一句话总结：遇到「求和为定值的所有组合」这类题，先想排序，再想「固定一个 / 两个数 + 双指针」，
 * 最后把去重逻辑嵌进排序后的相邻比较里——这三步是共通的骨架，题目怎么变，骨架不变。
 */
public class ThreeSum {

    // ============ 方法一：排序 + 双指针（推荐，标准解法，O(n²) 时间 / O(1) 额外空间） ============

    /**
     * des: 排序后固定第一个数，内层退化为「有序数组两数之和」，双指针一次扫描即可穷尽所有配对
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            // 不足三个元素，连一个三元组都凑不出来
            return result;
        }

        // 排序是后面「相邻去重」与「单调移动指针」全部逻辑的地基
        Arrays.sort(nums);
        int n = nums.length;

        // 留出 l 与 r 两个位置，故 i 最多枚举到 n - 3
        for (int i = 0; i < n - 2; i++) {
            // 剪枝：排序后 nums[i] 已是剩余部分的最小值，一旦为正，三数之和必为正
            if (nums[i] > 0) {
                break;
            }

            // 首数去重：与上一轮取值相同时，后续所有组合都已被上一轮枚举过，直接跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int l = i + 1;
            int r = n - 1;

            while (l < r) {
                // 题目约束 |nums[i]| <= 10^5，三项相加远在 int 范围内，无需担心溢出
                int sum = nums[i] + nums[l] + nums[r];

                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[l], nums[r]));

                    // 尾数、末数的去重：跳过取值相同的元素，避免同一组合被重复记录。
                    // 边界务必是 l < r，否则指针可能越过彼此甚至越界
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }

                    // 当前这对 (l, r) 已记录完毕，双向收缩寻找新的配对
                    l++;
                    r--;
                } else if (sum < 0) {
                    // 和偏小：当前 l 配任何更小的 r 都只会更小，故 l 确定为无用位置，右移
                    l++;
                } else {
                    // 和偏大：对称地，r 确定为无用位置，左移
                    r--;
                }
            }
        }

        return result;
    }

    // ============ 方法二：排序 + 哈希表（同为 O(n²)，用于对照「哈希思路为何不占优」） ============

    /**
     * des: 固定首数 i，内层枚举第二个数 j，第三个数的目标值 -nums[i]-nums[j] 用 HashSet O(1) 查
     * <p>
     * 与两数之和的哈希解法一脉相承，但请特别留意它多出来的代价：
     * 两数之和只需找「一组」解，找到即可返回；本题要找「所有不重复」的解，
     * 于是哈希表解决不了去重问题，必须额外借助排序 + Set 兜底。
     * <p>
     * 具体来说：先排序（保证记录的三元组天然升序，Set 才能正确判重），
     * 再用 Set&lt;List&lt;Integer&gt;&gt; 收集答案——List 的 equals/hashCode 按元素逐个比较，
     * 取值相同、顺序也相同的三元组会被自动合并，重复问题就此消解，无需手写跳过逻辑。
     * 代价是每个答案都要多做一次哈希运算，且额外占用 O(n) 空间放 Set。
     * <p>
     * 结论：在「求所有不重复组合」这个需求下，哈希法相对双指针没有任何优势——
     * 时间复杂度同为 O(n²)，空间反而更差。它的价值在于示范思路差异：
     * 双指针把去重嵌进了「单调移动」里，而哈希把去重外推给了 Set，后者更省心但更费资源。
     */
    public List<List<Integer>> threeSumByHash(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        if (nums == null || nums.length < 3) {
            return new ArrayList<>(result);
        }

        // 排序在此处的作用是「保证三元组按升序记录」，而非去重——去重交给 Set
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (nums[i] > 0) {
                break;
            }

            // seen 中存放的是「本轮已扫描过」的 nums[j]（j 严格小于当前 j）
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                // 想要三数之和为 0，第三个数必须恰好是它
                int target = -nums[i] - nums[j];

                if (seen.contains(target)) {
                    // 由于数组已排序，target <= nums[j]，且 nums[i] 是三者中最小的，
                    // 故 (nums[i], target, nums[j]) 天然升序，Set 的判重因此可靠
                    result.add(Arrays.asList(nums[i], target, nums[j]));
                }

                // 把当前数纳入 seen，供后续的 j 作为「第三个数」使用
                seen.add(nums[j]);
            }
        }

        return new ArrayList<>(result);
    }

    // ============ 方法三：三重暴力枚举（O(n³)，仅用于说明为何必须优化） ============

    /**
     * des: 三重循环枚举所有下标组合，用 Set 兜住重复，正确但不可用
     * <p>
     * 保留它的目的是提供一个「绝对正确」的基准：当方法一、方法二在边界用例上出现分歧时，
     * 可以拿它的输出来判定谁错了。同时它也直观展示了什么叫「不漏但会重」——
     * 暴力枚举天然不遗漏任何组合，可重复问题必须靠 Set 事后补救，
     * 而方法一是在枚举过程中就同步解决了重复，这正是排序带来的效率差异。
     * <p>
     * 时间 O(n³)，空间 O(答案数)。n 稍大（如 3000）即是 2.7×10^10 次运算，实际不可运行，
     * 故测试中仅在 n <= 200 的小规模用例上启用它做交叉验证。
     */
    public List<List<Integer>> threeSumByBruteForce(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        // 排序后再存入，保证同一组合的不同排列被 Set 识别为同一个元素
                        int[] triple = {nums[i], nums[j], nums[k]};
                        Arrays.sort(triple);
                        result.add(Arrays.asList(triple[0], triple[1], triple[2]));
                    }
                }
            }
        }

        return new ArrayList<>(result);
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，注意首数 -1 重复出现，考验首数去重 ----------
        check(new int[]{-1, 0, 1, 2, -1, -4},
                new int[][]{{-1, -1, 2}, {-1, 0, 1}});

        // ---------- 用例 2：官方示例，凑不出和为 0 的三元组 ----------
        check(new int[]{0, 1, 1}, new int[][]{});

        // ---------- 用例 3：官方示例，三个 0 恰好成一解 ----------
        check(new int[]{0, 0, 0}, new int[][]{{0, 0, 0}});

        // ---------- 用例 4：四个 0，应只输出一个 [0,0,0]，考验内层去重 ----------
        check(new int[]{0, 0, 0, 0}, new int[][]{{0, 0, 0}});

        // ---------- 用例 5：空数组 ----------
        check(new int[]{}, new int[][]{});

        // ---------- 用例 6：元素不足三个 ----------
        check(new int[]{0, 1}, new int[][]{});

        // ---------- 用例 7：全为正数，应被 nums[i] > 0 的剪枝立刻拦下 ----------
        check(new int[]{1, 2, 3, 4}, new int[][]{});

        // ---------- 用例 8：全为负数，任何三数之和都为负 ----------
        check(new int[]{-1, -2, -3}, new int[][]{});

        // ---------- 用例 9：[-2,1,1] 这种「两数相同」的解，考验内层指针边界 ----------
        check(new int[]{-2, 0, 1, 1, 2},
                new int[][]{{-2, 0, 2}, {-2, 1, 1}});

        // ---------- 用例 10：官方大用例，重复元素密集，是最能暴露去重漏洞的一组 ----------
        check(new int[]{-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6},
                new int[][]{{-4, -2, 6}, {-4, 0, 4}, {-4, 1, 3}, {-4, 2, 2},
                        {-2, -2, 4}, {-2, 0, 2}});

        // ---------- 用例 11：大量重复的 -1 与 1，去重后只应剩 [-1,0,1] 一解 ----------
        // 注意别误以为有 [-1,-1,2]——数组里根本没有 2
        check(new int[]{-1, -1, -1, -1, 0, 1, 1, 1, 1},
                new int[][]{{-1, 0, 1}});

        // ---------- 用例 12：仅靠 [0,0,0] 一种解，但 0 的数量很多 ----------
        check(new int[]{0, 0, 0, 0, 0, 0}, new int[][]{{0, 0, 0}});

        // ---------- 用例 13：首尾对称分布，验证双指针双向收缩不会漏解 ----------
        check(new int[]{-4, -3, -2, -1, 0, 1, 2, 3, 4},
                new int[][]{{-4, 0, 4}, {-4, 1, 3}, {-3, -1, 4}, {-3, 0, 3}, {-3, 1, 2},
                        {-2, -1, 3}, {-2, 0, 2}, {-1, 0, 1}});

        // ---------- 用例 14：极端取值，验证三数相加不会溢出 ----------
        check(new int[]{100000, -100000, 0, 50000, -50000},
                new int[][]{{-100000, 0, 100000}, {-50000, 0, 50000}});

        // ---------- 用例 15：随机小规模输入，与暴力解法交叉验证 ----------
        java.util.Random random = new java.util.Random(20261003);
        int round = 0;
        for (int t = 0; t < 200; t++) {
            int[] nums = new int[random.nextInt(9) + 3];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = random.nextInt(11) - 5;   // 取值范围收窄到 [-5, 5]，保证有足够多的解
            }
            if (!checkSilently(nums)) {
                round++;
            }
        }
        System.out.printf("随机交叉验证 200 组：%s%n%n", round == 0 ? "全部一致 ✅" : "有 " + round + " 组不一致 ❌");

        // ---------- 用例 16：性能用例，3000 个元素，验证双指针的 O(n²) 在规模上可用 ----------
        int[] large = new int[3000];
        for (int i = 0; i < large.length; i++) {
            large[i] = random.nextInt(20001) - 10000;
        }
        long start = System.currentTimeMillis();
        List<List<Integer>> largeResult = new ThreeSum().threeSum(large);
        long cost = System.currentTimeMillis() - start;
        System.out.printf("性能用例 n=3000：耗时 %d ms，找到 %d 组解 ✅%n", cost, largeResult.size());
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, int[][] expected) {
        ThreeSum solution = new ThreeSum();

        List<List<Integer>> r1 = solution.threeSum(nums);
        List<List<Integer>> r2 = solution.threeSumByHash(nums);
        // 暴力解法是 O(n³)，只在规模可控时启用，避免测试空转
        boolean runBrute = nums.length <= 200;
        List<List<Integer>> r3 = runBrute ? solution.threeSumByBruteForce(nums) : r1;

        Set<List<Integer>> expectSet = norm(expected);
        boolean p1 = norm(r1).equals(expectSet);
        boolean p2 = norm(r2).equals(expectSet);
        boolean p3 = norm(r3).equals(expectSet);

        boolean pass = p1 && p2 && p3;
        System.out.printf("nums = %s%n", Arrays.toString(nums));
        System.out.printf("  期望值     : %s%n", expectSet);
        System.out.printf("  排序双指针 : %s%s%n", norm(r1), p1 ? "  ✅" : "  ❌");
        System.out.printf("  排序哈希   : %s%s%n", norm(r2), p2 ? "  ✅" : "  ❌");
        System.out.printf("  三重暴力   : %s%s%n", runBrute ? norm(r3) : "(规模过大，跳过)", p3 ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 随机用例只判断三者是否一致、不打印明细，避免刷屏；返回是否全部一致 */
    private static boolean checkSilently(int[] nums) {
        ThreeSum solution = new ThreeSum();
        Set<List<Integer>> r1 = norm(solution.threeSum(nums));
        Set<List<Integer>> r2 = norm(solution.threeSumByHash(nums));
        Set<List<Integer>> r3 = norm(solution.threeSumByBruteForce(nums));
        return r1.equals(r2) && r1.equals(r3);
    }

    /** des: 把结果规范化成 Set，并保证每组内部升序——这样「同一组合的不同排列」才会被识别为同一个元素 */
    private static Set<List<Integer>> norm(List<List<Integer>> triples) {
        Set<List<Integer>> set = new HashSet<>();
        for (List<Integer> triple : triples) {
            int[] arr = new int[triple.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = triple.get(i);
            }
            Arrays.sort(arr);
            set.add(Arrays.asList(arr[0], arr[1], arr[2]));
        }
        return set;
    }

    /** des: 二维期望值转成同一套规范化表示，便于与解法输出直接比对 */
    private static Set<List<Integer>> norm(int[][] triples) {
        Set<List<Integer>> set = new HashSet<>();
        for (int[] triple : triples) {
            set.add(Arrays.asList(triple[0], triple[1], triple[2]));
        }
        return set;
    }
}
