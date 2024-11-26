package org.lwl.algorithms.tree;
import org.lwl.datastructure.tree.TreeNode;


/**
 *
 *@author  lwl
 *@date 2024/10/23
 *@description  求二叉树的最大路径和
 */
public class BinaryTreeMaximumPathSum {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathSumHelper(root);
        return maxSum;
    }

    private int maxPathSumHelper(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 递归计算左右子树的最大路径和
        int leftSum = Math.max(0, maxPathSumHelper(node.left));
        int rightSum = Math.max(0, maxPathSumHelper(node.right));

        System.out.println("node.val: " + node.val + ", leftSum: " + leftSum + ", rightSum: " + rightSum);
        // 更新全局最大路径和
        maxSum = Math.max(maxSum, node.val + leftSum + rightSum);

        // 返回当前节点的最大贡献值
        return node.val + Math.max(leftSum, rightSum);
    }

    public static void main(String[] args) {
        // 构建示例二叉树
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        //返回 5 2 1 3 7 = 18
        BinaryTreeMaximumPathSum solution = new BinaryTreeMaximumPathSum();
        int result = solution.maxPathSum(root);
        System.out.println("Maximum Path Sum: " + result);
    }
}
