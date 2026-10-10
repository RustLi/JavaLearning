package org.lwl.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 169 - 多数元素（提交时将类名改为 Solution）
 * <p>
 * 题目：给定一个大小为 n 的数组 nums，返回其中的多数元素。多数元素是指在数组中出现次数「大于 ⌊n/2⌋」的元素。
 * 可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * <p>
 * 关键理解一：这个「大于 n/2」的门槛是严格的，它带来两个重要推论。
 * 其一，多数元素「最多只能有一个」——反证：若 A、B 都超过 n/2，则二者次数之和大于 n，与数组长度矛盾。
 * 其二，多数元素在数量上「压倒了所有其它元素的集合」——非多数元素的个数之和小于 n/2 < 多数元素的个数。
 * 第二条正是投票算法能够成立的根基。
 * <p>
 * 关键理解二：题目保证「总是存在多数元素」，这一点比看上去重要。
 * Boyer-Moore 投票算法本身在「不存在多数元素」时也会返回一个候选值（例如 [1,2,3] 会返回 3），
 * 只是那个值并非多数元素——它不报错，只是给错答案，属于静默失败。
 * 因此标准做法是在投票结束后「再遍历一次」校验候选值的真实出现次数；
 * 本题因为题目给了保证，这一趟可以省略（见方法四的对比说明）。
 * <p>
 * 核心思路：Boyer-Moore 投票算法（推荐，O(n) / O(1)）
 * 把这题想象成一场「擂台赛」或「阵营对拼」：
 * - 数组里每个元素代表一张选票，支持各自的门派；
 * - 擂台上站着一位擂主 candidate，身旁记着它的净人数 count；
 * - 来的票与自己同门 → count++（阵营壮大）；
 * - 来的票是别的门派 → 双方各倒下一个人，count--（对拼消耗）；
 * - 一旦 count 归零，说明擂主阵营全军覆没，那么下一个上场的人直接成为新擂主。
 * 遍历结束时仍站在擂台上的门派，就是多数派。
 * <p>
 * 为什么「对拼消耗」不会误伤多数元素？关键在于多数元素的人数比所有反对者加起来还多。
 * 哪怕让反对者一对一地去换掉多数元素，反对者也会先耗尽——换到最后，擂台上剩下的必然是多数元素。
 * 形式化地说：每次 count 归零，意味着刚过去的那段前缀里，多数元素与非多数元素「恰好各出局了同样多的人」。
 * 设多数元素在前缀中出现了 k 次、非多数元素也出现了 k 次，那么在后缀中它出现 (total - k) 次，
 * 而后缀长度为 (n - 2k)，由 total > n/2 可得 total - k > n/2 - k = (n - 2k)/2，
 * 即「在后缀中它依然是多数元素」。于是问题规模可以不断缩小而不改变结论，归纳到底，答案就是最后的擂主。
 * <p>
 * 与前一道题的联系：本题与 LeetCode 136「只出现一次的数字」同属「配对抵消」思想家族，但形态不同。
 * - LC 136 用的是异或，要求「完全相同的元素严格一一配对」，是「精确抵消」；
 * - 本题用的是投票，允许「不相等的元素也互相消耗」，是「模糊抵消」。
 * - 异或之所以更严格，是因为它作用在比特位这个精确的结构上；而投票只关心「是不是同一个候选」，粒度更粗。
 * 两者共享同一个内核：只要某种「两两消除」的规则保证答案不会被消掉，剩下的就一定是答案。
 * <p>
 * 时间复杂度 O(n)：一次遍历，每个元素 O(1) 处理。
 * 空间复杂度 O(1)：仅用 candidate 与 count 两个变量，与 n 无关。
 * <p>
 * 举一反三：把「大于 n/2」的门槛换成「大于 n/3」，就得到 LeetCode 229 求众数 II。
 * 此时最多有 2 个多数元素，投票算法也随之推广为「多候选人」版本：维护两组 (candidate, count)，
 * 新票先与已有候选人比对，都不匹配则消耗票数最少（或为 0）的那一个。
 * 更一般地，出现次数超过 ⌊n/k⌋ 的元素最多有 k - 1 个，对应 k - 1 组候选——
 * 这就是 Boyer-Moore 的通用形式。另外 LeetCode 面试题 17.10「主要元素」与本题题面相同，
 * 但「不保证多数元素存在」，必须补上第二次遍历做校验，正好是本题注释里那个坑的正面考察。
 */
public class MajorityElement {

    // ============ 方法一：Boyer-Moore 投票算法（推荐，时间 O(n) 空间 O(1)） ============

    /**
     * des: 擂台对拼，同门加人、异门消耗，归零则换擂主，最后留下的即多数元素
     */
    public int majorityElement(int[] nums) {
        // 当前擂主；初值不重要，因为 count 为 0 时会在第一次迭代被立刻覆盖
        int candidate = 0;
        // 擂主阵营的净人数
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                // 上一任擂主阵营已全军覆没，当前元素上场成为新擂主
                candidate = num;
            }
            // 同门则壮大阵营，异门则一对一消耗
            count += (num == candidate) ? 1 : -1;
        }

        // 题目保证多数元素存在，故此刻的擂主必为答案（若不作保证，需补方法四的校验）
        return candidate;
    }

    // ============ 方法二：哈希表计数（O(n) 空间，用于对照理解） ============

    /**
     * des: 直接统计每个元素的出现次数，边统计边判断是否已过半
     * <p>
     * 时间 O(n)，空间 O(n)。这是最贴近题面字面定义的写法，代码最易懂，
     * 代价是占用与 n 成正比的空间，不满足「常量额外空间」的理想目标（本题并未强制要求）。
     */
    public int majorityElementByMap(int[] nums) {
        // 多数元素的门槛：出现次数必须严格大于 n / 2
        int threshold = nums.length / 2;
        Map<Integer, Integer> counter = new HashMap<>();

        for (int num : nums) {
            // 取出历史次数 +1；未出现过则视为 0 +1
            int times = counter.getOrDefault(num, 0) + 1;

            // 提前返回：一旦某个数过半即可确定答案，无需再扫描剩余元素
            if (times > threshold) {
                return num;
            }
            counter.put(num, times);
        }

        // 题目保证存在多数元素，正常流程不会走到这里
        throw new IllegalArgumentException("不存在多数元素");
    }

    // ============ 方法三：排序后取中位数（O(n log n)，用于对照理解） ============

    /**
     * des: 排序后下标 ⌊n/2⌋ 处的元素必然被多数元素占据，直接返回即可
     * <p>
     * 为什么中位数一定是多数元素？设多数元素为 M，它在排序后占据的连续区间为 [i, j]。
     * 由「M 的个数 > n/2」可知 j - i + 1 > n/2，即 j - i ≥ ⌊n/2⌋，说明这段区间长度足以跨越数组正中。
     * 反证更直接：若 nums[⌊n/2⌋] 不是 M，
     * - M 若全在中位下标左侧，则 M 的个数 ≤ ⌊n/2⌋ ≤ n/2，与 M 过半矛盾；
     * - M 若全在中位下标右侧，则 M 的个数 ≤ n - 1 - ⌊n/2⌋，n 为偶数时为 n/2 - 1，
     *   n 为奇数时为 (n-1)/2，两种情况都小于 n/2，同样矛盾。
     * <p>
     * 时间 O(n log n)，空间 O(n)（此处为不改动入参做了拷贝；若允许原地排序则额外空间为 O(log n) 的递归栈）。
     */
    public int majorityElementBySort(int[] nums) {
        // 拷贝一份，避免排序操作污染调用方的原始数组
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);

        // 中位下标必被多数元素占据，直接返回
        return copy[copy.length / 2];
    }

    // ============ 方法四：投票 + 二次校验（题目未保证存在多数元素时的正确姿势） ============

    /**
     * des: 投票只是「选出一个候选」，得到候选后必须回头数一遍它是否真的过半
     * <p>
     * 对比用例 [1, 2, 3]：不存在多数元素，但方法一会毫无征兆地返回 3。
     * 这类「不报错、只给错答案」的静默失败在线上极难排查，因此但凡题目没有明确保证多数元素存在，
     * 都应补上这一趟校验，把静默失败转换成显式失败。
     */
    public int majorityElementWithVerify(int[] nums) {
        // 第一趟：投票选出候选值
        int candidate = majorityElement(nums);

        // 第二趟：实地统计候选值的真实出现次数
        int times = 0;
        for (int num : nums) {
            if (num == candidate) {
                times++;
            }
        }

        // 校验未通过说明多数元素并不存在，显式抛错而不是返回一个错误的数
        if (times <= nums.length / 2) {
            throw new IllegalArgumentException("不存在多数元素");
        }
        return candidate;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，3 出现 2 次 > 3/2 = 1 ----------
        check(new int[]{3, 2, 3}, 3);

        // ---------- 用例 2：官方示例，2 出现 4 次 > 7/2 = 3 ----------
        check(new int[]{2, 2, 1, 1, 1, 2, 2}, 2);

        // ---------- 用例 3：官方示例，落单元素本身就是多数元素 ----------
        check(new int[]{1}, 1);

        // ---------- 用例 4：n = 2，门槛是严格大于 1，故必须两次都是同一个数 ----------
        check(new int[]{1, 1}, 1);
        check(new int[]{5, 5}, 5);

        // ---------- 用例 5：多数元素恰好比半数多一个，是最紧的边界 ----------
        check(new int[]{1, 2, 1}, 1);
        check(new int[]{6, 5, 5}, 5);

        // ---------- 用例 6：多数元素是 0，验证「值为 0」不会被误判 ----------
        check(new int[]{0, 0, 1}, 0);

        // ---------- 用例 7：含负数，验证比较逻辑对符号不敏感 ----------
        check(new int[]{-1, -1, 2}, -1);

        // ---------- 用例 8：含 Integer 边界值，投票只做相等比较，不涉及算术不会溢出 ----------
        check(new int[]{Integer.MAX_VALUE, 1, Integer.MAX_VALUE}, Integer.MAX_VALUE);

        // ---------- 用例 9：多数元素在数组开头，投票过程不会切换擂主 ----------
        check(new int[]{9, 9, 9, 1, 2}, 9);

        // ---------- 用例 10：多数元素在数组末尾，验证擂主中途被换掉后仍能赢回 ----------
        check(new int[]{1, 2, 3, 9, 9, 9, 9}, 9);

        // ---------- 用例 11：验证「不存在多数元素」时，二次校验能拦住静默失败 ----------
        checkNoMajority(new int[]{1, 2, 3});

        // ---------- 用例 12：验证「恰好等于半数」不算多数元素（门槛是严格大于） ----------
        checkNoMajority(new int[]{1, 1, 2, 2});
    }

    /** des: 四种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, int expected) {
        MajorityElement solution = new MajorityElement();
        int r1 = solution.majorityElement(nums);
        int r2 = solution.majorityElementByMap(nums);
        int r3 = solution.majorityElementBySort(nums);
        int r4 = solution.majorityElementWithVerify(nums);

        boolean pass = r1 == expected && r2 == expected && r3 == expected && r4 == expected;
        System.out.printf("nums = %s%n", Arrays.toString(nums));
        System.out.printf("  期望值       : %d%n", expected);
        System.out.printf("  投票算法     : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  哈希表计数   : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  排序取中位数 : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.printf("  投票+校验    : %d%s%n", r4, r4 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 专门验证「不存在多数元素」的场景：投票会给出错误候选，唯有二次校验能识破 */
    private static void checkNoMajority(int[] nums) {
        MajorityElement solution = new MajorityElement();

        System.out.printf("nums = %s（不存在多数元素）%n", Arrays.toString(nums));
        // 投票算法的静默失败：它照样返回一个数，且不抛异常
        System.out.printf("  投票算法（无校验）: %d  ⚠️ 静默返回了错误答案%n", solution.majorityElement(nums));

        // 二次校验能正确识别出「候选值并未过半」
        try {
            solution.majorityElementWithVerify(nums);
            System.out.println("  投票+校验          : 未抛异常  ❌");
        } catch (IllegalArgumentException e) {
            System.out.printf("  投票+校验          : 抛出异常「%s」  ✅%n", e.getMessage());
        }
        System.out.println();
    }
}
