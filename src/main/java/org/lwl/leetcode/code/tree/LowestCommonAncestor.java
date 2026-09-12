package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

/**
 * @date: 2026/08/18
 * @author: lwl
 * @description: 二叉搜索树（BST）中两个节点的最近公共祖先（LCA）
 *
 *  给定一个二叉搜索树的根节点 root，以及两个指定节点 p、q，找出这两个节点最近的公共祖先。
 *
 *  核心思想：利用 BST 性质（左子树 < 根 < 右子树），从根节点向下找"分岔点"。
 *  当遇到第一个值介于 p 和 q 之间（或等于 p / q）的节点时，它就是最近公共祖先。
 *
 *       6
 *     /   \
 *    2     8
 *   / \   / \
 *  0   4 7   9
 *     / \
 *    3   5
 *
 *  例1: p = 2, q = 8  -> LCA = 6
 *  例2: p = 2, q = 4  -> LCA = 2（2 是 4 的祖先）
 *  例3: p = 0, q = 5  -> LCA = 2
 */
public class LowestCommonAncestor {

    public static void main(String[] args) {
        // 层序构建二叉搜索树
        Integer[] array = {6, 2, 8, 0, 4, 7, 9, null, null, 3, 5};
        TreeNode root = TreeNode.createTree(array);

        LowestCommonAncestor solution = new LowestCommonAncestor();

        // 例1：p = 2, q = 8
        TreeNode p1 = findNode(root, 2);
        TreeNode q1 = findNode(root, 8);
        System.out.println("例1: p=2, q=8 -> LCA = " + solution.lowestCommonAncestor(root, p1, q1).val);

        // 例2：p = 2, q = 4（2 是 4 的祖先）
        TreeNode p2 = findNode(root, 2);
        TreeNode q2 = findNode(root, 4);
        System.out.println("例2: p=2, q=4 -> LCA = " + solution.lowestCommonAncestor(root, p2, q2).val);

        // 例3：p = 0, q = 5
        TreeNode p3 = findNode(root, 0);
        TreeNode q3 = findNode(root, 5);
        System.out.println("例3: p=0, q=5 -> LCA = " + solution.lowestCommonAncestor(root, p3, q3).val);
    }

    /**
     * des: 解法一（递归）：利用 BST 性质向下搜索分岔点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // p、q 都在左子树，往左找
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        // p、q 都在右子树，往右找
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // 当前节点介于 p、q 之间（或等于其中一个），即为分岔点
        return root;
    }

    /**
     * des: 解法二（迭代）：与递归思路一致，用 while 循环替代递归栈
     */
    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        while (cur != null) {
            // p、q 都在左子树
            if (cur.val > p.val && cur.val > q.val) {
                cur = cur.left;
            }
            // p、q 都在右子树
            else if (cur.val < p.val && cur.val < q.val) {
                cur = cur.right;
            }
            // 当前节点是分岔点
            else {
                return cur;
            }
        }
        return null;
    }

    /**
     * des: 在 BST 中查找指定值的节点（仅用于测试构造 p、q）
     */
    private static TreeNode findNode(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        if (root.val == target) {
            return root;
        }
        return target < root.val ? findNode(root.left, target) : findNode(root.right, target);
    }
}
