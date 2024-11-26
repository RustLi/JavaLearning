package org.lwl.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
    public Integer val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Integer val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root = createTree(array);
        System.out.println("前序遍历:");
        preorderTraversal(root);
        System.out.println();
        System.out.println("中序遍历:");
        inorderTraversal(root);
        System.out.println();
        System.out.println("后序遍历:");
        postorderTraversal(root);
        System.out.println();
        System.out.println("广度优先遍历:");
        bfsTraversal(root);
    }

    /**
     * 构建一个完全二叉树，采用层次遍历
     **/
    public static TreeNode createTree(Integer[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(array[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (i < array.length) {
            TreeNode node = queue.poll();
            if (node == null){
                break;
            }
            // 分配左子节点
            if (array[i] != null){
                node.left = new TreeNode(array[i]);
                queue.offer(node.left);
            }
            i++;

            // 分配右子节点
            if (i < array.length && array[i] != null) {
                node.right = new TreeNode(array[i]);
                queue.offer(node.right);
            }
            i++;
        }

        return root;
    }


    /**
     * 前序遍历
     **/
    public static void preorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        // 访问根节点
        System.out.print(root.val + " ");

        // 遍历左子树
        preorderTraversal(root.left);

        // 遍历右子树
        preorderTraversal(root.right);
    }

    /**
     * 中序遍历
     **/
    public static void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        // 遍历左子树
        inorderTraversal(root.left);

        // 访问根节点
        System.out.print(root.val + " ");

        // 遍历右子树
        inorderTraversal(root.right);
    }


    /**
     * 后序遍历
     **/
    public static void postorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        // 遍历左子树
        postorderTraversal(root.left);

        // 遍历右子树
        postorderTraversal(root.right);

        // 访问根节点
        System.out.print(root.val + " ");
    }

    /**
     * 广度优先遍历二叉树，又称层次遍历二叉树，采用队列，先进先出
     */
    // 使用队列实现广度优先遍历
    public static void bfsTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            // 访问当前节点
            System.out.print(node.val + " ");

            // 将当前节点的左子节点加入队列
            if (node.left != null) {
                queue.offer(node.left);
            }

            // 将当前节点的右子节点加入队列
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }
}