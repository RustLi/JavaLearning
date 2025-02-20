package org.lwl.datastructure.tree;

import java.util.*;

public class BinTree {

    //定义二叉树节点
    public static class TreeNode{
        public TreeNode left;
        public TreeNode right;
        public Object data;
        public TreeNode(Object data){
            this.data = data;
        }
    }

    public static void main(String[] args){
        binTreeTest();

//        BinTree binTree = new BinTree();
//        int[] array = {3,5,1,6,7,9,2,4};
//        BinTree.TreeNode root = binTree.createBinTree(array);
//        System.out.println("层序遍历-----");
//        binTree.breadthFirstTraverse(root);
//        System.out.println("前序遍历-----");
//        binTree.preOrderTraverseNoRecursion(root);
//        System.out.println("中序遍历-----");
//        binTree.inOrderTraverseNoRecursion(root);
//        System.out.println("后序遍历-----");
//        binTree.postOrderTraverseNoRecursion(root);
//        System.out.println("深度优先----");
//        binTree.depthFirstTraverse(root);
    }

    private static void binTreeTest(){
        BinTree binTree = new BinTree();
//        int[] array = {3,5,1,6,7,9,2,3};
//        int[] array = {3,5,1};
        int[] array = {1, 2, 3, 4, 5, 6, 7};

        BinTree.TreeNode root = binTree.createBinTree(array,0);

        System.out.println("先序遍历：");
        binTree.preOrderReverse(root);
        System.out.println();

        System.out.println("中序遍历：");
        binTree.inOrderTraverse(root);
        System.out.println();

        System.out.println("后序遍历：");
        binTree.postOrderTraverse(root);
    }
    
    //构建二叉树，返回根节点
    public static TreeNode createBinTree(int[] values, int index){
        if (index >= values.length || values[index] == -1) { // 假设-1表示空节点
            return null;
        }

        TreeNode node = new TreeNode(values[index]);
        node.left = createBinTree(values, 2 * index + 1); // 左子节点索引
        node.right = createBinTree(values, 2 * index + 2); // 右子节点索引

        return node;
    }


    /**
     *  先序遍历: root -- left -- right
     **/
    public void preOrderReverse(TreeNode node){
        if (node == null){
            return;
        }
        System.out.println(node.data + "");
        preOrderReverse(node.left);
        preOrderReverse(node.right);
    }


    /**
     * 中序遍历 left -- root -- right
     **/
    public void inOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        inOrderTraverse(node.left);
        System.out.println(node.data + "");
        inOrderTraverse(node.right);
    }

    /**
     * 后序遍历 left -- right -- root
     **/
    public void postOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        postOrderTraverse(node.left);
        postOrderTraverse(node.right);
        System.out.println(node.data + "");
    }


    /**
     * 先序遍历二叉树（非递归），深度优先遍历的特例
     * @param root
     */
    public void preOrderTraverseNoRecursion(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode currentNode;
        stack.push(root);
        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            System.out.print(currentNode.data + " ");
            if (currentNode.right != null)
                stack.push(currentNode.right);
            if (currentNode.left != null)
                stack.push(currentNode.left);
        }
    }

    /**
     * 中序遍历二叉树（非递归）
     * @param root
     */
    public void inOrderTraverseNoRecursion(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode currentNode = root;
        while (currentNode != null || !stack.isEmpty()) {
            // 一直循环到二叉排序树最左端的叶子结点（currentNode是null）
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = stack.pop();
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.right;
        }
    }

    /**
     * 后序遍历二叉树（非递归）
     * @param root
     */
    public void postOrderTraverseNoRecursion(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode currentNode = root;
        TreeNode rightNode = null;
        while (currentNode != null || !stack.isEmpty()) {
            // 一直循环到二叉排序树最左端的叶子结点（currentNode是null）
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = stack.pop();
            // 当前结点没有右结点或上一个结点（已经输出的结点）是当前结点的右结点，则输出当前结点
            while (currentNode.right == null || currentNode.right == rightNode) {
                System.out.print(currentNode.data + " ");
                rightNode = currentNode;
                if (stack.isEmpty()) {
                    return; //root以输出，则遍历结束
                }
                currentNode = stack.pop();
            }
            stack.push(currentNode); //还有右结点没有遍历
            currentNode = currentNode.right;
        }
    }

    /**
     * 广度优先遍历二叉树，又称层次遍历二叉树，采用队列，先进先出
     */
    public static void breadthFirstTraverse(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode currentNode;
        queue.offer(root);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            System.out.print(currentNode.data + " ");
            if (currentNode.left != null)
                queue.offer(currentNode.left);
            if (currentNode.right != null)
                queue.offer(currentNode.right);
        }
    }


    /**
     * 深度优先遍历，采用栈，后进先出
     */
    public static void depthFirstTraverse(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode currentNode;
        if (root == null){
            return;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            System.out.print(currentNode.data + " ");
            if (currentNode.right != null)
                stack.push(currentNode.right);
            if (currentNode.left != null)
                stack.push(currentNode.left);
        }
    }

}
