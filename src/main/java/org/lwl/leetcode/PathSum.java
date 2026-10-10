package org.lwl.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * LeetCode 437 - 路径总和 III（提交时将类名改为 Solution）
 * <p>
 * 题目：给定一棵二叉树的根节点 root 和一个整数 targetSum，统计「节点值之和恰好等于 targetSum 的路径」的条数。
 * 题目对"路径"有两条约定，读题时务必逐字吃透：
 * 1. <b>起点不必是根节点，终点也不必是叶子节点</b>——树上任意一段连续向下延伸的节点串都算一条路径；
 * 2. <b>方向必须自上而下</b>——只能从父节点走向子节点，不能折返，也不能跨到兄弟节点去。
 * <p>
 * 关键理解一：在这道题里，"路径"到底是什么？
 * 它不是"根到叶的一条完整链"，而是"任选一个节点当起点，沿 left/right 指针一路往下，
 * 走任意步（可以是 0 步，即起点自己就是终点），随时可以停"所形成的一条竖直链。
 * 换句话说，路径与"根"、与"叶子"都没有必然关系，它只是树中一条自上而下的连续片段。
 * 这正是它与 LeetCode 112/113（路径必须从根到叶）最本质的区别。
 * <p>
 * 关键理解二：为什么"枚举每个节点当起点"这种朴素做法不会数错？
 * 因为每条路径的起点是唯一的（就是这条链上位置最高的那个节点）。
 * 只要把每个节点都当成起点试一遍，从它出发往下走，沿途累加，遇到 targetSum 就计数，
 * 那么任意一条路径都会在"枚举到它自己的起点"那一次被数到，且只被数到一次——天然不重不漏。
 * 代价是慢：n 个起点，每个起点最多往下走 n 步，总复杂度 O(n²)，树退化成链时最惨。
 * <p>
 * 关键理解三：前缀和如何把 O(n²) 压成 O(n)？
 * 核心洞察：<b>树上任意一段"竖直连续片段"的和，等于两条"从根出发的路径和"之差。</b>
 * 设 currSum 为根走到当前节点的和，prefix 为根走到当前节点某个祖先的和，
 * 那么"该祖先的下一个节点 → 当前节点"这一段的和就等于 currSum - prefix。
 * 要让这段和等于 targetSum，即 currSum - prefix = targetSum，移项得 prefix = currSum - targetSum。
 * 于是做法水到渠成：一边 DFS 一边把"已经走过的每个节点处的前缀和"记进一张哈希表（前缀和 → 出现次数）。
 * 每走到一个节点，就查表里 currSum - targetSum 出现过几次——出现几次，
 * 就说明"以当前节点为终点"的合法路径有几条。
 * 每个节点只做一次 O(1) 查表与 O(1) 记账，一次遍历搞定，总复杂度 O(n)。
 * 这与 LeetCode 560「和为 K 的子数组」是同一套思路在树上的翻版。
 * <p>
 * 关键理解四：账本为什么必须在回溯时"销账"？
 * 哈希表记录的是「当前这条从根到此处路径上」出现过的前缀和。
 * DFS 从某个分支退出、转去走兄弟分支时，原先那条路径上的节点已经不在当前路径上了，
 * 它们的前缀和必须从表里减掉，否则兄弟分支会"借用"到不属于自己路径的前缀和，
 * 数出根本不存在的路径（节点值含负数或 0 时，这种假阳性尤其隐蔽）。
 * 进入分支时记账、离开分支时销账，这正是回溯（backtracking）的标准姿势。
 * <p>
 * 关键理解五：为什么哈希表初始要 put(0, 1)？
 * 这个 key=0 代表"空前缀"——对应"路径起点恰好就是根节点"的情形。
 * 当 currSum 本身就等于 targetSum 时，查询键 currSum - targetSum = 0，
 * 若表里没有这个 0，所有"从根开始"的路径都会被漏掉。它是数量恰好为 1 的一条虚拟起点。
 * <p>
 * 关键理解六：为什么要用 long？
 * 节点值可达 ±10^9，节点数最多 1000，一条从根到叶的路径前缀和最大可逼近 10^12，
 * 远超 int 约 21 亿的上限。若用 int 累加会静默溢出成负数，查询键随之错乱，答案悄无声息地变错。
 * 注意：targetSum 本身是 int（题目如此），但 currSum 与查表的 key 必须是 long。
 * <p>
 * 时空复杂度：
 * - 方法一（双重递归）：时间 O(n²)（树退化成链时最坏；平衡树上约 O(n log n)），空间 O(h) 递归栈；
 * - 方法二（前缀和 + 回溯）：时间 O(n)，每个节点访问一次、查表记账均为 O(1)；
 *   空间 O(h)，哈希表最多同时保存一条根到叶路径上的前缀和（即树高 h 个），另有递归栈 O(h)。
 * <p>
 * 举一反三：把「前缀和 + 哈希表」看成一招通用剑法，凡"求连续片段之和等于某值"的题都能套：
 * - LeetCode 560（和为 K 的子数组）：本题的一维版本，数组上的前缀和，代码骨架几乎一模一样；
 * - LeetCode 930（和相同的二元子数组）：目标和的判定方式不同，思路不变；
 * - LeetCode 974（和可被 K 整除的子数组）：前缀和不存原值，改存"对 K 取模的余数"；
 * - LeetCode 525（连续数组）：把 0 视作 -1，问题立刻变成"和为 0 的最长子数组"；
 * - LeetCode 124（二叉树中的最大路径和）与本题同属"树上路径"，但那条路径允许拐弯（左→父→右），
 *   所以套不了前缀和，得改成"自底向上返回单边最大值"的树形 DP，对照着看更能分清两者的分野；
 * - 判定口诀：路径带方向、不可拐弯、求"连续片段之和" → 前缀和 + 哈希；
 *   路径可拐弯、求"最大值/最值" → 树形 DP 自底向上。
 */
public class PathSum {

    /** 二叉树节点定义（本地调试用，LeetCode 提交时使用平台自带的 TreeNode） */
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // ============ 方法一：双重递归（枚举每个节点作为起点，暴力但直观） ============

    /**
     * des: 外层递归枚举"起点节点"，内层递归从该起点往下累加，凑够 targetSum 就计数
     * <p>
     * 为什么不会重复计数？每条路径的起点唯一，外层遍历保证每个节点只当一次起点。
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        // 以 root 为起点的条数 + 以左子树中某节点为起点的条数 + 以右子树中某节点为起点的条数
        return countFrom(root, targetSum)
                + pathSum(root.left, targetSum)
                + pathSum(root.right, targetSum);
    }

    /**
     * des: 统计"以 node 为起点、向下延伸"的所有路径中，节点和恰好为 rest 的条数
     * <p>
     * rest 用 long 承接：沿途累加可能突破 int 上限。
     */
    private int countFrom(TreeNode node, long rest) {
        if (node == null) {
            return 0;
        }
        int count = 0;
        if (node.val == rest) {
            count++;    // 路径在当前位置停下刚好凑够，但不要 return
        }
        // 继续往下走：当前节点已贡献 node.val，剩下的差额交给子树去找。
        // 之所以不能提前 return，是因为后面可能还跟着和为 0 的片段（如 +5、-5）构成更长的合法路径。
        count += countFrom(node.left, rest - node.val);
        count += countFrom(node.right, rest - node.val);
        return count;
    }

    // ============ 方法二：前缀和 + 回溯（一次遍历，O(n)，推荐） ============

    /**
     * des: 一次 DFS，用哈希表记录"当前路径上各前缀和出现的次数"，边走边查
     */
    public int pathSumByPrefix(TreeNode root, int targetSum) {
        // key：从根到某个节点的前缀和；value：该前缀和在这条路径上出现的次数
        Map<Long, Integer> prefixCount = new HashMap<>();
        // 空前缀，对应"路径起点就是根"的情形，必须存在且恰好为 1
        prefixCount.put(0L, 1);
        return dfs(root, 0L, targetSum, prefixCount);
    }

    private int dfs(TreeNode node, long currSum, int targetSum, Map<Long, Integer> prefixCount) {
        if (node == null) {
            return 0;
        }
        currSum += node.val;

        // 以当前节点为终点：起点就是"前缀和为 currSum - targetSum"那个位置的下一个节点
        long need = currSum - targetSum;
        int count = prefixCount.getOrDefault(need, 0);

        // 记账：把当前前缀和登记进账本，供更深层的节点查询
        prefixCount.put(currSum, prefixCount.getOrDefault(currSum, 0) + 1);

        count += dfs(node.left, currSum, targetSum, prefixCount);
        count += dfs(node.right, currSum, targetSum, prefixCount);

        // 销账：即将离开这个分支，本节点的前缀和不能留给兄弟分支使用
        prefixCount.put(currSum, prefixCount.get(currSum) - 1);

        return count;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，答案为 3 ----------
        // 三条路径分别是 5->3、5->2->1、-3->11
        check(buildTree(10, 5, -3, 3, 2, null, 11, 3, -2, null, 1), 8, 3);

        // ---------- 用例 2：单节点 ----------
        check(buildTree(5), 5, 1);      // 起点即终点，路径就是根本身
        check(buildTree(5), 3, 0);      // 无解

        // ---------- 用例 3：右斜链，验证"任意片段都能当路径" ----------
        // 1->2->3->4->5 中，和为 3 的只有 "1->2" 与独点 "3"，共 2 条
        check(buildTree(1, null, 2, null, 3, null, 4, null, 5), 3, 2);

        // ---------- 用例 4：含负数，起点落在中间 ----------
        // 1->-2 的和恰好是 -1，共 1 条
        check(buildTree(1, -2, -3), -1, 1);

        // ---------- 用例 5：含 0，验证同一个和能被多次统计 ----------
        // root=0 带两个值为 1 的孩子，和为 1 的路径有 4 条
        check(buildTree(0, 1, 1), 1, 4);

        // ---------- 用例 6：空树 ----------
        check(null, 0, 0);

        // ---------- 用例 7：全零树，验证"每个片段都是合法路径" ----------
        // 5 个节点全部为 0，targetSum = 0，此时任意一条向下链都成立，共 11 条
        check(buildTree(0, 0, 0, null, null, 0, 0), 0, 11);

        // ---------- 用例 8：验证 long 的必要性（内部若用 int 累加会溢出算错） ----------
        // 右斜链 1e9 -> 1e9 -> 1e9，targetSum = 2e9；
        // 前缀和峰值 3e9 已超 int 上限，答案应为 2（前两节点之和、后两节点之和）
        check(buildTree(1000000000, null, 1000000000, null, 1000000000), 2000000000, 2);

        // ---------- 附加演示：逐步追踪官方示例上前缀和 DFS 的决策过程 ----------
        tracePrefixSum(buildTree(10, 5, -3, 3, 2, null, 11, 3, -2, null, 1), 8);
    }

    /** des: 用两种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(TreeNode root, int targetSum, int expected) {
        PathSum solution = new PathSum();
        // 两种解法都不修改树，但仍各传一份拷贝，保证互不干扰
        int r1 = solution.pathSum(copy(root), targetSum);
        int r2 = solution.pathSumByPrefix(copy(root), targetSum);
        boolean pass = r1 == expected && r2 == expected;

        System.out.printf("root = %s  targetSum = %d%n", levelOrder(root), targetSum);
        System.out.printf("  期望值          : %d%n", expected);
        System.out.printf("  双重递归 O(n²)  : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  前缀和 O(n)     : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 逐步打印前缀和 DFS 在官方示例上的决策过程，直观展示"查表命中即找到路径" */
    private static void tracePrefixSum(TreeNode root, int targetSum) {
        System.out.println("========== 附：前缀和 DFS 在官方示例上的逐步追踪 ==========");
        System.out.println("targetSum = " + targetSum);
        System.out.println("账本含义：key = 从根到某节点的前缀和，value = 当前路径上它出现的次数");
        System.out.println();

        Map<Long, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0L, 1);
        System.out.println("初始账本 " + prefixCount + "   （0 = 空前缀，路径从根开始时需要它）");
        System.out.println();

        int total = traceDfs(root, 0L, targetSum, prefixCount, "");

        System.out.println();
        System.out.println("合计命中 " + total + " 条");
        System.out.println();
    }

    /** des: tracePrefixSum 的递归实现，每步打印 currSum、查询键、命中次数以及账本变化 */
    private static int traceDfs(TreeNode node, long currSum, int targetSum,
                                Map<Long, Integer> prefixCount, String indent) {
        if (node == null) {
            return 0;
        }
        currSum += node.val;
        long need = currSum - targetSum;
        int hit = prefixCount.getOrDefault(need, 0);

        System.out.printf("%s节点 %d：currSum = %d，查询前缀和 %d（= %d - %d），命中 %d 次%s%n",
                indent, node.val, currSum, need, currSum, targetSum, hit,
                hit > 0 ? "  ← 找到 " + hit + " 条以本节点结尾的路径" : "");

        prefixCount.put(currSum, prefixCount.getOrDefault(currSum, 0) + 1);
        System.out.printf("%s  记账后 %s%n", indent, prefixCount);

        int count = hit;
        count += traceDfs(node.left, currSum, targetSum, prefixCount, indent + "  ");
        count += traceDfs(node.right, currSum, targetSum, prefixCount, indent + "  ");

        prefixCount.put(currSum, prefixCount.get(currSum) - 1);
        System.out.printf("%s  销账后 %s%n", indent, prefixCount);

        return count;
    }

    // ============ 测试辅助方法 ============

    /** des: 按 LeetCode 的层序数组（null 表示空节点）构建二叉树 */
    private static TreeNode buildTree(Integer... vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (i < vals.length && !queue.isEmpty()) {
            TreeNode node = queue.poll();
            // 左孩子
            if (i < vals.length && vals[i] != null) {
                node.left = new TreeNode(vals[i]);
                queue.offer(node.left);
            }
            i++;
            // 右孩子
            if (i < vals.length && vals[i] != null) {
                node.right = new TreeNode(vals[i]);
                queue.offer(node.right);
            }
            i++;
        }
        return root;
    }

    /** des: 深拷贝一棵树，保证多组用例之间互不干扰 */
    private static TreeNode copy(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode node = new TreeNode(root.val);
        node.left = copy(root.left);
        node.right = copy(root.right);
        return node;
    }

    /** des: 层序序列化，格式与 LeetCode 一致（内部空节点输出 null，末尾多余的 null 省略） */
    private static String levelOrder(TreeNode root) {
        if (root == null) {
            return "[]";
        }
        List<String> out = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                out.add("null");
                continue;
            }
            out.add(String.valueOf(node.val));
            queue.offer(node.left);
            queue.offer(node.right);
        }
        // 去掉末尾多余的 null，还原成 LeetCode 的紧凑写法
        int end = out.size();
        while (end > 0 && "null".equals(out.get(end - 1))) {
            end--;
        }
        return out.subList(0, end).toString().replace(" ", "");
    }
}
