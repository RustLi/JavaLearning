package data_structure;

import java.util.ArrayList;
import java.util.List;

public class BinTree {

    //定义二叉树节点
    private class Node{
        private Node leftNode;
        private Node rightNode;
        private Object data;
        public Node(Object data){
            this.data = data;
        }
    }

    public Node initBinTree(int[] arr){
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

        int temp = 0;
        int m,n;
        while (temp <= (arr.length - 2) / 2){
            m = 2 * temp + 1;
            n = 2 * temp + 2;
            if ( m < arr.length){
                nodeList.get(temp).leftNode = nodeList.get(m);
            }
            if (n < arr.length){
                nodeList.get(temp).rightNode = nodeList.get(n);
            }
            temp++;
        }
        return nodeList.get(0);
    }


}
