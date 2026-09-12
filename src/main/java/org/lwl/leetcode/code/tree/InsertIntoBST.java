package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

/**
 * @date: 2026/08/20
 * @author: lwl
 * @description: 二叉搜索树中的插入操作（LeetCode 701）
 *
 *  给定二叉搜索树（BST）的根节点 root 和要插入树中的值 value，将值插入二叉搜索树，返回插入后的根节点。
 *  题目保证新值和原始 BST 中的任意节点值都不同（无需处理相等情况）。
 *  注意：可能存在多种有效插入方式，只要树在插入后仍保持 BST 即可，可返回任意有效结果。
 *
 *  核心思想：利用 BST 的搜索性质——左子树所有节点 < 根 < 右子树所有节点。
 *  从根出发一路按大小比较：value 小于当前节点往左走，大于往右走，走到空位（null）即创建新节点挂上。
 *  这样新节点必然满足沿路所有祖先的大小关系，BST 性质自然保持。
 *
 *       4             插入 5            4
 *      / \                            / \
 *     2   7         -------->        2   7
 *    / \                                /
 *   1   3                              5
 *
 *  插入过程：4 < 5 往右 -> 7 > 5 往左 -> 左孩子为空 -> 新节点 5 挂在 7.left
 *  插入后中序遍历：1 -> 2 -> 3 -> 4 -> 5 -> 7（仍是升序，BST 性质保持）
 */
public class InsertIntoBST {

    public static void main(String[] args) {
        // 层序构建二叉搜索树
        Integer[] array = {4, 2, 7, 1, 3};
        TreeNode root = TreeNode.createTree(array);

        InsertIntoBST solution = new InsertIntoBST();

        // 例1：递归插入 5
        TreeNode newRoot = solution.insertIntoBST(root, 5);
        System.out.print("例1（递归）插入后中序遍历: ");
        TreeNode.inorderTraversal(newRoot);
        System.out.println();

        // 例2：迭代插入 5（在例1结果基础上再次演示，树已包含 5，改用 0 体现不同走向）
        TreeNode root2 = TreeNode.createTree(array);
        TreeNode newRoot2 = solution.insertIntoBSTIterative(root2, 0);
        System.out.print("例2（迭代）插入后中序遍历: ");
        TreeNode.inorderTraversal(newRoot2);
        System.out.println();
    }

    /**
     * des: 解法一（递归）：按大小比较向下递归，遇到空位创建新节点
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 找到空位，创建新节点返回给父节点
        if (root == null) {
            return new TreeNode(val);
        }
        // 值小于当前节点，插入到左子树
        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            // 值大于当前节点，插入到右子树（题目保证不等，无需 else if）
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }

    /**
     * des: 解法二（迭代）：沿搜索路径找到空位，直接挂上新节点
     */
    public TreeNode insertIntoBSTIterative(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode cur = root;
        while (true) {
            if (val < cur.val) {
                // 左孩子为空 -> 空位，挂上新节点并结束
                if (cur.left == null) {
                    cur.left = new TreeNode(val);
                    break;
                }
                cur = cur.left;
            } else {
                // 右孩子为空 -> 空位，挂上新节点并结束
                if (cur.right == null) {
                    cur.right = new TreeNode(val);
                    break;
                }
                cur = cur.right;
            }
        }
        return root;
    }
}
