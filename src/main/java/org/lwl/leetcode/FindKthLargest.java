package org.lwl.leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LeetCode 215 - 数组中的第 K 个最大元素（提交时将类名改为 Solution）
 * <p>
 * 题目：给定整数数组 nums 和整数 k，返回数组中第 k 个最大的元素。
 * 注意找的是数组「排序后」的第 k 个最大元素，而「不是」第 k 个不同的元素。
 * 必须设计时间复杂度为 O(n) 的算法。
 * <p>
 * 关键理解一：「第 k 个最大」不是「第 k 个不同」，这是题面特意强调的排除项。
 * 以示例 nums = [3,2,3,1,2,4,5,5,6], k = 4 为例：
 * 若误读成「去重后的第 4 大」，去重得 [6,5,4,3,2,1]，答案会是 3 —— 错的。
 * 正确做法是保留重复元素，升序为 [1,2,2,3,3,4,5,5,6]，降序为 [6,5,5,4,...]，
 * 第 4 个是 4。重复元素各自占用一个名次，本题是「纯粹的定位问题」，不涉及计数与去重。
 * <p>
 * 关键理解二：降序第 k 个 ⟺ 升序下标 n-k，这是把题面转成代码的关键一步。
 * n = 6 时：第 1 大（最大 6）在升序下标 5 = n-1；第 6 大（最小 1）在升序下标 0 = n-n。
 * 所以统一写成 target = n - k，全程按「升序 + 目标下标」思考，少绕一层降序的弯。
 * <p>
 * 关键理解三：题目要求 O(n)，等于明说「不许排序」。排序下界是 O(n log n)，
 * 必须找一种只做「部分排序」的算法 —— 这正是快速选择（QuickSelect）的用武之地。
 * <p>
 * 核心思路：快速选择，复用快排的 partition，但只递归一侧
 * partition 有一个常被忽略的性质：返回的 pivotIndex 就是该元素在「最终有序数组」中的绝对位置，
 * 一次划分之后永久确定，不会再变。快排拿到这个信息后「两边都递归」（因为要全排序）；
 * 而本题只要一个位置，于是：
 * - pivotIndex == target → 它就是答案，立即返回；
 * - pivotIndex &lt;  target → 答案必在右侧，左半区「整块丢弃」；
 * - pivotIndex &gt;  target → 答案必在左侧，右半区「整块丢弃」。
 * 每轮划分都砍掉一半数据，这就是快选：分治 + 剪枝。
 * <p>
 * 为什么平均是 O(n)：快排的递归式是 T(n) = 2T(n/2) + O(n) = O(n log n)（两边都管）；
 * 快选只走一边，T(n) = T(n/2) + O(n) = n + n/2 + n/4 + ... &lt; 2n = O(n)。
 * 「同一份 partition 代码，只因为少递归一边，复杂度就从 O(n log n) 掉到 O(n)」，
 * 这是分治里剪枝价值最直白的体现。
 * <p>
 * 坑一：最坏情况退化为 O(n²)，必须随机化基准。
 * 若固定取 nums[high] 作基准，遇到「已排序数组」[1,2,3,4,5] 时，
 * 每轮 partition 都返回 high，只排除 1 个元素，T(n) = n + (n-1) + ... = O(n²)。
 * 「输入有序」反而成了最坏情况 —— 这在工程上很常见（如按时间递增的日志）。
 * 解法：每轮随机选一个元素与 nums[high] 交换，让「选到极端值」的概率极低，期望复杂度回到 O(n)。
 * <p>
 * 坑二：大量重复元素时两路划分仍会退化，需要三路划分。
 * 即使随机化，若数组「全部相等」（如 [2,2,2,2,2]），无论选谁当 pivot 都是 2；
 * Lomuto 用 `&lt; pivot` 判断，无元素满足，pivotIndex 恒等于 low，每轮又只排除 1 个，仍是 O(n²)。
 * 解法：三路划分（荷兰国旗问题），把区间切成 [ &lt; pivot | == pivot | &gt; pivot ] 三段，
 * 等于 pivot 的整段一次全部就位。全相等数组一轮结束。
 * JDK 的 Arrays.sort 对基本类型用双轴快排 + 三路划分，正是出于同样的考虑。
 * <p>
 * 方法一：快速选择 + Lomuto 划分 + 随机基准（推荐，平均 O(n)，空间 O(1)）
 * 方法二：快速选择 + 三路划分（处理大量重复元素，避免退化）
 * 方法三：小顶堆（O(n log k)，适合 n 极大而 k 极小的场景，如十亿行日志取 Top 10）
 * 方法四：直接排序（O(n log n)，最直观的暴力解，用于对照）
 * <p>
 * 时间复杂度：方法一/二平均 O(n)、最坏 O(n²)（方法二在重复元素场景下抗退化能力更强）；
 * 方法三 O(n log k)；方法四 O(n log n)。
 * 空间复杂度：方法一/二 O(1)（原地划分，已改用迭代写法，不占递归栈）；
 * 方法三 O(k)；方法四 O(n)（此处拷贝了一份，避免污染调用方数组）。
 * <p>
 * 举一反三：
 * 1. 求「第 k 小」只需把 target 从 n-k 改成 k-1，其余代码一字不动 —— 这题的价值就在于此。
 * 2. 快选与二分查找同属「排除法」家族：二分靠有序性砍一半，快选靠 partition 砍一半，
 *    两者都把 O(n) 的线性扫描降到了 O(log n) 与平均 O(n) 量级，内核都是「一次比较，排除一大片」。
 * 3. 若要求「最坏 O(n)」而非「平均 O(n)」，需用 BFPRT 算法（中位数的中位数）选基准，
 *    它保证每次划分至少砍掉 3/10，代价是常数极大，实践中反而常慢于随机化快选。
 * 4. Top K 家族问题同源：LC 347 前 K 个高频元素、LC 973 最接近原点的 K 个点、
 *    LC 692 前 K 个高频单词，都可用「快选 + 排序」或「堆」两条路线解决。
 * 5. 若数据是「流式」到达而非一次性给全，快选失效（无法回看全局），必须改用堆 ——
 *    参见 LC 295 数据流的中位数。
 */
public class FindKthLargest {

    // ============ 方法一：快速选择 + Lomuto 划分 + 随机基准（推荐，平均 O(n) 空间 O(1)） ============

    /**
     * des: 降序第 k 大 = 升序下标 n-k，用快选不断砍掉一半区间直到命中
     * <p>
     * 时间平均 O(n)（最坏 O(n²)，随机基准后概率极低），空间 O(1)。
     */
    public int findKthLargest(int[] nums, int k) {
        // 降序第 k 个 ⟺ 升序下标 n-k（见类注释「关键理解二」）
        int target = nums.length - k;
        return quickSelect(nums, target);
    }

    /**
     * des: 快选的迭代写法 —— 只更新区间边界，不走递归，天然省下递归栈
     * <p>
     * 为什么改成迭代而非递归？因为快选「只走一边」，
     * 「更新 low 或 high 后重新划分」与「递归调用自己」完全等价，
     * 写成循环既直观，又少了一层函数调用开销。
     * <p>
     * 循环不变量：target 始终落在 [low, high] 内。
     * 每轮 partition 后据 pivotIndex 与 target 的关系收缩区间，循环退出时 low == high == target。
     */
    private int quickSelect(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int pivotIndex = partition(nums, low, high);

            if (pivotIndex == target) {
                // 基准落在目标位置，它就是最终有序数组该位置上的值，直接命中
                return nums[pivotIndex];
            } else if (pivotIndex < target) {
                // 目标在右侧：[low, pivotIndex] 整块丢弃
                low = pivotIndex + 1;
            } else {
                // 目标在左侧：[pivotIndex, high] 整块丢弃
                high = pivotIndex - 1;
            }
        }

        // 区间收缩到只剩一个元素，由不变量知它正是 target，答案即此元素
        return nums[low];
    }

    /**
     * des: Lomuto 划分，返回基准元素的最终下标（与 QuickSort.partition 同源）
     * <p>
     * 与 QuickSort 的唯一差别是开头多了一步「随机选基准」：
     * 随机挑一个下标与 high 交换，再沿用「以末尾为基准」的经典流程，
     * 从而避开「有序输入」这一最坏情况（见类注释「坑一」）。
     * <p>
     * 追踪示例：nums = [3,2,1,5,6,4]，low = 0，high = 5，假设随机数恰好落在 high，
     * 即 pivot = nums[5] = 4，初始 i = -1：
     * <p>
     * ┌─────┬────────┬─────────────┬─────┬───────────┬──────────────┐
     * │  j  │ arr[j] │ arr[j] < 4? │  i  │   操作    │   数组变化   │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 0   │ 3      │ ✓           │ 0   │ swap(0,0) │ 3 2 1 5 6 4  │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 1   │ 2      │ ✓           │ 1   │ swap(1,1) │ 3 2 1 5 6 4  │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 2   │ 1      │ ✓           │ 2   │ swap(2,2) │ 3 2 1 5 6 4  │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 3   │ 5      │ ✗           │ 2   │ 不动      │ 3 2 1 5 6 4  │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 4   │ 6      │ ✗           │ 2   │ 不动      │ 3 2 1 5 6 4  │
     * └─────┴────────┴─────────────┴─────┴───────────┴──────────────┘
     * <p>
     * 循环结束 i = 2，arr[0..2] = {3,2,1} 均小于 4，arr[3..4] = {5,6} 均不小于 4。
     * 最后 swap(i+1=3, high=5) 把基准 4 归位 → 3 2 1 4 6 5，返回 3。
     */
    private int partition(int[] nums, int low, int high) {
        // 随机化：把随机位置的元素换到 high，随后沿用「以末尾为基准」的流程
        // 若不随机化，输入为已排序数组时每轮只排除 1 个元素，退化为 O(n²)
        int randomIndex = ThreadLocalRandom.current().nextInt(low, high + 1);
        swap(nums, randomIndex, high);

        int pivot = nums[high];
        int i = low - 1; // i 是「小于基准」区域的右边界

        for (int j = low; j < high; j++) {
            if (nums[j] < pivot) {
                i++;
                swap(nums, i, j);
            }
        }

        swap(nums, i + 1, high); // 基准归位到「小于区」之后
        return i + 1;
    }

    // ============ 方法二：快速选择 + 三路划分（大量重复元素时不退化） ============

    /**
     * des: 三路划分把区间切成「小于 / 等于 / 大于」三段，等于段一次全部就位
     * <p>
     * 为什么需要它？方法一的 Lomuto 用 `&lt; pivot` 判断，
     * 当数组存在大量重复（尤其「全相等」）时，没有任何元素满足 `&lt; pivot`，
     * pivotIndex 恒等于 low，每轮只排除 1 个元素，随机化也救不了 —— 仍是 O(n²)。
     * <p>
     * 三路划分（荷兰国旗问题）把等于 pivot 的元素「聚成一整段」：
     * <pre>
     *   [ low, lt ) 全部 &lt; pivot
     *   [ lt , gt ] 全部 == pivot   ← 这一段整体归位，可以一次跳过
     *   ( gt , high] 全部 &gt; pivot
     * </pre>
     * 于是判断条件变为：target &lt; lt 去左段，target &gt; gt 去右段，
     * 否则 target 落在「等于段」内 —— 该段所有值都相同，直接返回 nums[target]。
     * 全相等数组一轮即结束。
     */
    public int findKthLargestBy3Way(int[] nums, int k) {
        int target = nums.length - k;
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            // 同样随机化，规避有序输入导致的退化
            int randomIndex = ThreadLocalRandom.current().nextInt(low, high + 1);
            swap(nums, randomIndex, low); // 把基准换到 low 作为「哨兵」，便于三路推进
            int pivot = nums[low];

            int lt = low;       // 「等于区」左边界，[low, lt) 是小于区
            int i = low + 1;    // 当前扫描指针
            int gt = high;      // 「等于区」右边界，(gt, high] 是大于区

            while (i <= gt) {
                if (nums[i] < pivot) {
                    // 换到小于区末尾；换过来的必定 == pivot（来自等于区），故 i 可安全后移
                    swap(nums, lt++, i++);
                } else if (nums[i] > pivot) {
                    // 换到大于区开头；换过来的元素「尚未比较」，故 i 不动
                    swap(nums, i, gt--);
                } else {
                    // 等于基准，留在等于区，继续扫描
                    i++;
                }
            }

            // 此刻 [lt, gt] 全是等于 pivot 的元素
            if (target < lt) {
                high = lt - 1;      // 答案在小于区
            } else if (target > gt) {
                low = gt + 1;       // 答案在大于区
            } else {
                // target 落在等于区，整段值相同，答案即 pivot
                return nums[target];
            }
        }

        // 区间收缩到只剩一个元素，它正是 target
        return nums[low];
    }

    // ============ 方法三：小顶堆（O(n log k)，适合 n 极大而 k 极小） ============

    /**
     * des: 维护一个大小为 k 的小顶堆，堆里始终是「迄今最大的 k 个数」，堆顶即第 k 大
     * <p>
     * 为什么是「小顶堆」而不是大顶堆？因为要淘汰的是这 k 个数里「最小的那个」——
     * 每来一个新数，若它比堆顶大，说明堆顶不配留在 Top K 里，弹出堆顶、压入新数。
     * 小顶堆的堆顶恰好是「最小」，淘汰最方便，所以求「第 k 大」用小顶堆。
     * （对称地，求「第 k 小」要用大顶堆。）
     * <p>
     * 与方法一/二的取舍：本方法时间 O(n log k)、空间 O(k)，
     * 只有在「k 远小于 n」时才真正划算（例如十亿行日志取 Top 10，堆始终不超过 10 个元素）。
     * 更关键的是它「不需要一次性拿到全部数据」——数据以流的形式到达也能工作，
     * 这是快选做不到的（快选必须能随机访问整个数组）。
     */
    public int findKthLargestByHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        for (int num : nums) {
            minHeap.offer(num);
            // 堆超过 k 个就把最小的挤出去，保证堆内恒为「迄今最大的 k 个数」
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // 堆顶是这 k 个数中最小的，也就是全数组第 k 大
        return minHeap.peek();
    }

    // ============ 方法四：直接排序（O(n log n)，最直观的暴力解，用于对照） ============

    /**
     * des: 升序排序后取下标 n-k，一行搞定，但复杂度不达标
     * <p>
     * 时间 O(n log n)，空间 O(n)（此处拷贝一份，避免排序污染调用方数组）。
     * 它是本题的「正确但不够快」基准线：理解快选的价值，最好的参照物就是它。
     */
    public int findKthLargestBySort(int[] nums, int k) {
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        return copy[copy.length - k];
    }

    /** des: 交换数组中的两个元素 */
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，降序为 [6,5,4,3,2,1]，第 2 大 = 5 ----------
        check(new int[]{3, 2, 1, 5, 6, 4}, 2, 5);

        // ---------- 用例 2：官方示例，含重复元素（5 与 2 各出现两次）----------
        // 升序 [1,2,2,3,3,4,5,5,6]，下标 9-4=5 处为 4
        check(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, 4);

        // ---------- 用例 3：单元素，k 只能是 1，答案即该元素 ----------
        check(new int[]{1}, 1, 1);

        // ---------- 用例 4：k = 1，求最大值（target = n-1，恰好是升序末位）----------
        check(new int[]{7, 3, 9, 1}, 1, 9);

        // ---------- 用例 5：k = n，求最小值（target = 0，恰好是升序首位）----------
        check(new int[]{7, 3, 9, 1}, 4, 1);

        // ---------- 用例 6：全部相等，验证三路划分的抗退化能力（方法一的软肋）----------
        check(new int[]{2, 2, 2, 2, 2}, 3, 2);

        // ---------- 用例 7：已升序，验证随机基准能规避「有序输入」这一最坏情况 ----------
        check(new int[]{1, 2, 3, 4, 5, 6}, 2, 5);

        // ---------- 用例 8：已降序，另一种极端排列 ----------
        check(new int[]{6, 5, 4, 3, 2, 1}, 5, 2);

        // ---------- 用例 9：含负数，验证比较逻辑对符号不敏感 ----------
        check(new int[]{-1, -5, 3, 0, -2}, 2, 0);

        // ---------- 用例 10：含 Integer 边界值，快选只做大小比较，不涉及算术不会溢出 ----------
        check(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 1, Integer.MAX_VALUE);
        check(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 3, Integer.MIN_VALUE);

        // ---------- 用例 11：仅两个元素，最小的区间规模 ----------
        check(new int[]{2, 1}, 1, 2);
        check(new int[]{2, 1}, 2, 1);

        // ---------- 用例 12：密集重复，且 k 恰好卡在重复值内部，最能暴露「去重」误读 ----------
        // 升序 [1,1,5,5,5,5,9,9,9,9]，降序 [9,9,9,9,5,5,5,5,1,1]。
        // 9 共 4 个占第 1~4 名，故第 4 大 = 9；若误读成「第 k 个不同」，去重只有 [9,5,1] 三个值，
        // k=4 会直接越界 —— 这个用例正是题目那句强调的最佳反例。
        check(new int[]{5, 5, 5, 1, 1, 9, 9, 9, 9, 5}, 4, 9);
        // 紧挨着再跨一步：第 5 大落到 5 上，验证「重复值名次连续、不跳过」
        check(new int[]{5, 5, 5, 1, 1, 9, 9, 9, 9, 5}, 5, 5);
    }

    /** des: 四种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, int k, int expected) {
        FindKthLargest solution = new FindKthLargest();

        // 方法一/二/三都会原地修改数组，故各传一份副本；方法四内部自带拷贝
        int r1 = solution.findKthLargest(nums.clone(), k);
        int r2 = solution.findKthLargestBy3Way(nums.clone(), k);
        int r3 = solution.findKthLargestByHeap(nums.clone(), k);
        int r4 = solution.findKthLargestBySort(nums, k);

        boolean pass = r1 == expected && r2 == expected && r3 == expected && r4 == expected;
        System.out.printf("nums = %s, k = %d%n", Arrays.toString(nums), k);
        System.out.printf("  期望值                : %d%n", expected);
        System.out.printf("  快选（Lomuto）+ 随机  : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  快选（三路划分）      : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  小顶堆                : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.printf("  直接排序              : %d%s%n", r4, r4 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }
}
