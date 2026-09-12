package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

/**
 * @date: 2026/08/27
 * @author: lwl
 * @description: 二叉树中的最大路径和（LeetCode 124）
 *
 *  路径被定义为一条从树中任意节点出发，沿父子连接到达任意节点的序列，该路径至少包含一个节点，
 *  不一定经过根节点；路径和为路径上所有节点值之和（节点值可为负数）。求最大路径和。
 *
 *  核心思想：任意一条路径都有唯一一个"拐点"（路径上深度最小的节点）。
 *  因此对每个节点做后序遍历，先算出左右子树能向上贡献的最大"单边路径和"（只能选一条分支），
 *  负数贡献直接舍弃（取 0），最后在当前节点把左右两边拼起来更新全局最大路径和。
 *
 *  demo 树1（经典形态，路径经过根节点）：
 *        1
 *       / \
 *      2   3
 *  路径 2 -> 1 -> 3，最大路径和 = 6
 *
 *  demo 树2（最优路径藏在右子树内部，且不经过根节点）：
 *       -10
 *       / \
 *      9   20
 *         /  \
 *        15   7
 *  路径 15 -> 20 -> 7，最大路径和 = 42
 *  （若带上根节点 -10 反而拖累总和，故最优路径绕开了根节点）
 *
 *  demo 树3（单节点为负数）：
 *       -3
 *  路径只能取该节点本身，最大路径和 = -3
 */
public class MaxPathSum {

    // 全局最大路径和
    private int maxSum = Integer.MIN_VALUE;

    public static void main(String[] args) {
        MaxPathSum solution = new MaxPathSum();

        // 例1：经典形态，路径经过根节点 2 -> 1 -> 3，和为 6
        Integer[] array1 = {1, 2, 3};
        TreeNode root1 = TreeNode.createTree(array1);
        System.out.println("树1 最大路径和 = " + solution.maxPathSum(root1));

        // 例2：最优路径不经过根节点 15 -> 20 -> 7，和为 42
        Integer[] array2 = {-10, 9, 20, null, null, 15, 7};
        TreeNode root2 = TreeNode.createTree(array2);
        System.out.println("树2 最大路径和 = " + solution.maxPathSum(root2));

        // 例3：单节点为负数，路径只能取自身，和为 -3
        TreeNode root3 = new TreeNode(-3);
        System.out.println("树3 最大路径和 = " + solution.maxPathSum(root3));
    }

    /**
     * des: 求二叉树的最大路径和
     */
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        gain(root);
        return maxSum;
    }

    /**
     * des: 后序遍历返回以 node 为起点向下延伸的最大"单边"路径和（只能选一条分支），
     *      同时在当前节点作为拐点处更新全局最大路径和
     */
    private int gain(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 负数贡献直接舍弃（取 0），保证向上延伸时路径和尽可能大
        int leftGain = Math.max(0, gain(node.left));
        int rightGain = Math.max(0, gain(node.right));

        // 以当前节点为拐点：左贡献 + 当前值 + 右贡献，拼成一条完整路径更新全局最大值
        maxSum = Math.max(maxSum, node.val + leftGain + rightGain);

        // 向上返回：路径不能分叉，只能选贡献更大的一条分支，并加上当前节点值
        return node.val + Math.max(leftGain, rightGain);
    }
}
