package data_structure.listnode;

import java.util.Stack;

/**
 * @date: 2019/12/17
 * @author: lwl
 * @description: 链表的基础操作，读取，删除，插入等
 */
public class BasicNode {
    public static void main(String[] args) {
        ListNode<Integer> head = new ListNode<>(0);
        head.next= new ListNode<>(1);
        head.next.next = new ListNode<>(2);

        //测试：获取第2个数据，下标从0开始
        BasicNode basicNode = new BasicNode();
//        int ret = basicNode.getNode(head,1);
//        System.out.println(ret);

//        basicNode.printNode(head);
        //测试：插入链表，012 -->> 0312
//        basicNode.insertNode(head,1, new ListNode<>(3));
//        basicNode.printNode(head);

        //删除链表 012 -->> 02
//        basicNode.deleteNode(head,1);
//        basicNode.printNode(head);

        //头插法创建链表
//        ListNode createNode = new ListNode();
//        basicNode.createNodeHead(createNode, 5);
//        printNode(createNode);

        //尾插法创建链表
//        basicNode.createNodeTail(createNode, 3);
//        printNode(createNode);

        basicNode.clearNode(head);
        printNode(head);

    }

    /**
     * des: 打印链表中的数据
     * @param
     */
    private static void printNode(ListNode head){
        ListNode p = head;
        while (p != null){
            System.out.println(p.data);
            p = p.next;
        }
    }

    /**
     * des: 获取链表中第i个数据
     * @param
     */
    private int getNode(ListNode head, int i){
        ListNode p = head;
        int j = 0;
        while (p != null && j < i){
            p = p.next;
            j++;
        }
        if (p == null || j > i){
            return -1;
        }
        return (int)p.data;
    }

    /**
     * des: 在head第i 个位置之前插入链表sNode
     * @param
     */
    private int insertNode(ListNode head, int i , ListNode sNode){
        ListNode p = head;
        int j = 1;
        while (p != null && j < i){
            p = p.next;
            j++;
        }
        if (p == null || j > i){
            return -1;
        }
        sNode.next = p.next;
        p.next = sNode;
        return 0;
    }

    /**
     * des: 删除链表中第i个元素
     * @param
     */
    private int deleteNode(ListNode head, int i){
        ListNode p = head;
        int j = 1;
        while (p != null && j < i){
            p = p.next;
            j++;
        }
        if (p == null || j > i){
            return -1;
        }
        p.next = p.next.next;
        return 0;
    }

    /**
     * des: 头插法创建单链表
     * 1.建立空链表L，L头节点指针指向NULL；
     * 2.循环
     *     生成新节点P,赋值，P插入到头节点和前一新节点之间
     */
    private void createNodeHead(ListNode head, int n){
        head.next = null;
        for (int i = 0; i < n; i++) {
            ListNode p = new ListNode();
            p.data = Math.random();
            p.next = head.next;
            head.next = p;
        }
    }

    /**
     * des: 尾插法创建链表
     * @param
     */
    private void createNodeTail(ListNode head, int n){
        for (int i = 0; i < n; i++) {
            ListNode p = new ListNode();
            p.data = Math.random();
            head.next = p;
            head = p;
        }
        head.next = null;
    }

    /**
     * des: 清空链表
     * @param
     */
    private void clearNode(ListNode head){
        ListNode p , q;
        p = head.next;
        while (p != null){
            q = p.next;
            p.next = null;
            p = q;
        }
        head.next = null;
    }
}
