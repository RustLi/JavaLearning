package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 46 - 全排列（提交时将类名改为 Solution）
 * <p>
 * 题目：给定一个不含重复数字的数组 nums，返回其所有可能的全排列。你可以按任意顺序返回答案。
 * <p>
 * 关键理解一：全排列是一棵「选择树」。
 * 把「生成一个排列」的过程摊开来瞧：第 1 个位置有 n 种选法，第 2 个位置在剩下的数里挑还有 n-1 种，
 * ……直到第 n 个位置只剩 1 种。把每个位置的选择画成分叉，就得到一棵 n 层的树：
 * <pre>
 *                          [ ]
 *          ┌───────────────┼───────────────┐
 *          1               2               3          第 1 层：3 种选择
 *      ┌───┴───┐       ┌───┴───┐       ┌───┴───┐
 *      2       3       1       3       1       2      第 2 层：各 2 种选择
 *      │       │       │       │       │       │
 *      3       2       3       1       2       1      第 3 层：各 1 种选择
 * </pre>
 * 叶子节点恰好 n! 个，每个叶子对应一个完整排列。于是问题转化为：<b>遍历这棵树，把所有叶子收集起来</b>。
 * <p>
 * 关键理解二：回溯 = 深度优先遍历 + 撤销选择。
 * 光有 DFS 还不够。因为 path（当前路径）和 used（已用标记）是<b>所有分支共享同一份</b>的，
 * 走进某个分支前改动了它们，退回时必须<b>恢复原样</b>，否则下一个分支将看到被污染的状态。
 * 这就是「回溯」二字的全部含义，三个动作构成它的心跳：
 * <pre>
 *      做选择  →  递归  →  撤销选择
 * </pre>
 * 打个比方：走迷宫时在岔路口做记号，此路不通退回来时必须把记号擦掉，
 * 否则下次走另一条岔路会被自己上一次留下的记号误导。
 * <p>
 * 不撤销会怎样？举个具体例子。nums = [1,2,3]，当前 path = [1]、used = [T,F,F]：
 * 选中 2 后 path = [1,2]、used = [T,T,F]，递归深入并返回。
 * 此时若不撤销，path 仍是 [1,2]、used 仍是 [T,T,F]；
 * 下一轮 i=2 发现 used[2] 还是 F，于是又选中 3，path 变成 [1,2,3] 并被记入答案。
 * 可正确答案本应是 [1,3,2]——2 根本没有被"放回去"，3 只能排在它后面。整棵树就此走歪。
 * <p>
 * 关键理解三：那个几乎人人踩过、且极难排查的坑——必须拷贝路径。
 * <pre>
 *      result.add(path);                    // ❌ 灾难性错误
 *      result.add(new ArrayList<>(path));   // ✅ 必须新建一份
 * </pre>
 * path 从头到尾是<b>同一个对象</b>，在递归中被反复增删。若直接存引用，
 * 等算法跑完所有分支，path 早已被回溯清空，result 里那 n! 个元素会<b>全部指向同一个空列表</b>。
 * 这个 bug 的阴险之处在于：程序不会报错，只是答案全错且全都一样——
 * 但凡在叶子处看到"所有结果长得一模一样"，第一个该怀疑的就是这里。
 * <p>
 * 关键理解四：题目那句「不含重复数字」不是废话，而是免除剪枝的许可证。
 * 它保证了每个叶子都是互不相同的排列，枚举即所得。
 * 一旦数组含重复元素（LeetCode 47），同一个排列会被不同分支重复生成，则必须先排序再剪枝去重。
 * <p>
 * 时间复杂度 O(n × n!)：共 n! 个叶子，每个叶子要花 O(n) 把路径拷贝进答案，
 * 这一项拷贝开销才是主导——很多人误以为是 O(n!)，实际要再乘一个 n。
 * 空间复杂度 O(n)：递归栈深度、path 长度、used 数组各为 n（返回的结果集按 LeetCode 惯例不计入）。
 * <p>
 * 举一反三：回溯是一整套「模板化」的算法，骨架高度统一，记住它就能一路推平一大片题——
 * <pre>
 *      void backtrack(路径, 选择列表) {
 *          if (满足结束条件) { 记录答案; return; }
 *          for (选择 : 选择列表) {
 *              做选择;
 *              backtrack(新路径, 新选择列表);
 *              撤销选择;
 *          }
 *      }
 * </pre>
 * 各类变体只是在这副骨架上改三处：<b>结束条件、选择列表、以及剪枝规则</b>。
 * - LeetCode 47（全排列 II，含重复元素）：排序后用
 *   {@code if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;} 剪枝。
 *   它的含义是给重复数字约定一个"出场顺序"——相同值的数必须从左往右依次被选中，
 *   于是同一个排列只可能由唯一一条分支产生，重复项自然被掐掉；
 * - LeetCode 78（子集）：<b>每个节点都是答案</b>，而不只是叶子，在进入函数时就记录路径；
 * - LeetCode 77（组合）：只需要选 k 个，凑够 k 就收手；还能进一步剪枝
 *   {@code i <= n - (k - path.size()) + 1}——剩余数字不够凑满 k 个时直接不再尝试；
 * - LeetCode 39 / 40（组合总和）：选择列表里加了 sum 约束，剪枝条件随之变成「超出目标就回头」；
 * - LeetCode 131（分割回文串）：选择列表换成了「下一个切点在哪」，其余骨架纹丝不动；
 * - LeetCode 51（N 皇后）：把「同一列、两条对角线」的约束预先建成三个布尔数组，剪枝就成了 O(1) 的查表。
 * <p>
 * 回溯与 DFS 的关系也值得掰扯清楚：DFS 是一种<b>遍历方式</b>，回溯是一种<b>解题框架</b>。
 * 回溯在 DFS 之上多加了两样东西——一份随路径变化的状态，以及用完即还的撤销动作。
 * 少了撤销，它退化成普通 DFS；少了状态，它无从判断何时该收手。
 */
public class Permutations {

    // ============ 方法一：used 数组标记（推荐，思维模型最清晰） ============

    /**
     * des: 用 used 数组充当「选择列表」，逐个试遍还没用过的数字，递归后原样撤销
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        backtrack(nums, used, path, result);
        return result;
    }

    /**
     * des: 回溯主过程——path 是已走过的路，used 是还能选哪些数
     */
    private void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // 结束条件：路径长度凑够了，说明一个完整排列已经成型
        if (path.size() == nums.length) {
            // 必须新建一份再存！path 是共享的同一个对象，存引用会被后续回溯清空
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;               // 这个数已经在当前路径上用过了，跳过
            }

            // ---- 做选择 ----
            used[i] = true;
            path.add(nums[i]);

            backtrack(nums, used, path, result);

            // ---- 撤销选择：把状态恢复成进入本分支前的样子，供下一个分支使用 ----
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    // ============ 方法二：原地交换（省掉 used 数组，代码更短） ============

    /**
     * des: 不再另开 used 数组，而是把数组自身切成「已定前缀」与「候选区」两段，靠位置本身表示"谁用过了"
     * <p>
     * 下标 [0, first) 是已确定的前缀，[first, n) 是候选区。
     * 每轮从候选区挑一个元素换到 first 位置，递归确定下一位，返回后再换回去。
     * 相比方法一，它省下了一个布尔数组，代价是会打乱数组顺序（这里先拷贝一份，避免污染调用方数据）。
     * <p>
     * 注意：交换法在「含重复元素」（LeetCode 47）的场景下不好做剪枝，
     * 那种题目仍应回到方法一的 used 数组写法。
     */
    public List<List<Integer>> permuteBySwap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int[] arr = Arrays.copyOf(nums, nums.length);

        swapBacktrack(arr, 0, result);
        return result;
    }

    private void swapBacktrack(int[] arr, int first, List<List<Integer>> result) {
        if (first == arr.length) {
            // 前缀铺满整个数组，一个排列完成
            result.add(toList(arr));
            return;
        }

        for (int i = first; i < arr.length; i++) {
            // 把候选区第 i 个元素换到 first 位，它就被"用掉"了
            swap(arr, first, i);

            swapBacktrack(arr, first + 1, result);

            // 换回来，让 first 位重新回到候选状态，供下一轮挑选
            swap(arr, first, i);
        }
    }

    // ============ 方法三：字典序「下一个排列」（迭代而非递归，思路完全不同） ============

    /**
     * des: 不递归枚举，而是从字典序最小的排列出发，反复求"下一个更大的排列"，直到没有下一个为止
     * <p>
     * 这需要用到 LeetCode 31（下一个排列）的经典算法，四步走：
     * 1. 从右往左找第一个升序对 nums[i] &lt; nums[i+1]——说明 i 位还有"变大"的余地；
     * 2. 从右往左找第一个大于 nums[i] 的 nums[j]，它就是该顶上 i 位的最小值；
     * 3. 交换 nums[i] 与 nums[j]；
     * 4. 反转 i+1 到末尾。因为 i 之后原本是降序（已是最大排列），反转后变为升序（最小排列），
     * 如此才能得到"恰好比原排列大的下一个"。
     * <p>
     * 与前两个方法相比：它天然按字典序输出，也不需要递归栈；
     * 但每个排列都要花 O(n) 求下一个，总复杂度同为 O(n × n!)，常数更大。
     * 它的真正价值在于揭示了「枚举排列」并非只能靠回溯——迭代推进同样可以，且顺序天然有序。
     */
    public List<List<Integer>> permuteByNextPermutation(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int[] arr = Arrays.copyOf(nums, nums.length);
        Arrays.sort(arr);          // 必须从字典序最小的那个排列启程

        do {
            result.add(toList(arr));
        } while (nextPermutation(arr));

        return result;
    }

    /** des: 把 arr 原地改成字典序中的下一个排列；已是最后一个（完全降序）则返回 false */
    private boolean nextPermutation(int[] arr) {
        int n = arr.length;

        // 步骤 1：从右往左找第一个升序对。找不到说明整体降序，已是最后一个排列
        int i = n - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }
        if (i < 0) {
            return false;
        }

        // 步骤 2：在 i 右侧（必为降序）从右往左找第一个大于 arr[i] 的数
        int j = n - 1;
        while (arr[j] <= arr[i]) {
            j--;
        }

        // 步骤 3：交换，让 i 位变大一点点——恰好是"最小幅度"的那次变大
        swap(arr, i, j);

        // 步骤 4：把 i 之后反转成升序，使后缀取到最小值
        reverse(arr, i + 1, n - 1);
        return true;
    }

    // ============ 通用小工具 ============

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void reverse(int[] arr, int left, int right) {
        while (left < right) {
            swap(arr, left++, right--);
        }
    }

    private List<Integer> toList(int[] arr) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int v : arr) {
            list.add(v);
        }
        return list;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，3 个数共 6 种排列 ----------
        check(new int[]{1, 2, 3});

        // ---------- 用例 2：含 0，验证 0 不被当作特殊值处理 ----------
        check(new int[]{0, 1});

        // ---------- 用例 3：只有一个元素，唯一的排列就是它自己 ----------
        check(new int[]{1});

        // ---------- 用例 4：空数组，数学上空排列算 1 种（0! = 1） ----------
        check(new int[]{});

        // ---------- 用例 5：含负数，验证正负号不影响逻辑 ----------
        check(new int[]{-1, -2, 3});

        // ---------- 用例 6：4 个数共 24 种，验证分支组合的完整性 ----------
        check(new int[]{5, 4, 6, 7});

        // ---------- 用例 7：2 个数，最小规模的多种排列 ----------
        check(new int[]{9, 8});

        // ---------- 用例 8：7 个数共 5040 种，验证大规模下的正确性与性能 ----------
        check(new int[]{1, 2, 3, 4, 5, 6, 7});
    }

    /** des: 三种解法跑同一组用例，用数学性质验证结果（顺序无关），而非硬编码期望值 */
    private static void check(int[] nums) {
        Permutations solution = new Permutations();

        List<List<Integer>> r1 = solution.permute(nums);
        List<List<Integer>> r2 = solution.permuteBySwap(nums);
        List<List<Integer>> r3 = solution.permuteByNextPermutation(nums);

        boolean p1 = isCompletePermutationSet(r1, nums);
        boolean p2 = isCompletePermutationSet(r2, nums);
        boolean p3 = isCompletePermutationSet(r3, nums);

        int n = nums.length;
        System.out.printf("nums = %s%n", Arrays.toString(nums));
        System.out.printf("  期望数量   : %d! = %d%n", n, factorial(n));
        System.out.printf("  used 标记  : %d 个%s%n", r1.size(), p1 ? "  ✅" : "  ❌");
        System.out.printf("  原地交换   : %d 个%s%n", r2.size(), p2 ? "  ✅" : "  ❌");
        System.out.printf("  下一个排列 : %d 个%s%n", r3.size(), p3 ? "  ✅" : "  ❌");
        System.out.printf("  结果预览   : %s%n", preview(r1));
        System.out.println(p1 && p2 && p3 ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /**
     * des: 用三条数学性质独立判定结果是否合法，无需手写期望清单
     * <p>
     * 1. 数量恰好为 n!；
     * 2. 结果内部无重复；
     * 3. 每一项都是 nums 的合法排列（元素相同、个数相同）。
     * 三条同时成立，即可推出结果必然就是全部排列——因为 n! 个互不相同的合法排列，
     * 数量已经用尽，不可能再有遗漏。
     */
    private static boolean isCompletePermutationSet(List<List<Integer>> actual, int[] nums) {
        if (actual.size() != factorial(nums.length)) {
            return false;
        }

        // 条件 2：无重复
        if (new HashSet<>(actual).size() != actual.size()) {
            return false;
        }

        // 条件 3：每一项都是 nums 的合法排列
        List<Integer> sortedNums = new ArrayList<>();
        for (int v : nums) {
            sortedNums.add(v);
        }
        Collections.sort(sortedNums);

        for (List<Integer> p : actual) {
            if (p.size() != nums.length) {
                return false;
            }
            List<Integer> copy = new ArrayList<>(p);
            Collections.sort(copy);
            if (!copy.equals(sortedNums)) {
                return false;
            }
        }

        return true;
    }

    private static long factorial(int n) {
        long r = 1;
        for (int i = 2; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    /** des: 结果较少时全部列出，较多时只列前 6 个，避免刷屏 */
    private static String preview(List<List<Integer>> result) {
        int limit = Math.min(result.size(), 6);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            sb.append(result.get(i));
            if (i < limit - 1) {
                sb.append(" ");
            }
        }
        if (result.size() > limit) {
            sb.append(" ... 共 ").append(result.size()).append(" 个");
        }
        return sb.toString();
    }
}
