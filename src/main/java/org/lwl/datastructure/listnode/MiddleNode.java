package data_structure.listnode;

/**
 * @author: lwl
 * @date: 2022/8/2
 * @description: leetCode-876：链表的中间节点，如果中间有2个，返回后面那个
 *      思路：双指针，从头开始，fast走2步，slow走1步，fast走到终点的时候，slow刚好在中间
 **/
public class MiddleNode {
    public static void main(String[] args) {
        ListNode<Integer> head = new ListNode<>(1);
        head.next= new ListNode<>(2);
        head.next.next = new ListNode<>(3);
        head.next.next.next = new ListNode<>(4);

        ListNode retNode = middleNode(head);
        ListNode.printNode(retNode);
    }


    private static ListNode middleNode(ListNode head) {
        // 快慢指针初始化指向 head
        ListNode slow = head, fast = head;
        // 快指针走到末尾时停止
        while (fast != null && fast.next != null) {
            //  慢指针走一步，快指针走两步
            slow = slow.next;
            fast = fast.next.next;
        }
        // 慢指针指向中点
        return slow;
    }
}
