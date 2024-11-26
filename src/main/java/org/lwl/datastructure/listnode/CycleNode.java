package org.lwl.datastructure.listnode;

/**
 * @author: lwl
 * @date: 2022/8/2
 * @description:  1. 判断是否有环：快慢指针相遇，说明有环；
 *                2. 如果有环，计算环的起点：先采用快慢指针，如果没环，返回null；
 *                   否则相遇时任一指针指向头节点，快慢同一速度行动，再次相遇即为环的起点；
 **/
public class CycleNode {
    public static void main(String[] args) {

    }

    /**
     * 链表是否成环：如果快慢相遇，fast超过了slow一圈
     **/
    private static boolean hasCycle(ListNode head) {
        // 快慢指针初始化指向 head
        ListNode slow = head, fast = head;
        // 快指针走到末尾时停止
        while (fast != null && fast.next != null) {
            // 慢指针走一步，快指针走两步
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指针相遇，说明含有环
            if (slow == fast) {
                return true;
            }
        }
        // 不包含环
        return false;
    }

    /**
     *
     * 头节点  --- 相遇点 ： k
     * 头节点 -- 起点 ： k-m
     * 起点 --- 相遇点 ： m
     * 相遇点 --- 起点： k-m
     **/
    private static ListNode detectCycle(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        // 上面的代码类似 hasCycle 函数
        if (fast == null || fast.next == null) {
            // fast 遇到空指针说明没有环
            return null;
        }

        // 重新指向头结点
        slow = head;
        // 快慢指针同步前进，相交点就是环起点
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

}
