package org.lwl.datastructure.listnode;

import java.util.LinkedList;

public class SingleLinkedList {
    private int size;//单链表的大小
    private Node head;//头节点

    public SingleLinkedList(){
        size = 0;
        head = null;
    }

    //单链表的节点
    private class Node{
        private Object data;
        private Node next;
        public Node(Object data){
            this.data = data;
        }
    }

    public static void main(String[] args){
        singleLinkedTest();
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

    //头部添加数据
    public void addHead(Object data){
        Node newHead = new Node(data);
        if (0 == size){
            head = newHead;
        }else {
            newHead.next = head;
            head = newHead;
        }
        size++;
    }

    //头部删除数据
    public void deleteObject(){
        head = head.next;
        size--;
    }

    //查找数据
    public Node findNode(Object data){
        Node cur = head;
        int temSize = size;
        while (temSize > 0){
            if (data.equals(cur.data)){
                return cur;
            }else {
                cur = cur.next;
            }
            temSize--;
        }
        return null;
    }

    //删除指定元素，成功返回true
    public boolean delete(Object data){
        if (size == 0){
            return false;
        }
        Node cur = head;
        Node tmpNode = head;
        //遍历找到相等的节点
        while (data != cur.data){
            if (cur.next == null){
                return false;
            }else {
                //保存当前相等的节点
                tmpNode = cur;
                cur = cur.next;//每次后移
            }
        }
        //删除的节点为头节点
        if (cur == head){
            head = cur.next;
            size--;
        }else {
            tmpNode.next = cur.next;//指针后移
            size--;
        }
        return true;
    }

    public boolean isEmpty(){
        return (size == 0);
    }

    //显示当前节点
    public void display(){
        if (size > 0){
            Node node = head;
            int tmpSize = size;
            if (tmpSize == 1){
                System.out.println(node.data);
                return;
            }
            while (tmpSize > 0){
                if (node.equals(head)){
                    System.out.println( node.data);
                }else if (node.next == null){
                    System.out.println(node.data);
                }else {
                    System.out.println(node.data);
                }
                node = node.next;
                tmpSize--;
            }
            System.out.println();
        }else {
            System.out.println("[]");
        }
    }
}
