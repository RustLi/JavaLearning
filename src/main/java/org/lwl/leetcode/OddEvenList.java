package org.lwl.leetcode;

/**
 * LeetCode 328 - 奇偶链表
 * 将所有奇数索引节点和偶数索引节点分别分组，保持原有相对顺序，
 * 将偶数索引节点组连接到奇数索引节点组之后。
 * 时间复杂度 O(n)，空间复杂度 O(1)
 */
public class OddEvenList {

    // 链表节点定义
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode oddEvenList(ListNode head) {
        // 空链表或单节点，无需处理
        if (head == null || head.next == null) {
            return head;
        }

        // odd: 奇数链尾指针   even: 偶数链尾指针
        // evenHead: 保存偶数链头，循环结束后接到奇数链尾部
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        // 偶数链存在下一个节点时，还能为奇数链再拆出一个节点
        while (even != null && even.next != null) {
            odd.next = even.next;  // 奇数链跨一个节点往下接
            odd = odd.next;
            even.next = odd.next;  // 偶数链跨一个节点往下接
            even = even.next;
        }

        odd.next = evenHead; // 偶数链接到奇数链之后
        return head;
    }

    // ============ 测试用例 ============
    public static void main(String[] args) {
        OddEvenList solution = new OddEvenList();

        // 示例 1: 1 -> 2 -> 3 -> 4 -> 5 => 1 -> 3 -> 5 -> 2 -> 4
        printList("示例1", solution.oddEvenList(buildList(1, 2, 3, 4, 5)));

        // 示例 2: 2 -> 1 -> 3 -> 5 -> 6 -> 4 -> 7 => 2 -> 3 -> 6 -> 7 -> 1 -> 5 -> 4
        printList("示例2", solution.oddEvenList(buildList(2, 1, 3, 5, 6, 4, 7)));

        // 边界：空链表
        printList("空链表", solution.oddEvenList(null));

        // 边界：单节点
        printList("单节点", solution.oddEvenList(buildList(1)));

        // 边界：两个节点
        printList("两节点", solution.oddEvenList(buildList(1, 2)));
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
