package org.lwl.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LeetCode 617 - 合并二叉树（提交时将类名改为 Solution）
 * <p>
 * 题目：给定两棵二叉树 root1、root2。想象把其中一棵覆盖到另一棵之上，两棵树上的一些节点会重叠，
 * 另一些不会。请把它们合并成一棵新二叉树。规则：
 * - 重叠（两个节点都不为空）：新节点值为两者之和；
 * - 不重叠（其中一方为空）：不为 null 的节点直接作为新树的节点（连同它的整棵子树）。
 * <p>
 * 关键理解：所谓"覆盖"，就是两棵树从根开始同步遍历，把每个位置上的节点一一对应起来。
 * 因此这是一次"同步遍历"——遍历的路径由两棵树共同决定，而不是各自遍历一次。
 * 一旦某个位置上有一方是 null，说明这棵树的这个分支已经到底了，另一方的整棵子树就是结果，
 * 直接挂上去即可，无需继续向下递归。
 * <p>
 * 核心思路一：递归 DFS（推荐）
 * 把大问题拆成同样的子问题——"合并以 t1、t2 为根的两棵树"：
 * - t1 == null，结果为 t2（整棵子树直接拿来）；
 * - t2 == null，结果为 t1；
 * - 都不为 null，t1.val += t2.val，再递归合并左右子树。
 * <p>
 * 核心思路二：迭代 BFS
 * 用队列成对地保存"待合并的两个节点"，逻辑与递归完全一致，只是把递归栈换成了显式队列，
 * 可以规避极深树导致的栈溢出。
 * <p>
 * 时间复杂度 O(min(m, n))：一旦某棵树在某位置为 null 就直接返回，两棵树不会同时被完整遍历；
 * 空间复杂度：递归 O(h)（h 为树高，最坏退化成链表为 O(n)），BFS O(w)（w 为最宽层节点数）。
 * <p>
 * 注意：原地合并版本会修改入参 root1 的结构，如果调用方还要复用原树，请用新建节点版本。
 */
public class MergeTrees {

    /** 二叉树节点定义（本地调试用，LeetCode 提交时使用平台自带的 TreeNode） */
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // ============ 方法一：递归 DFS，原地合并（推荐，代码最简） ============

    /**
     * des: 直接在 root1 上合并，返回 root1；root1 为空时返回 root2
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        // 一方为空，另一方的整棵子树就是答案，直接返回，无需继续递归
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        // 两节点重叠，值相加
        root1.val += root2.val;
        // 同步递归合并左右子树
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    // ============ 方法二：递归 + 新建节点（不修改原树） ============

    /**
     * des: 不修改入参，每个位置都新建节点，适合原树仍需复用的场景
     */
    public TreeNode mergeTreesByNewNode(TreeNode root1, TreeNode root2) {
        // 两个都为空才结束；只有一方为空时要保留另一方的值
        if (root1 == null && root2 == null) {
            return null;
        }
        int val = (root1 == null ? 0 : root1.val) + (root2 == null ? 0 : root2.val);
        TreeNode node = new TreeNode(val);
        // 取子节点时仍要注意空指针：root1 为空就不该访问 root1.left
        node.left = mergeTreesByNewNode(root1 == null ? null : root1.left,
                root2 == null ? null : root2.left);
        node.right = mergeTreesByNewNode(root1 == null ? null : root1.right,
                root2 == null ? null : root2.right);
        return node;
    }

    // ============ 方法三：迭代 BFS（用队列替代递归栈） ============

    /**
     * des: 成对入队待合并的节点，避免深树递归栈溢出
     */
    public TreeNode mergeTreesByBfs(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        // 队列中成对存放：奇偶相邻的两个元素是同一位置上的 (t1, t2)
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root1);
        queue.offer(root2);

        while (!queue.isEmpty()) {
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            t1.val += t2.val; // 能入队的必然都不为空

            // 左孩子：t1 缺则直接挂 t2 的；否则两方都有，需要继续向下合并
            if (t1.left == null) {
                t1.left = t2.left;
            } else if (t2.left != null) {
                queue.offer(t1.left);
                queue.offer(t2.left);
            }

            // 右孩子同理
            if (t1.right == null) {
                t1.right = t2.right;
            } else if (t2.right != null) {
                queue.offer(t1.right);
                queue.offer(t2.right);
            }
        }
        return root1;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：LeetCode 官方示例 ----------
        // root1 = [1,3,2,5]   root2 = [2,1,3,null,4,null,7]  ->  [3,4,5,5,4,null,7]
        compare(buildTree(1, 3, 2, 5),
                buildTree(2, 1, 3, null, 4, null, 7),
                buildTree(3, 4, 5, 5, 4, null, 7));

        // ---------- 用例 2：一方为单节点 ----------
        // root1 = [1]   root2 = [1,2]  ->  [2,2]
        compare(buildTree(1), buildTree(1, 2), buildTree(2, 2));

        // ---------- 用例 3：边界——其中一棵树为空 ----------
        compare(null, buildTree(1), buildTree(1));
        compare(buildTree(1), null, buildTree(1));

        // ---------- 用例 4：两棵都不规则，验证分支错位 ----------
        // root1 = [1,null,3]   root2 = [2,1,null]  ->  [3,1,3]
        compare(buildTree(1, null, 3), buildTree(2, 1, null), buildTree(3, 1, 3));

        // ---------- 验证：新建节点版本不修改原树 ----------
        System.out.println();
        TreeNode a = buildTree(1, 3, 2, 5);
        TreeNode b = buildTree(2, 1, 3, null, 4, null, 7);
        String beforeA = levelOrder(a);
        String beforeB = levelOrder(b);
        TreeNode merged = new MergeTrees().mergeTreesByNewNode(a, b);
        System.out.println("新建节点版结果   : " + levelOrder(merged));
        System.out.println("原 root1 是否不变: " + beforeA.equals(levelOrder(a)) + "  " + levelOrder(a));
        System.out.println("原 root2 是否不变: " + beforeB.equals(levelOrder(b)) + "  " + levelOrder(b));
    }

    /** des: 分别用三种解法跑同一组用例并比对期望结果 */
    private static void compare(TreeNode root1, TreeNode root2, TreeNode expected) {
        // 三种解法都会修改入参，因此每次都用全新的树
        MergeTrees solution = new MergeTrees();
        String r1 = levelOrder(solution.mergeTrees(copy(root1), copy(root2)));
        String r2 = levelOrder(solution.mergeTreesByNewNode(copy(root1), copy(root2)));
        String r3 = levelOrder(solution.mergeTreesByBfs(copy(root1), copy(root2)));
        String exp = levelOrder(expected);

        System.out.println("root1 = " + levelOrder(root1) + "  root2 = " + levelOrder(root2));
        System.out.println("  期望值       : " + exp);
        System.out.println("  递归原地合并 : " + r1 + (r1.equals(exp) ? "  ✅" : "  ❌"));
        System.out.println("  递归新建节点 : " + r2 + (r2.equals(exp) ? "  ✅" : "  ❌"));
        System.out.println("  迭代 BFS     : " + r3 + (r3.equals(exp) ? "  ✅" : "  ❌"));
        System.out.println();
    }

    // ============ 测试辅助方法 ============

    /**
     * des: 按 LeetCode 的层序数组（null 表示空节点）构建二叉树
     */
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

    /**
     * des: 层序序列化，格式与 LeetCode 一致（内部空节点输出 null，末尾多余的 null 省略）
     */
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
