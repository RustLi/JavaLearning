import data_structure.BinTree;
import data_structure.ListNode;
import data_structure.SingleLinkedList;
import sorts.BubbleSort;
import sorts.InsertSort;
import sorts.QuickSort;
import sorts.SelectSort;

import static data_structure.ListNode.reverseList;
import static data_structure.ListNode.reverseList2;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
//        sortTest();
//        strucTest();
//        singleLinkedTest();
        binTreeTest();
    }

    private static void sortTest(){
        int[] intput = {9,2,3,5,1,6,7};

        //for bubbleSort begin
        /* BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(intput);*/
        //for bubbleSort end

        //for quickSort
        /*QuickSort quickSort = new QuickSort();
        quickSort.quickSort(intput);*/

        //for SelectSort
        /*SelectSort selectSort = new SelectSort();
        selectSort.selectSort(intput);*/

        //for InsertSort
        InsertSort insertSort = new InsertSort();
        insertSort.insertSort(intput);
    }


    //反转链表
    private static void strucTest(){
        ListNode<Integer> head = new ListNode<>(1);
        head.next= new ListNode<>(2);
        head.next.next = new ListNode<>(3);

        ListNode<Integer> h = head;
        while (null != h){
            System.out.println(h.data);
            h = h.next;
        }
//        head = reverseList(head);
        head = reverseList2(head);

        System.out.println("反转以后的：：：：");
        while (null != head){
            System.out.println(head.data);
            head = head.next;
        }
    }

    private static void singleLinkedTest(){
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addHead("A");
        singleLinkedList.addHead("B");
        singleLinkedList.addHead("C");
        singleLinkedList.addHead("D");

        singleLinkedList.display();

        singleLinkedList.delete("B");
        singleLinkedList.display();

        System.out.println(singleLinkedList.findNode("D"));
    }

    private static void binTreeTest(){
        BinTree binTree = new BinTree();
        int[] array = {3,5,1,6,7,9,2,3};
        BinTree.Node root = binTree.createBinTree(array);

        System.out.println("先序遍历：");
        binTree.preOrderReaverse(root);
        System.out.println();

        System.out.println("中序遍历：");
        binTree.inOrderTraverse(root);
        System.out.println();

        System.out.println("后序遍历：");
        binTree.postOrderTraverse(root);
    }
}
