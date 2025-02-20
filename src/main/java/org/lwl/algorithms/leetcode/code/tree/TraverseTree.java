package org.lwl.algorithms.leetcode.code.tree;


import org.lwl.datastructure.tree.BinTree;

/**
 * @date: 2021/4/10
 * @author: lwl
 * @description:
 *
 *  // 「翻转二叉树」，输入一个二叉树根节点 root，让你把整棵树镜像翻转
 *
 *       4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 */
public class TraverseTree {

    public static void main(String[] args) {
        int[] array = {4,2,7,1,3,6,9};
        BinTree.TreeNode root = BinTree.createBinTree(array,0);

        System.out.println("before..");
        BinTree.breadthFirstTraverse(root);

        BinTree.TreeNode newRoot = traverse(root);
        System.out.println("after...");
        BinTree.depthFirstTraverse(newRoot);
    }


    /**
     * des: 翻转二叉树
     * @param
     */
    private static BinTree.TreeNode traverse(BinTree.TreeNode root){
        if (root == null) return null;

        //前序遍历

        //交换root节点的左右节点
        BinTree.TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        traverse(root.left);
        traverse(root.right);
        return root;
    }
}
