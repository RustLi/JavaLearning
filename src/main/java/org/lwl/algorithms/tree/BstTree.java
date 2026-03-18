package org.lwl.algorithms.tree;

import org.lwl.datastructure.tree.TreeNode;

/**
 * 二叉搜索树（BST）相关算法：
 * 1）判断是否为有效 BST
 * 2）增删改查（基于 TreeNode）
 *
 * BST 定义：对任意节点 node，满足：
 * - 所有左子树节点值 < node.val < 所有右子树节点值
 */
public class BstTree {

    public static void main(String[] args) {
        // 构建一棵 BST：5 / \ 3 7 / \ / 2 4 6
        TreeNode root = null;
        root = insert(root, 5);
        root = insert(root, 3);
        root = insert(root, 7);
        root = insert(root, 2);
        root = insert(root, 4);
        root = insert(root, 6);

        System.out.print("初始 BST 中序遍历（有序）: ");
        TreeNode.inorderTraversal(root);
        System.out.println();
        System.out.println("是否有效 BST: " + isValidBST(root));

        // 查询
        System.out.println("查找值为 4 的节点是否存在: " + (search(root, 4) != null));
        System.out.println("查找值为 10 的节点是否存在: " + (search(root, 10) != null));

        // 更新：将 4 更新为 8（先删后插）
        root = update(root, 4, 8);
        System.out.print("更新 4 -> 8 后中序遍历: ");
        TreeNode.inorderTraversal(root);
        System.out.println();
        System.out.println("是否仍为有效 BST: " + isValidBST(root));

        // 删除：删除 5（根节点）
        root = delete(root, 5);
        System.out.print("删除 5 后中序遍历: ");
        TreeNode.inorderTraversal(root);
        System.out.println();
        System.out.println("是否仍为有效 BST: " + isValidBST(root));
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

    /**
     * 查找：在 BST 中查找指定值
     */
    public static TreeNode search(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        if (target == root.val) {
            return root;
        } else if (target < root.val) {
            return search(root.left, target);
        } else {
            return search(root.right, target);
        }
    }

    /**
     * 插入：向 BST 中插入一个值，返回新的根节点
     */
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val < root.val) {
            root.left = insert(root.left, val);
        } else if (val > root.val) {
            root.right = insert(root.right, val);
        }
        // 若 val == root.val，当前实现不插入重复值
        return root;
    }

    /**
     * 删除：从 BST 中删除一个值，返回新的根节点
     */
    public static TreeNode delete(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (val < root.val) {
            root.left = delete(root.left, val);
        } else if (val > root.val) {
            root.right = delete(root.right, val);
        } else {
            // 找到要删除的节点
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                // 左右子树都存在：用右子树的最小节点替换当前节点
                TreeNode minNode = findMin(root.right);
                root.val = minNode.val;
                // 删除右子树中该最小节点
                root.right = delete(root.right, minNode.val);
            }
        }
        return root;
    }

    /**
     * 更新：将 oldVal 更新为 newVal（先删后插）
     */
    public static TreeNode update(TreeNode root, int oldVal, int newVal) {
        if (search(root, oldVal) == null) {
            return root;
        }
        root = delete(root, oldVal);
        root = insert(root, newVal);
        return root;
    }

    /**
     * 找到以 node 为根的子树中的最小值节点
     */
    private static TreeNode findMin(TreeNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}

