package org.lwl.datastructure.listnode;
/**
 * @date: 2019/12/17
 * @author: lwl
 * @description: 链表定义
 */
public class ListNode<T> {
    //定义数据域和指针域
    public T val;
    public ListNode next;

    public ListNode(T val) {
        this.val = val;
    }

    public ListNode() {
    }

    public static void printNode(ListNode head){
        ListNode p = head;
        while (p != null){
            System.out.println(p.val);
            p = p.next;
        }
    }
}
