package org.lwl.leetcode;

/**
 * LeetCode 206 - 反转链表
 * 给定单链表的头节点 head，反转链表，返回反转后的头节点。
 *
 * 核心思想（迭代法 / 双指针）：
 *  用三个指针 prev / cur / nxt 遍历链表，每步把 cur.next 指向前一个节点
 *  实现"局部反转"，然后整体向后平移，直到 cur 为 null，prev 即为新头。
 *  关键在于反转前必须先保存后继节点（nxt），否则会断链丢失后面的节点。
 *
 *  示例：1 -> 2 -> 3 -> 4 -> 5  =>  5 -> 4 -> 3 -> 2 -> 1
 *
 *  递归法：从尾到头逆向反转，先递归到尾节点拿到 newHead，
 *  回溯时执行 head.next.next = head 指回前驱，再断开 head.next。
 *
 * 时间复杂度 O(n)，空间复杂度：迭代 O(1)、递归 O(n)（递归栈深度）
 */
public class ReverseLinkedList {

    // 链表节点定义
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * des: 迭代法反转链表（双指针），空间复杂度 O(1)
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;  // 前驱节点
        ListNode cur = head;   // 当前要反转的节点
        while (cur != null) {
            ListNode nxt = cur.next; // 先保存后继，防止断链
            cur.next = prev;         // 局部反转：当前节点指向前驱
            prev = cur;              // prev 前移
            cur = nxt;               // cur 前移
        }
        return prev; // 原链表的尾节点成为新头
    }

    /**
     * des: 递归法反转链表，从尾到头逆向反转
     */
    public ListNode reverseListRecursive(ListNode head) {
        // 空链表 或 尾节点（head.next == null），直接返回自身
        if (head == null || head.next == null) {
            return head;
        }
        // 先反转后面的链表，newHead 始终是原链表的尾节点
        ListNode newHead = reverseListRecursive(head.next);
        // 让后继节点指回自己：head.next（原后继）的 next 指向 head
        head.next.next = head;
        // 断开自己与后继的连接，避免成环
        head.next = null;
        return newHead;
    }

    // ============ 测试用例 ============
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();

        // 示例 1: 1 -> 2 -> 3 -> 4 -> 5 => 5 -> 4 -> 3 -> 2 -> 1
        printList("迭代法-示例1", solution.reverseList(buildList(1, 2, 3, 4, 5)));

        // 示例 2: 1 -> 2 => 2 -> 1
        printList("迭代法-示例2", solution.reverseList(buildList(1, 2)));

        // 边界：空链表
        printList("迭代法-空链表", solution.reverseList(null));

        // 边界：单节点
        printList("迭代法-单节点", solution.reverseList(buildList(1)));

        // 递归法验证
        printList("递归法-示例1", solution.reverseListRecursive(buildList(1, 2, 3, 4, 5)));
    }

    private static ListNode buildList(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    private static void printList(String tag, ListNode head) {
        System.out.print(tag + ": ");
        StringBuilder sb = new StringBuilder();
        ListNode cur = head;
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) {
                sb.append(" -> ");
            }
            cur = cur.next;
        }
        System.out.println(sb);
    }
}
