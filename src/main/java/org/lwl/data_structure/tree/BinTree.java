package data_structure.tree;

import java.util.*;

public class BinTree {

    //定义二叉树节点
    public static class TreeNode{
        public TreeNode leftChild;
        public TreeNode rightChild;
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
        int[] array = {3,5,1};
        BinTree.TreeNode root = binTree.createBinTree(array);

        System.out.println("先序遍历：");
        binTree.preOrderReaverse(root);
        System.out.println();

        System.out.println("中序遍历：");
        binTree.inOrderTraverse(root);
        System.out.println();

        System.out.println("后序遍历：");
        binTree.postOrderTraverse(root);
    }
    
    //构建二叉树，返回根节点
    public static TreeNode createBinTree(int[] arr){
        if (arr == null){
            return null;
        }
        if (arr.length == 1){
            return new TreeNode(arr[0]);
        }
        List<TreeNode> nodeList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++){
            nodeList.add(new TreeNode(arr[i]));
        }

        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < arr.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).leftChild = nodeList
                    .get(parentIndex * 2 + 1);
            // 右孩子
            nodeList.get(parentIndex).rightChild = nodeList
                    .get(parentIndex * 2 + 2);
        }
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = arr.length / 2 - 1;
        // 左孩子
        nodeList.get(lastParentIndex).leftChild = nodeList
                .get(lastParentIndex * 2 + 1);
        // 右孩子,如果数组的长度为奇数才建立右孩子
        if (arr.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList
                    .get(lastParentIndex * 2 + 2);
        }
        return nodeList.get(0);
    }


    //先序遍历
    public void preOrderReaverse(TreeNode node){
        if (node == null){
            return;
        }
        System.out.println(node.data);
        preOrderReaverse(node.leftChild);
        preOrderReaverse(node.rightChild);
    }


    //中序遍历
    public void inOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        inOrderTraverse(node.leftChild);
        System.out.println(node.data);
        inOrderTraverse(node.rightChild);
    }

    //后序遍历
    public void postOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.println(node.data);
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
            if (currentNode.rightChild != null)
                stack.push(currentNode.rightChild);
            if (currentNode.leftChild != null)
                stack.push(currentNode.leftChild);
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
                currentNode = currentNode.leftChild;
            }
            currentNode = stack.pop();
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.rightChild;
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
                currentNode = currentNode.leftChild;
            }
            currentNode = stack.pop();
            // 当前结点没有右结点或上一个结点（已经输出的结点）是当前结点的右结点，则输出当前结点
            while (currentNode.rightChild == null || currentNode.rightChild == rightNode) {
                System.out.print(currentNode.data + " ");
                rightNode = currentNode;
                if (stack.isEmpty()) {
                    return; //root以输出，则遍历结束
                }
                currentNode = stack.pop();
            }
            stack.push(currentNode); //还有右结点没有遍历
            currentNode = currentNode.rightChild;
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
            if (currentNode.leftChild != null)
                queue.offer(currentNode.leftChild);
            if (currentNode.rightChild != null)
                queue.offer(currentNode.rightChild);
        }
    }


    /**
     * 深度优先遍历，采用栈，后进先出
     */
    public void depthFirstTraverse(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode currentNode;
        if (root == null){
            return;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            System.out.print(currentNode.data + " ");
            if (currentNode.rightChild != null)
                stack.push(currentNode.rightChild);
            if (currentNode.leftChild != null)
                stack.push(currentNode.leftChild);
        }
    }

}
