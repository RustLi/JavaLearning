package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 448 - 找到所有数组中消失的数字（提交时将类名改为 Solution）
 * <p>
 * 题目：给你一个含 n 个整数的数组 nums，其中 nums[i] 在区间 [1, n] 内。
 * 请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
 * <p>
 * 关键理解：这道题的题眼不在「找缺失」，而在那句看似多余的约束——nums[i] ∈ [1, n]。
 * 数组长度是 n，合法下标是 [0, n-1]，而元素值域恰好是 [1, n]，两者大小相同、只差一个偏移：
 * <pre>
 *     数字 v  ←→  下标 v - 1
 * </pre>
 * 也就是说，题目白送了一个「哈希函数」：任何一个数字 v，都能直接算出它该待的下标位置。
 * 于是数组本身就可以被当作一张哈希表（或一个 boolean[]）来使用，不必再另开容器。
 * <p>
 * 如果没有这条约束（比如 nums 里可以是任意整数），本题就退化成一道普通的集合题，
 * 只能老老实实建 HashSet，空间 O(n)——约束的宽窄直接决定了最优解的形式。
 * <p>
 * 核心思路一：负号标记（推荐，O(n) / O(1)）
 * 数组里原本全是正数（[1, n] 不含 0），所以「正负号」这一位信息是白送的，可以借来当标记位：
 * 1. 第一遍扫描：每读到一个数字 v，就去下标 v - 1 处把它变成负数，表示「v 出现过」；
 * 2. 第二遍扫描：凡是仍为正数的下标 i，说明数字 i + 1 从未被标记过，即它是缺失的。
 * <p>
 * 这个方法有两处极易踩空的细节，缺一不可：
 * - 读的时候必须取绝对值（Math.abs）：第一遍扫描到后半程时，当前位置的数可能早被前一轮打了负号，
 *   直接拿来当下标会算错位置，甚至数组越界。
 * - 写的时候必须先判断是否为正数：同一个数字出现多次时会重复标记，
 *   若不加判断地盲目取反，「负负得正」会把已有标记抹掉，导致结果多出一批假缺失。
 *   反例：[2, 2] 若不判断，nums[1] 会被取反两次又变回正数，最终误判为「1、2 都缺失」。
 * <p>
 * 核心思路二：交换归位（同样 O(n) / O(1)）
 * 换一个角度：数字 v 的「家」就是下标 v - 1。那就不打标记，直接把每个数字送回它家里。
 * 送完之后再扫一遍，谁不在自己家里，谁代表的数字就是缺失的。
 * 循环终止条件 nums[i] != nums[nums[i] - 1] 一箭双雕：既表示「已经到家了」，
 * 也表示「家里已经住着一个相同的值」（重复元素），后者正是防止死循环的关键。
 * <p>
 * 关于「修改输入数组」的提醒：上面两种 O(1) 空间的解法都会就地改写 nums。
 * 这在刷题场景下是默认允许的，但工程中若要保留调用方的原始数据，
 * 就必须先拷贝一份，此时空间退化为 O(n)，与 HashSet 解法再无优势可言——这是面试里常见的追问点。
 * <p>
 * 时间复杂度 O(n)：两种原地解法都是「至多两次线性扫描」；
 * 交换归位虽然内层套了 while，但每次交换都让一个位置永久归位，总交换次数不超过 n，均摊下来仍是 O(n)。
 * 空间复杂度 O(1)：除返回结果外，只用了常数个临时变量（返回值按 LeetCode 惯例不计入辅助空间）。
 * 作为对照，HashSet 解法时间 O(n)、空间 O(n)。
 * <p>
 * 举一反三：这类题统称「原地哈希」，共同前提是「值域大小与数组长度同阶」，一旦满足，数组即可当表用——
 * - LeetCode 442（数组中重复的数据）：与本题共用同一套标记骨架，
 *   只是收集时机相反——本题收「从未变负的位置」，442 收「标记时发现已是负数」的位置；
 * - LeetCode 41（缺失的第一个正数）：同为原地哈希，但值域不再保证落在 [1, n]，
 *   需要先把范围外的数换到数组尾部剔除，再套同样的标记逻辑；
 * - LeetCode 268（丢失的数字）：值域 [0, n] 与下标 [0, n-1] 恰好错开一位，
 *   求和、异或、原地标记三条路都走得通；
 * - LeetCode 645（错误的集合）：同时找出「重复的」和「缺失的」，正是 442 与 448 的合体；
 * - 再往上抽象一层：凡是看到「n 个数、值域 1..n」这类抽屉原理式的描述，
 *   第一反应就该是「值即下标」，而不是「开个 HashMap」。
 */
public class FindDisappearedNumbers {

    // ============ 方法一：负号标记（推荐，满足题目全部要求） ============

    /**
     * des: 借用数字正负号当标记位，把「值 → 下标」的映射用在原地，省掉额外的哈希表
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;

        // 第一遍：每读到一个数字，就把「它该待的下标」处改成负数，表示这个数字出现过
        for (int i = 0; i < n; i++) {
            // 当前位置的值可能已被前一轮打过负号，必须取绝对值还原出它原本代表的数字
            int index = Math.abs(nums[i]) - 1;

            // 只在正数时取反：重复出现的数字若二次取反，会把标记抹掉（负负得正）
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }

        // 第二遍：没被标记成负数的下标，就对应着从未出现过的数字
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                // 下标 i 对应的数字是 i + 1
                result.add(i + 1);
            }
        }

        return result;
    }

    // ============ 方法二：交换归位（同样 O(n) / O(1)） ============

    /**
     * des: 把每个数字直接交换到它应该在的下标上，位置对不上的就是缺失的数字
     */
    public List<Integer> findByCycleSort(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // 数字 nums[i] 的家是下标 nums[i] - 1，只要它还没到家就一直换过去。
            // 条件同时兼顾了重复元素：家里已住着同一个值时便不再交换，否则会陷入死循环。
            // 每完成一次交换，目标下标就永久归位，因此总交换次数不超过 n。
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                result.add(i + 1);
            }
        }

        return result;
    }

    // ============ 方法三：HashSet 辅助（O(n) 空间，用于对照理解） ============

    /**
     * des: 先把数组整体装进集合，再逐个检查 1..n 谁没露面
     * <p>
     * 时间 O(n)，空间 O(n)。它不满足 O(1) 空间的进阶要求，但语义最直白，
     * 且不会破坏输入数组，适合作为验证另外两种解法正确性的「标准答案」。
     */
    public List<Integer> findBySet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= nums.length; i++) {
            if (!set.contains(i)) {
                result.add(i);
            }
        }

        return result;
    }

    /** des: 交换数组中的两个元素 */
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，缺 5 与 6 ----------
        check(new int[]{4, 3, 2, 7, 8, 2, 3, 1}, Arrays.asList(5, 6));

        // ---------- 用例 2：官方示例，n 最小规模下的缺失 ----------
        check(new int[]{1, 1}, Arrays.asList(2));

        // ---------- 用例 3：完全无缺失，应返回空列表 ----------
        check(new int[]{1, 2, 3}, Collections.emptyList());

        // ---------- 用例 4：全部取到上界，唯独缺 1 ----------
        check(new int[]{2, 2}, Arrays.asList(1));

        // ---------- 用例 5：单元素数组，无缺失 ----------
        check(new int[]{1}, Collections.emptyList());

        // ---------- 用例 6：整个数组都是同一个数，缺失最多 ----------
        check(new int[]{3, 3, 3}, Arrays.asList(1, 2));

        // ---------- 用例 7：严格逆序，每个数字都恰好出现一次 ----------
        check(new int[]{5, 4, 3, 2, 1}, Collections.emptyList());

        // ---------- 用例 8：重复与缺失各占一半，专门触发「重复标记」的坑 ----------
        check(new int[]{2, 2, 3, 3}, Arrays.asList(1, 4));

        // ---------- 用例 9：只缺一个数，且该数位于数组末尾（最易漏判的位置） ----------
        check(new int[]{1, 2, 3, 4, 5, 6, 7, 9, 9}, Arrays.asList(8));

        // ---------- 用例 10：n = 100 的规模，仅缺 50 与 100，验证线性解法在大数组下依然正确 ----------
        int n = 100;
        int[] big = new int[n];
        for (int i = 0; i < n; i++) {
            big[i] = i + 1;
        }
        big[49] = 1;   // 顶掉 50
        big[99] = 1;   // 顶掉 100
        check(big, Arrays.asList(50, 100));
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] nums, List<Integer> expected) {
        FindDisappearedNumbers solution = new FindDisappearedNumbers();

        // 两种原地解法都会改写入参，故各自传入独立副本，避免相互污染
        List<Integer> r1 = solution.findDisappearedNumbers(Arrays.copyOf(nums, nums.length));
        List<Integer> r2 = solution.findByCycleSort(Arrays.copyOf(nums, nums.length));
        List<Integer> r3 = solution.findBySet(nums);

        boolean pass = r1.equals(expected) && r2.equals(expected) && r3.equals(expected);

        System.out.printf("nums = %s%n", brief(nums));
        System.out.printf("  期望值     : %s%n", expected);
        System.out.printf("  负号标记   : %s%s%n", r1, r1.equals(expected) ? "  ✅" : "  ❌");
        System.out.printf("  交换归位   : %s%s%n", r2, r2.equals(expected) ? "  ✅" : "  ❌");
        System.out.printf("  HashSet    : %s%s%n", r3, r3.equals(expected) ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 数组过长时只打印前 10 个元素，避免测试输出刷屏 */
    private static String brief(int[] nums) {
        if (nums.length <= 20) {
            return Arrays.toString(nums);
        }
        return Arrays.toString(Arrays.copyOf(nums, 10)) + " ... 共 " + nums.length + " 个元素";
    }
}
