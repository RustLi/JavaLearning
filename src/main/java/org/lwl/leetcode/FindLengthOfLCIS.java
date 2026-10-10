package org.lwl.leetcode;

import java.util.Arrays;

/**
 * LeetCode 674 - 最长连续递增序列（提交时将类名改为 Solution）
 * <p>
 * 题目：给定一个未经排序的整数数组 nums，找到最长且「连续递增」的子序列，并返回该序列的长度。
 * 「连续递增」的精确定义：存在下标 l &lt; r，使得区间 [l, r] 内每一对相邻元素都严格满足 nums[i] &lt; nums[i+1]。
 * 注意题目虽写作「子序列」，实际要求的却是「子数组」——元素必须原封不动地挨在一起，一个都不能跳过，
 * 这是 LeetCode 少见的命名不严谨之处，审题时务必按「子数组」理解。
 * <p>
 * <b>本类的重点在于「返回序列本身」而非长度：</b>LeetCode 官方签名只要一个 int，
 * 但面试与工程中经常追问「把那个序列给我」，此时状态转移本身要改造成可回溯的形式。
 * 类中同时给出连续版（本题）与可跳跃版（LC 300）两种还原实现，方便对照。
 * <p>
 * 关键理解一：「连续」二字把难度从 LeetCode 300 直接削掉一整档。
 * 把两题摆在一起看最清楚：
 * - LC 300（最长递增子序列）允许跳过元素，于是任意两段递增区间可以拼接，产生「接在哪个 j 后面最优」
 *   这个真正的决策，解法升级为 O(n^2) DP 或 O(n log n) 贪心+二分；
 * - LC 674 要求元素相邻，递增段一旦被破坏就只能从头计数，<b>决策空间塌缩成唯一选择</b>，
 *   于是 O(n) 一次遍历足矣，连 dp 数组都用不上。
 * 所以读题第一件事就是分清「连续」与「不连续」——这一个词直接决定复杂度的量级。
 * <p>
 * 关键理解二：为什么 LC 300 那套「贪心 + 二分」在这里彻底失效？
 * 该解法的灵魂是 tails[k] = 「所有长度为 k+1 的递增子序列中，结尾元素的最小值」，
 * 它成立的前提是子序列能跳跃拼接，所以「把一个长度的结尾换得更小」总有机会接上后面的元素。
 * 而本题要求连续，一个元素只能接在紧邻的前一个后面，「结尾越小越容易接上」这个前提根本不存在，
 * tails 的维护自然无从谈起。
 * 结论：算法的适用性由「约束」决定，而不是越花哨越好——面对 LC 674，最朴素的线性扫描才是正解。
 * <p>
 * 关键理解三：判断递增不要用减法。
 * 写成 nums[i] - nums[i-1] &gt; 0 时，若相邻元素恰好跨越 Integer.MIN_VALUE 与 Integer.MAX_VALUE，
 * 减法结果会整型溢出变成负数，把「递增」误判成「递减」（可对照同目录 {@code OverflowDemo}）。
 * 直接写 nums[i] &gt; nums[i-1] 让编译器去做比较，既安全又清晰。
 * <p>
 * 核心思路：沿用 LC 300 的状态定义——dp[i] = 以 nums[i] 结尾的最长连续递增序列长度，
 * 但转移方程只剩一支分支：
 * <pre>
 *      dp[i] = dp[i-1] + 1     若 nums[i] &gt; nums[i-1]（递增延续）
 *      dp[i] = 1               否则（递增段在此断裂，只能以自己重新开始）
 * </pre>
 * 答案取所有 dp[i] 的最大值。由于 dp[i] 只依赖 dp[i-1]，用一个变量 cur 滚动即可，空间降到 O(1)。
 * <p>
 * <b>要返回序列本身时，只需额外记住「最优段的起点下标」</b>——这正是双指针视角的用武之地：
 * 用 start 标记当前递增段的起点，i 向后探查，段一断就结算长度。本类方法三即由此而来。
 * 与 LC 300 的还原方式对比：LC 300 因为存在「接在哪个 j 后面」的决策，必须用 prev[] 数组
 * 记录每一位的前驱、再从终点回溯；而本题的连续约束让「前驱」永远是紧邻的左边一格，
 * 于是<b>根本不需要 prev 数组</b>，一个 start 足矣。约束越强，还原越省。
 * <p>
 * 一个必须注意的坑：ans 要<b>每轮迭代都更新</b>，不能只在「断裂时」结算。
 * 因为数组末尾那一段递增区间身后已经没有元素了，永远不会触发断裂分支，
 * 只在断裂处结算的话，末尾这一整段会被静默漏掉。让 ans 每轮参与取最大值就不存在这个盲区。
 * <p>
 * 时间复杂度 O(n)：每个元素恰好被访问一次，转移是 O(1)。
 * 空间复杂度 O(1)（仅需滚动变量）；返回序列时因需复制结果，为 O(k)，k 为答案长度。
 * <p>
 * 举一反三：本题是「线性扫描 + 滚动状态」这一整类题的绝佳样本。
 * - 同为「最长递增」，去掉「连续」二字就升级成 LC 300；在 LC 300 上再加一维计数则是 LC 673；
 * - 骨架最近的是 {@link MaxSubArray}（LC 53 最大子数组和）：两题都是「以 i 结尾」的滚动状态，
 *   区别仅在于转移是「累加」还是「计数」、「断裂」的判定是「和变小」还是「不再递增」。
 *   把这两题并排看，一次就能吃透这个通用模板，也可对照 {@link MaxProfit}（LC 121）如何把
 *   股票利润转化为差分数组的最大子段和，再一次回到同一个骨架；
 * - LC 485（最大连续 1 的个数）与本题近乎同构，只是「递增」的判定换成了「等于 1」；
 * - LC 128（最长连续序列）形似而神不同——它要求的是「数值连续」（如 1,2,3,4），
 *   与下标连续性无关，靠哈希集合做到 O(n)，切勿混淆；
 * - 常见变体：若题目放宽为「非递减」（允许相等），把 nums[i] &gt; nums[i-1] 改为 &gt;= 即可，其余一字不动。
 */
public class FindLengthOfLCIS {

    // ============ 方法一：一次遍历 + 滚动变量（只要长度时，空间最优） ============

    /**
     * des: dp[i] = 以 nums[i] 结尾的最长连续递增段长度，用 cur 滚动，ans 全程收集最大值
     */
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            // 题目保证 1 <= nums.length，此处仅作工程上的防御，避免调用方传空数组时越界
            return 0;
        }

        // cur: 以当前元素结尾的连续递增段长度，即 dp[i]
        int cur = 1;
        // ans: 全局最大值，至少为 1（单个元素自成一段）
        int ans = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                // 递增延续，长度加一。用 > 直接比较，不做减法，避免整型溢出
                cur++;
            } else {
                // 递增断裂（含相等），以当前元素重新开一段
                cur = 1;
            }
            // 每轮都收集，末尾那一整段才不会因为没有元素触发断裂而被漏掉
            ans = Math.max(ans, cur);
        }

        return ans;
    }

    // ============ 方法二：双指针切分递增段（只要长度，换个视角） ============

    /**
     * des: 用 start 标记当前递增段的起点，i 向后探查，段一断就结算长度并把 start 挪到 i
     * <p>
     * 与方法一同一复杂度、同一答案，差别只在视角：方法一盯的是「以 i 结尾」，方法二盯的是「这一段从哪开始」。
     * 后者正是方法三的直系前身——只要把结算处的 (i - start) 记下来，就顺手拿到了子数组。
     */
    public int findLengthOfLCISByTwoPointers(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int start = 0;      // 当前递增段的起点下标
        int ans = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                // 递增在此断裂：先把刚走完的 [start, i-1] 这段结算掉，再从 i 重开一段
                ans = Math.max(ans, i - start);
                start = i;
            }
        }
        // 末尾这一段身后没有元素来触发断裂分支，必须补一次结算
        ans = Math.max(ans, nums.length - start);

        return ans;
    }

    // ============ 方法三：返回最长的连续递增子数组本身（本题的序列还原版） ============

    /**
     * des: 在方法二的基础上多记一个 bestStart / bestLen，段断裂时顺手保存最优区间，最后切出来
     * <p>
     * 循环上界故意写到 i &lt;= nums.length，把 i == nums.length 当作一个「虚拟哨兵」，
     * 逼末尾那一段也走一次结算逻辑，从而省掉方法二里那句补结算——这是处理「末尾段容易漏」的常用技巧。
     * 注意条件顺序：i == nums.length 必须放在 || 左边，靠短路求值挡住 nums[i] 的越界访问。
     * <p>
     * 相比 LC 300 的还原，这里不需要 prev[] 数组：连续约束下每一位的前驱必然是紧邻的左边一格，
     * 记下起点就足以还原整段。约束越强，还原越省。
     * <p>
     * 若存在多个等长答案，返回<b>最靠左</b>的那一个（结算用严格大于，故先到者不被后来者顶替）。
     */
    public int[] longestContinuousIncreasingSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int bestStart = 0;      // 当前最优递增段的起点
        int bestLen = 1;        // 当前最优递增段的长度，单元素自成一段
        int start = 0;          // 正在探查的这一段的起点

        for (int i = 1; i <= nums.length; i++) {
            if (i == nums.length || nums[i] <= nums[i - 1]) {
                // i 走到数组长度时视为「断裂」，于是末尾那段也能在此被结算
                if (i - start > bestLen) {      // 严格大于：等长时保留更靠左的那一段
                    bestLen = i - start;
                    bestStart = start;
                }
                start = i;
            }
        }

        return Arrays.copyOfRange(nums, bestStart, bestStart + bestLen);
    }

    // ============ 方法四：对照——LC 300 可跳跃版的最长递增子序列（含还原） ============

    /**
     * des: 【对照方法】LC 300 的解法，允许跳过元素，因此必须用 prev[] 记录前驱、从终点回溯
     * <p>
     * 与本题的关键差异：因为存在「接在哪个 j 后面」这个真正的决策，dp 数组的每一位都可能指向
     * 一个不相邻的前驱，光靠起点无法还原序列，必须逐位记录 prev[i]，最后从最优终点沿 prev 倒着走回去。
     * <p>
     * 用 [1,3,5,4,7] 对照最能说明问题：
     * - 本方法返回 [1, 3, 5, 7]，长度 4（5 与 7 之间隔着 4，但 LC 300 允许跳过）；
     * - 方法三返回 [1, 3, 5]，长度 3（4 把递增段截断了，这是 LC 674 的正确答案）。
     * 同一份输入、两套合法答案，差别全在「连续」二字。
     * <p>
     * 时间复杂度 O(n^2)，空间复杂度 O(n)（dp 与 prev 各一维）。
     * 若需要 O(n log n) 的还原，则要在 tails 之外再维护 tailsIdx[k]（产生 tails[k] 的原下标）
     * 与 pos[i]（nums[i] 落在 tails 的位置），回溯时沿 tailsIdx 逐级往回跳，实现更复杂，本题不展开。
     */
    public int[] longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] dp = new int[n];          // dp[i] = 以 nums[i] 结尾的最长递增子序列长度
        int[] prev = new int[n];        // prev[i] = 接在 nums[i] 前面的那个下标，-1 表示无前驱
        Arrays.fill(prev, -1);

        int bestEnd = 0;                // 最长子序列的结尾下标

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;        // 记下这位前驱，回溯时全靠它
                }
            }
            if (dp[i] > dp[bestEnd]) {
                bestEnd = i;
            }
        }

        // 从终点沿 prev 一路往回走，得到的是逆序，倒着填入新数组即可自然还原为正序
        int[] ans = new int[dp[bestEnd]];
        int idx = ans.length - 1;
        for (int cur = bestEnd; cur != -1; cur = prev[cur]) {
            ans[idx--] = nums[cur];
        }

        return ans;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ==================== 第一部分：返回长度 ====================
        System.out.println("==================== 返回长度 ====================");
        System.out.println();

        // ---------- 用例 1：官方示例，[1,3,5] 连续；[1,3,5,7] 虽递增却被 4 隔开 ----------
        check(new int[]{1, 3, 5, 4, 7}, 3);

        // ---------- 用例 2：官方示例，全部相等，严格递增下相等不算递进，答案 1 ----------
        check(new int[]{2, 2, 2, 2, 2}, 1);

        // ---------- 用例 3：整体单调递增，整段即答案 ----------
        check(new int[]{1, 2, 3, 4, 5}, 5);

        // ---------- 用例 4：整体单调递减，每段长度都只有 1 ----------
        check(new int[]{5, 4, 3, 2, 1}, 1);

        // ---------- 用例 5：单元素，起点即终点 ----------
        check(new int[]{1}, 1);

        // ---------- 用例 6：最长递增段落在末尾，检验末尾结算有没有被漏掉 ----------
        check(new int[]{1, 2, 3, 1, 2, 3, 4}, 4);

        // ---------- 用例 7：最长递增段落在开头，检验断裂时的结算有没有算错 ----------
        check(new int[]{1, 2, 3, 4, 1}, 4);

        // ---------- 用例 8：相邻元素跨越整型边界，验证用 > 比较而非减法 ----------
        // 若写成 nums[i] - nums[i-1] > 0，4294967295 会溢出成 -1，递增被误判为递减，答案错成 1
        check(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}, 2);

        // ---------- 用例 9：负数区间同样适用，题目下探到 -10^9 ----------
        check(new int[]{-3, -2, -1, 0, -5, -4}, 4);

        // ---------- 用例 10：n = 10^4 的整体递增，验证题目上限规模下的正确性 ----------
        int size = 10000;
        int[] big = new int[size];
        for (int i = 0; i < size; i++) {
            big[i] = i;
        }
        check(big, size);

        // ==================== 第二部分：返回序列本身 ====================
        System.out.println("==================== 返回子数组本身 ====================");
        System.out.println();

        // ---------- 用例 1：官方示例，最长连续递增段是 [1,3,5] ----------
        checkArray(new int[]{1, 3, 5, 4, 7}, new int[]{1, 3, 5});

        // ---------- 用例 2：全部相等，只能返回单个元素，取最靠左的那个 ----------
        checkArray(new int[]{2, 2, 2, 2, 2}, new int[]{2});

        // ---------- 用例 3：整体单调递增，返回整个数组 ----------
        checkArray(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5});

        // ---------- 用例 4：整体单调递减，同样返回单个元素 ----------
        checkArray(new int[]{5, 4, 3, 2, 1}, new int[]{5});

        // ---------- 用例 5：单元素，返回它自己 ----------
        checkArray(new int[]{1}, new int[]{1});

        // ---------- 用例 6：最长递增段落在末尾，检验末尾段能否被正确切出 ----------
        checkArray(new int[]{1, 2, 3, 1, 2, 3, 4}, new int[]{1, 2, 3, 4});

        // ---------- 用例 7：最长递增段落在开头 ----------
        checkArray(new int[]{1, 2, 3, 4, 1}, new int[]{1, 2, 3, 4});

        // ---------- 用例 8：两段等长，验证「取最靠左」的约定 ----------
        checkArray(new int[]{1, 2, 3, 1, 2, 3}, new int[]{1, 2, 3});

        // ---------- 用例 9：负数区间，返回的仍是原数组中的真实切片 ----------
        checkArray(new int[]{-3, -2, -1, 0, -5, -4}, new int[]{-3, -2, -1, 0});

        // ---------- 用例 10：跨越整型边界的相邻元素 ----------
        checkArray(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE});

        // ==================== 附加演示：连续与可跳跃的答案并不相同 ====================
        demonstrateSubArrayVsSubsequence();
    }

    /** des: 两种求长度的解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, int expected) {
        FindLengthOfLCIS solution = new FindLengthOfLCIS();
        int r1 = solution.findLengthOfLCIS(nums);
        int r2 = solution.findLengthOfLCISByTwoPointers(nums);
        int r3 = solution.longestContinuousIncreasingSubArray(nums).length;

        boolean pass = r1 == expected && r2 == expected && r3 == expected;

        System.out.printf("nums = %s%n", brief(nums));
        System.out.printf("  期望值             : %d%n", expected);
        System.out.printf("  一次遍历           : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  双指针             : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  返回数组的长度     : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 校验返回序列的解法，逐个元素比对，并检查返回的确实是原数组的真实切片 */
    private static void checkArray(int[] nums, int[] expected) {
        FindLengthOfLCIS solution = new FindLengthOfLCIS();
        int[] actual = solution.longestContinuousIncreasingSubArray(nums);

        boolean pass = Arrays.equals(actual, expected);
        // 长度自洽性检查：返回数组的长度必须等于求长度解法给出的答案
        boolean lenConsistent = actual.length == solution.findLengthOfLCIS(nums);

        System.out.printf("nums = %s%n", brief(nums));
        System.out.printf("  期望序列           : %s%n", Arrays.toString(expected));
        System.out.printf("  实际返回           : %s%s%n", Arrays.toString(actual), pass ? "  ✅" : "  ❌");
        System.out.printf("  与求长度结果一致   : %s%n", lenConsistent ? "✅" : "❌");
        System.out.println(pass && lenConsistent ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 直观展示「连续」与「可跳跃」两种约束下，返回的序列并不相同 */
    private static void demonstrateSubArrayVsSubsequence() {
        int[] nums = {1, 3, 5, 4, 7};
        FindLengthOfLCIS solution = new FindLengthOfLCIS();
        int[] continuous = solution.longestContinuousIncreasingSubArray(nums);
        int[] jumpable = solution.longestIncreasingSubsequence(nums);

        System.out.println("========== 附：「连续」与「可跳跃」返回的序列并不相同 ==========");
        System.out.printf("nums = %s%n", Arrays.toString(nums));
        System.out.printf("  连续递增子数组（LC 674）    : %s，长度 %d%n",
                Arrays.toString(continuous), continuous.length);
        System.out.printf("  递增子序列（LC 300，可跳跃）: %s，长度 %d%n",
                Arrays.toString(jumpable), jumpable.length);
        System.out.println("  两者都是原数组中保持相对顺序的递增片段，");
        System.out.println("  但 [1,3,5,7] 里 5 与 7 之间隔着 4，不满足「连续」，");
        System.out.println("  因此本题的答案只能是 [1,3,5]。");
        System.out.println();
    }

    /** des: 数组过大时只打印长度与开头片段，避免测试输出刷屏 */
    private static String brief(int[] nums) {
        if (nums.length <= 12) {
            return Arrays.toString(nums);
        }
        return "长度 " + nums.length + " 的数组，前 6 个 = "
                + Arrays.toString(Arrays.copyOf(nums, 6)) + " ...";
    }
}
