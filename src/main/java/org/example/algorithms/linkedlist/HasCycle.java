package algorithms.linkedlist;

import data_structure.listnode.ListNode;

import java.util.List;

/**
 * @date: 2020/4/10
 * @author: lwl
 * @description: 判断链表是否有环
 */
public class HasCycle {

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        head.next= new ListNode<>(1);
        head.next.next = new ListNode<>(2);
        head.next.next.next = new ListNode<>(3);

        System.out.println(hasCycle(head));
    }

    private static boolean hasCycle(data_structure.listnode.ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) return true;
        }
        return false;
    }

    /**
     * des: 已知链表中含有环，返回这个环的起始位置
     *
     * 采用数学方法分析：
     *     fast走2步，slow走1步；
     *    假设slow走了k步，那么fast一共走了2k步；
     *    假设相遇点离环点起点是m步（另一个方向k-m步），那么head离起点距离是k-m；
     *    根据以上，算出环点长度为 (k-m) + m = k；
     *
     *    此时将fast或slow指向head，两者以相同速度行走（都走了k-m），相遇点即为所求；
     */
    private static ListNode detectCycle(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        // 上面的代码类似 hasCycle 函数
        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * des: 寻找链表的倒数第 k 个元素
     *
     * 让快指针先走 k 步，然后快慢指针开始同速前进。这样当快指针走到链表末尾 null 时，慢指针所在的位置就是倒数第 k 个链表节点（为了简化，假设 k 不会超过链表长度）
     */
    private static ListNode findKNode(ListNode head,int k){
        ListNode slow, fast;
        slow = fast = head;
        while (k-- > 0)
            fast = fast.next;

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
