package org.lwl.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 136 - 只出现一次的数字（提交时将类名改为 Solution）
 * <p>
 * 题目：给你一个「非空」整数数组 nums，除了某个元素只出现一次以外，其余每个元素均出现两次。
 * 找出那个只出现了一次的元素。要求算法线性时间复杂度，且只使用常量额外空间。
 * <p>
 * 关键理解：题目同时给出了两个约束——O(n) 时间、O(1) 空间，这两条把常见的「捷径」全部堵死了：
 * - 排序后相邻比较：时间 O(n log n)，超时；
 * - HashSet 辅助去重：空间 O(n)，超标；
 * - 求和相减（2 * 去重和 - 总和）：去重本身就要额外空间，同样超标。
 * 也就是说，标准解法必须是一个「一遍扫描、不借助任何容器」的技巧，而它正是位运算中的异或。
 * <p>
 * 另一个容易被忽略的推论：其余元素都出现两次，则数组长度必为奇数（2k + 1），
 * 因此 nums 永不为空，方法一定能返回一个真实存在的元素——这解释了为什么不需要考虑「找不到」的兜底分支。
 * <p>
 * 核心思路：全员异或（推荐，O(n) / O(1)）
 * 异或（^）有四条性质，恰好构成这个解法：
 * 1. a ^ a = 0            —— 相同抵消，这是「成对元素消失」的根本原因；
 * 2. a ^ 0 = a            —— 0 是异或的单位元，所以累加器初值取 0；
 * 3. a ^ b = b ^ a        —— 交换律；
 * 4. (a ^ b) ^ c = a ^ (b ^ c) —— 结合律。
 * 由 3、4 可知，n 个数异或的最终结果与「运算顺序」完全无关，只取决于每个数出现的次数是奇数次还是偶数次。
 * 于是把整个数组一路异或下去，出现两次的元素两两抵消为 0，最后剩下的必然是那个只出现一次的元素。
 * <p>
 * 换个更底层的视角看会更踏实：异或是不进位的加法，结果的第 k 个二进制位 = 所有元素第 k 位上 1 的「总个数 mod 2」。
 * 成对的元素在每一位上都贡献 2 个 1，是偶数，全部归零；唯独那个单独的元素在所有为 1 的位上贡献奇数个 1，被完整保留。
 * 这也顺带解释了为什么负数无需特殊处理：Java 的 int 以补码存储，而异或是纯位级操作，
 * 符号位与其它位一视同仁地参与运算，结果的符号自然正确。
 * <p>
 * 补充一个常见写法误区：累加器初值必须是 0，而不能是 nums[0]（那样会让第一个元素被漏算一次）。
 * 若写成「从 nums[1] 开始、初值 nums[0]」，虽然结果相同，但多了一条分支且容易在空数组上崩，
 * 直接 `int result = 0;` 配合增强 for 循环是既安全又最短的写法。
 * <p>
 * 时间复杂度 O(n)：一次遍历，每个元素做一次异或（位运算视为 O(1)）。
 * 空间复杂度 O(1)：仅用一个 int 变量保存中间结果，与 n 无关。
 * <p>
 * 举一反三：这道题是「异或抵消」思想的入门题，它的推广几乎覆盖了整个位运算专题——
 * - 若其余元素出现「三次」（LeetCode 137），异或就失效了（三次异或等于自身），
 *   需改为「按位统计每个二进制位上 1 的个数并对 3 取模」，或用 ones/twos 双状态机；
 * - 若「有两个」元素只出现一次（LeetCode 260），先全员异或得到 a ^ b，
 *   再取其中最低位的 1（x & -x）作为分组依据，把数组分成两组分别异或，即得 a 和 b；
 * - 若题目换成「找出缺失的数字」（LeetCode 268），可把下标也一起异或进来，思路完全同源；
 * - 更一般地，异或能筛出「出现奇数次」的所有数（其异或和即为答案），
 *   因为偶数次天然归零——凡是能抽象成「配对抵消」的问题，都值得先想一下异或。
 */
public class SingleNumber {

    // ============ 方法一：全员异或（推荐，满足题目全部要求） ============

    /**
     * des: 利用 a ^ a = 0、a ^ 0 = a 以及异或的交换律与结合律，让成对元素互相抵消
     */
    public int singleNumber(int[] nums) {
        // 初值 0 是异或的单位元：0 ^ x = x，保证第一个元素能被正确计入
        int result = 0;

        for (int num : nums) {
            // 出现两次的元素在这里被抵消为 0，只有单独出现的那个会被留下
            result ^= num;
        }

        return result;
    }

    // ============ 方法二：HashSet 辅助（O(n) 空间，用于对照理解） ============

    /**
     * des: 出现过就删除、没出现就加入，最终集合里只剩那个落单的元素
     * <p>
     * 时间 O(n)，空间 O(n)。虽然不满足题目的空间要求，但它是最贴近「字面语义」的写法，
     * 用它跑一遍结果可与异或解法互相印证。
     */
    public int singleNumberBySet(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            // add 返回 false 说明此前已存在，即成对出现，直接抵消掉
            if (!set.add(num)) {
                set.remove(num);
            }
        }

        // 抵消完毕，集合中仅剩只出现一次的元素
        return set.iterator().next();
    }

    // ============ 方法三：排序后两两比较（O(n log n)，用于对照理解） ============

    /**
     * des: 排序后成对元素必然相邻，按下标每次跳两步，第一对不相等的左值即为答案
     * <p>
     * 时间 O(n log n)，空间 O(n)（此处为不改动入参做了拷贝；若允许原地排序则额外空间为 O(1)）。
     * 同样不满足要求，但它的思路最直观，适合作为理解题意的起点。
     */
    public int singleNumberBySort(int[] nums) {
        // 拷贝一份，避免排序操作污染调用方的原始数组
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);

        // 每次跳两步：成对的元素一定落在 (0,1)、(2,3)、(4,5) ... 这样的相邻分组里
        for (int i = 0; i + 1 < copy.length; i += 2) {
            if (copy[i] != copy[i + 1]) {
                // 分组左值不等于右值，说明左值就是那个落单的元素
                return copy[i];
            }
        }

        // 前面的分组全部成对，说明落单的是排在最末尾的那个元素
        return copy[copy.length - 1];
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，落单元素在数组中间 ----------
        check(new int[]{2, 2, 1}, 1);

        // ---------- 用例 2：官方示例，落单元素在数组末尾 ----------
        check(new int[]{4, 1, 2, 1, 2}, 4);

        // ---------- 用例 3：官方示例，只有单个元素，直接返回它自己 ----------
        check(new int[]{1}, 1);

        // ---------- 用例 4：落单元素排在数组第一个位置 ----------
        check(new int[]{7, 3, 3, 5, 5, 9, 9}, 7);

        // ---------- 用例 5：落单元素是 0，验证「结果 0」不会被误判为无解 ----------
        check(new int[]{0, 3, 3}, 0);

        // ---------- 用例 6：含负数，验证补码下异或的符号位处理正确 ----------
        check(new int[]{-1, -1, -8}, -8);

        // ---------- 用例 7：整数与负数混合，验证跨符号位运算无误 ----------
        check(new int[]{-5, 3, 3, -7, -7}, -5);

        // ---------- 用例 8：含 Integer 边界值，验证不会发生溢出 ----------
        check(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE}, Integer.MIN_VALUE);

        // ---------- 用例 9：规模稍大的偶数长度数组，落单元素在最后 ----------
        check(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5}, 5);
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, int expected) {
        SingleNumber solution = new SingleNumber();
        int r1 = solution.singleNumber(nums);
        int r2 = solution.singleNumberBySet(nums);
        int r3 = solution.singleNumberBySort(nums);

        boolean pass = r1 == expected && r2 == expected && r3 == expected;
        System.out.printf("nums = %s%n", Arrays.toString(nums));
        System.out.printf("  期望值     : %d%n", expected);
        System.out.printf("  异或       : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  HashSet    : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  排序两两比 : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }
}
