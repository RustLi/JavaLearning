package org.lwl.leetcode.code.tree;

import org.lwl.datastructure.tree.TreeNode;

/**
 * @date: 2026/08/20
 * @author: lwl
 * @description: 二叉树的直径（LeetCode 543）
 *
 *  给定一棵二叉树的根节点 root，返回该树的直径。
 *  直径 = 树中任意两个节点之间最长路径的长度（边数），这条路径可能经过也可能不经过根节点。
 *
 *  核心思想：任意一条最长路径都有一个"拐点"（路径经过的最高节点），
 *  且经过该拐点的路径长度 = 左子树高度 + 右子树高度。
 *  因此用后序遍历求每个节点的高度，同时更新全局最大值（左高 + 右高）即为直径。
 *
 *  demo 树1：
 *        1
 *       / \
 *      2   3
 *     / \
 *    4   5
 *  最长路径 4 -> 2 -> 1 -> 3（3 条边）-> 直径 = 3
 *
 *  demo 树2（直径不经过根节点）：
 *        1
 *       / \
 *      2   3
 *     / \
 *    4   5
 *   / \   \
 *  6   7   8
 *  最长路径 6 -> 4 -> 2 -> 5 -> 8（4 条边）-> 直径 = 4
 *  （经过根节点 1 的最长路径仅 3 条边，小于 4，故直径藏在左子树内部）
 */
public class DiameterOfBinaryTree {

    // 全局最长直径（边数）
    private int maxDiameter = 0;

    public static void main(String[] args) {
        DiameterOfBinaryTree solution = new DiameterOfBinaryTree();

        // 例1：经典形态，直径经过根节点
        Integer[] array1 = {1, 2, 3, 4, 5};
        TreeNode root1 = TreeNode.createTree(array1);
        System.out.println("树1 直径 = " + solution.diameterOfBinaryTree(root1));

        // 例2：直径不经过根节点，藏在左子树内部（6->4->2->5->8，4 条边）
        Integer[] array2 = {1, 2, 3, 4, 5, null, null, 6, 7, null, 8};
        TreeNode root2 = TreeNode.createTree(array2);
        System.out.println("树2 直径 = " + solution.diameterOfBinaryTree(root2));

        // 例3：只有根节点，直径为 0
        TreeNode root3 = new TreeNode(1);
        System.out.println("树3 直径 = " + solution.diameterOfBinaryTree(root3));
    }

    /**
     * des: 求二叉树直径
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        height(root);
        return maxDiameter;
    }

    /**
     * des: 后序遍历返回以 node 为根的子树高度（边数），同时更新全局直径
     */
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        // 经过当前节点的最长路径 = 左子树高度 + 右子树高度
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

        // 当前子树高度 = 较高子树的边数 + 1（连到当前节点的一条边）
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
