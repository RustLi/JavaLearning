package data_structure.listnode;

/**
 * @author: lwl
 * @date: 2022/8/1
 * @description: 链表的倒数第 k 个节点
 *      思路：p1先走k步，p2和p1一起走n-k步，p2停在n-k+1的节点上（1为head节点），正好是倒数第k个节点
 **/
public class ReverseKNode {
    public static void main(String[] args) {
        ListNode<Integer> head = new ListNode<>(1);
        head.next= new ListNode<>(2);
        head.next.next = new ListNode<>(3);
        head.next.next.next = new ListNode<>(4);

        ListNode retNode = findFromEnd(head,2);
        ListNode.printNode(retNode);
    }

    // 返回链表的倒数第 k 个节点
    private static ListNode findFromEnd(ListNode head, int k) {
        ListNode p1 = head;
        // p1 先走 k 步
        for (int i = 0; i < k; i++) {
            p1 = p1.next;
        }
        ListNode p2 = head;
        // p1 和 p2 同时走 n - k 步
        while (p1 != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        // p2 现在指向第 n - k + 1 个节点，即倒数第 k 个节点
        return p2;
    }
}
