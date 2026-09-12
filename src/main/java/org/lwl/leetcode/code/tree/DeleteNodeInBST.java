package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

/**
 * @date: 2026/08/20
 * @author: lwl
 * @description: 删除二叉搜索树中的节点（LeetCode 450）
 *
 *  给定二叉搜索树（BST）的根节点 root 和一个值 key，删除 BST 中 key 对应的节点，并保证 BST 的性质不变。
 *  返回 BST（有可能被更新）的根节点的引用。若树中不存在 key，则无需删除，直接返回原树。
 *
 *  核心思想：先搜索定位，再分情况删除。
 *   1. 无左孩子（含叶子）：右孩子直接顶替；
 *   2. 无右孩子：左孩子直接顶替；
 *   3. 有两个孩子：取右子树的最小节点（中序后继），用它的值覆盖待删节点，
 *      再递归删除右子树中该后继节点——把"删双孩子"转化为"删单孩子/叶子"。
 *
 *       5                删除 3                5
 *      / \                                   / \
 *     3   6                                 4   6
 *    / \   \                               /     \
 *   2   4   7                             2       7
 *
 *  删除过程：5 > 3 往左 -> 找到 3（双孩子），右子树最小节点 = 4
 *            用 4 覆盖 3 -> 再递归删除右子树中的 4 -> 3 变为 4，4 被删
 *  删除后中序遍历：2 -> 4 -> 5 -> 6 -> 7（仍是升序，BST 性质保持）
 */
public class DeleteNodeInBST {

    public static void main(String[] args) {
        // 层序构建二叉搜索树
        Integer[] array = {5, 3, 6, 2, 4, null, 7};
        TreeNode root = TreeNode.createTree(array);

        DeleteNodeInBST solution = new DeleteNodeInBST();

        // 例1：删除 3（有两个孩子）-> 中序 2 4 5 6 7
        TreeNode r1 = solution.deleteNode(TreeNode.createTree(array), 3);
        System.out.print("例1 删除 3（双孩子）: ");
        TreeNode.inorderTraversal(r1);
        System.out.println();

        // 例2：删除 6（只有右孩子）-> 7 顶替 -> 中序 2 3 4 5 7
        TreeNode r2 = solution.deleteNode(TreeNode.createTree(array), 6);
        System.out.print("例2 删除 6（单孩子）: ");
        TreeNode.inorderTraversal(r2);
        System.out.println();

        // 例3：删除 2（叶子）-> 中序 3 4 5 6 7
        TreeNode r3 = solution.deleteNode(TreeNode.createTree(array), 2);
        System.out.print("例3 删除 2（叶子）  : ");
        TreeNode.inorderTraversal(r3);
        System.out.println();

        // 例4：删除不存在的 key=10 -> 原树不变
        TreeNode r4 = solution.deleteNode(TreeNode.createTree(array), 10);
        System.out.print("例4 删除 10（不存在）: ");
        TreeNode.inorderTraversal(r4);
        System.out.println();
    }

    /**
     * des: 递归删除：先搜索定位，再分情况处理，最后接回父节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        // 递归出口：树为空 或 搜索到底未找到
        if (root == null) {
            return null;
        }

        // 搜索阶段：key 小于当前值往左，大于往右
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // 找到目标节点，分情况删除

            // 情况1：无左孩子（含叶子），右孩子顶替
            if (root.left == null) {
                return root.right;
            }
            // 情况2：无右孩子，左孩子顶替
            if (root.right == null) {
                return root.left;
            }
            // 情况3：有两个孩子，取右子树最小节点（中序后继）的值覆盖
            TreeNode successor = findMin(root.right);
            root.val = successor.val;
            // 递归删除右子树中那个后继节点（此时它必是单孩子或叶子）
            root.right = deleteNode(root.right, successor.val);
        }
        return root;
    }

    /**
     * des: 找到子树中的最小节点：一路向左走到底
     */
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
