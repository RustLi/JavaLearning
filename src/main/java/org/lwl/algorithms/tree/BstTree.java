package org.lwl.datastructure.tree;

/**
 * 判断二叉树是否为有效的二叉搜索树（BST）
 *
 * BST 定义：
 * - 对于任意节点 node：
 *   所有左子树节点值 < node.val < 所有右子树节点值
 */
public class BstTree {

    public static void main(String[] args) {
        // 构造一棵合法的 BST：  2
        //                    /   \
        //                   1     3
        Integer[] validArray = {2, 1, 3};
        TreeNode validRoot = TreeNode.createTree(validArray);
        System.out.println("合法 BST 示例是否有效: " + isValidBST(validRoot));

        // 构造一棵非法的 BST：
        //       5
        //      / \
        //     1   4
        //        / \
        //       3   6
        // 4 的左子节点 3 小于根节点 5，违反 BST 定义
        TreeNode invalidRoot = new TreeNode(5);
        invalidRoot.left = new TreeNode(1);
        invalidRoot.right = new TreeNode(4);
        invalidRoot.right.left = new TreeNode(3);
        invalidRoot.right.right = new TreeNode(6);
        System.out.println("非法 BST 示例是否有效: " + isValidBST(invalidRoot));
    }

    /**
     * 判断一棵树是否为有效 BST
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    /**
     * 递归校验：
     * - 对当前节点 node，需要满足：lower < node.val < upper
     * - 左子树最大值 < node.val，右子树最小值 > node.val
     */
    private static boolean isValidBST(TreeNode node, Integer lower, Integer upper) {
        if (node == null) {
            return true;
        }

        int val = node.val;
        if (lower != null && val <= lower) {
            return false;
        }
        if (upper != null && val >= upper) {
            return false;
        }

        // 左子树所有节点必须 < 当前节点值
        if (!isValidBST(node.left, lower, val)) {
            return false;
        }
        // 右子树所有节点必须 > 当前节点值
        return isValidBST(node.right, val, upper);
    }
}

