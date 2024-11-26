package org.lwl.algorithms.tree;


import org.lwl.datastructure.tree.BinTree;

/**
 * @author: lwl
 * @date: 2022/9/5
 * @description:  计算二叉树最大深度
 *      思路：1. 遍历，记录最大深度和当前的最大深度；
 *          2. 构造子树，递归调用；
 **/
public class MaxDepthTree {
    public static void main(String[] args) {

    }

    // 记录最大深度
    int res = 0;
    // 记录遍历到的节点的深度
    int depth = 0;

    /**
     * 方法一：前序遍历
     **/
    // 主函数
    int maxDepth(BinTree.TreeNode root) {
        traverse(root);
        return res;
    }

    // 二叉树遍历框架
    void traverse(BinTree.TreeNode root) {
        if (root == null) {
            return;
        }
        // 前序位置
        depth++;
        if (root.leftChild == null && root.rightChild == null) {
            // 到达叶子节点，更新最大深度
            res = Math.max(res, depth);
        }
        traverse(root.leftChild);
        traverse(root.rightChild);
        // 后序位置
        depth--;
    }

    /**
     * 方法二：构造子树遍历
     **/
    // 定义：输入根节点，返回这棵二叉树的最大深度
    int maxDepth1(BinTree.TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 利用定义，计算左右子树的最大深度
        int leftMax = maxDepth1(root.leftChild);
        int rightMax = maxDepth1(root.rightChild);
        // 整棵树的最大深度等于左右子树的最大深度取最大值，
        // 然后再加上根节点自己
        int res = Math.max(leftMax, rightMax) + 1;

        return res;
    }
}
