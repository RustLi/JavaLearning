package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @date: 2026/08/18
 * @author: lwl
 * @description: 二叉搜索树中第 K 小的元素（LeetCode 230）
 *
 *  给定一个二叉搜索树的根节点 root，和一个整数 k，返回第 k 小的元素（k 从 1 开始计数）。
 *
 *  核心思想：BST 的中序遍历结果是升序序列，因此"第 k 小" == "中序遍历第 k 个访问的节点"。
 *
 *       5
 *     /   \
 *    3     6
 *   / \
 *  2   4
 *
 *  中序遍历：2 -> 3 -> 4 -> 5 -> 6（升序）
 *  例1: k = 1 -> 2
 *  例2: k = 3 -> 4
 *  例3: k = 5 -> 6
 */
public class KthSmallestElement {

    /** 已访问节点计数 */
    private int count = 0;
    /** 第 k 小的值 */
    private int result = 0;

    public static void main(String[] args) {
        // 层序构建二叉搜索树
        Integer[] array = {5, 3, 6, 2, 4};
        TreeNode root = TreeNode.createTree(array);

        KthSmallestElement solution = new KthSmallestElement();

        // 例1：k = 1 -> 第1小 = 2
        System.out.println("例1: k=1 -> " + solution.kthSmallest(root, 1));
        // 例2：k = 3 -> 第3小 = 4
        System.out.println("例2: k=3 -> " + solution.kthSmallest(root, 3));
        // 例3：k = 5 -> 第5小 = 6
        System.out.println("例3: k=5 -> " + solution.kthSmallest(root, 5));
    }

    /**
     * des: 解法一（递归）：中序遍历 + 计数，访问到第 k 个节点即剪枝返回
     */
    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        result = 0;
        inorder(root, k);
        return result;
    }

    /**
     * des: 递归中序遍历，返回是否已找到第 k 小的节点（用于剪枝）
     */
    private boolean inorder(TreeNode node, int k) {
        if (node == null) {
            return false;
        }
        // 1. 先遍历左子树
        if (inorder(node.left, k)) {
            return true;
        }
        // 2. 访问当前节点并计数
        count++;
        if (count == k) {
            result = node.val;
            return true;
        }
        // 3. 再遍历右子树
        return inorder(node.right, k);
    }

    /**
     * des: 解法二（迭代）：显式栈模拟中序遍历，找到第 k 个节点立即返回
     */
    public int kthSmallestIterative(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;

        while (cur != null || !stack.isEmpty()) {
            // 一路向左，把路径上的节点压栈（模拟递归深入左子树）
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // 弹栈即"访问"当前节点
            cur = stack.pop();
            k--;
            if (k == 0) {
                return cur.val;
            }
            // 转向右子树，继续中序
            cur = cur.right;
        }
        return -1; // 不会走到这里，k 保证在 [1, 节点数] 范围内
    }
}
