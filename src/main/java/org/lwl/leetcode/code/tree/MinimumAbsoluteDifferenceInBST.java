package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @date: 2026/08/20
 * @author: lwl
 * @description: 二叉搜索树的最小绝对差（LeetCode 530 / 783）
 *
 *  给你一个二叉搜索树的根节点 root ，返回树中任意两不同节点值之间的最小差值。
 *  差值是一个正数，其数值等于两值之差的绝对值。
 *
 *  核心思想：BST 的中序遍历结果是升序序列，最小差值必然出现在升序序列中相邻的两个值之间。
 *  因此在遍历过程中记录"上一个访问的节点值"，不断与当前值做差并更新最小值。
 *
 *       4
 *      / \
 *     2   6
 *    / \
 *   1   3
 *
 *  中序遍历：1 -> 2 -> 3 -> 4 -> 6（升序）
 *  相邻差值：|1-2|=1, |2-3|=1, |3-4|=1, |4-6|=2
 *  最小差值 = 1
 */
public class MinimumAbsoluteDifferenceInBST {

    /** 上一个访问到的节点值（null 表示尚未访问任何节点） */
    private Integer prev;
    /** 当前得到的最小差值 */
    private int minDiff;

    public static void main(String[] args) {
        // 层序构建二叉搜索树
        Integer[] array = {4, 2, 6, 1, 3};
        TreeNode root = TreeNode.createTree(array);

        MinimumAbsoluteDifferenceInBST solution = new MinimumAbsoluteDifferenceInBST();

        // 例1：递归解法
        System.out.println("例1（递归）: 最小差值 = " + solution.getMinimumDifference(root));
        // 例2：迭代解法
        System.out.println("例2（迭代）: 最小差值 = " + solution.getMinimumDifferenceIterative(root));
    }

    /**
     * des: 解法一（递归）：中序遍历，边遍历边比较相邻差值
     */
    public int getMinimumDifference(TreeNode root) {
        prev = null;
        minDiff = Integer.MAX_VALUE;
        inorder(root);
        return minDiff;
    }

    private void inorder(TreeNode node) {
        if (node == null) {
            return;
        }
        // 1. 先遍历左子树
        inorder(node.left);
        // 2. 访问当前节点：与上一个节点比较差值（升序保证 node.val >= prev）
        if (prev != null) {
            minDiff = Math.min(minDiff, node.val - prev);
        }
        prev = node.val;
        // 3. 再遍历右子树
        inorder(node.right);
    }

    /**
     * des: 解法二（迭代）：显式栈模拟中序遍历，同样只比较相邻两个值
     */
    public int getMinimumDifferenceIterative(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        Integer prev = null;
        int minDiff = Integer.MAX_VALUE;

        while (cur != null || !stack.isEmpty()) {
            // 一路向左，把路径上的节点压栈（模拟递归深入左子树）
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // 弹栈即"访问"当前节点
            cur = stack.pop();
            if (prev != null) {
                minDiff = Math.min(minDiff, cur.val - prev);
            }
            prev = cur.val;
            // 转向右子树，继续中序
            cur = cur.right;
        }
        return minDiff;
    }
}
