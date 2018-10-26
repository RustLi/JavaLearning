package data_structure;

import java.util.ArrayList;
import java.util.List;

public class BinTree {

    //定义二叉树节点
    public class Node{
        private Node leftChild;
        private Node rightChild;
        private Object data;
        public Node(Object data){
            this.data = data;
        }
    }

    //构建二叉树，返回根节点
    public Node createBinTree(int[] arr){
        if (arr == null){
            return null;
        }
        if (arr.length == 1){
            return new Node(arr[0]);
        }
        List<Node> nodeList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++){
            nodeList.add(new Node(arr[i]));
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
    public void preOrderReaverse(Node node){
        if (node == null){
            return;
        }
        System.out.println(node.data);
        preOrderReaverse(node.leftChild);
        preOrderReaverse(node.rightChild);
    }


    //中序遍历
    public void inOrderTraverse(Node node){
        if (node == null){
            return;
        }
        inOrderTraverse(node.leftChild);
        System.out.println(node.data);
        inOrderTraverse(node.rightChild);
    }

    //后序遍历
    public void postOrderTraverse(Node node){
        if (node == null){
            return;
        }
        postOrderTraverse(node.leftChild);
        System.out.println(node.data);
        postOrderTraverse(node.rightChild);
    }

}
