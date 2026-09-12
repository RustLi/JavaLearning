package org.lwl.leetcode.code.node;

/**
 * @author: RustLi
 * @date: 2026/8/27
 * @description: 反转链表 II（LeetCode 92）——迭代解法（头插法）
 *
 *  给定单链表的头指针 head 和两个整数 left、right（left <= right，从 1 开始计数），
 *  反转从位置 left 到位置 right 的链表节点，返回反转后的链表头。
 *
 *  核心思想：三指针 + 头插法。
 *    1) 加一个 dummy 节点，避免 left == 1 时没有前驱的特殊处理。
 *    2) pre 先走到 left 位置的前一个节点，作为反转段的"锚点"。
 *    3) cur 固定指向区间内第一个节点；循环 right - left 次，
 *       每次把 cur.next 摘出来，头插到 pre 之后。
 *    经过 right - left 次头插，区间内的节点顺序就被完全颠倒，且首尾自动衔接正确。
 *
 *  demo 示例：1 -> 2 -> 3 -> 4 -> 5，反转 [2, 4]
 *    pre 定位到 1，cur = 2，执行 right-left = 2 次头插：
 *      第1次：摘出 3 头插到 1 后 -> 1 -> 3 -> 2 -> 4 -> 5
 *      第2次：摘出 4 头插到 1 后 -> 1 -> 4 -> 3 -> 2 -> 5
 */
public class ReverseBetweenIterative {

    public static void main(String[] args) {
        ReverseBetweenIterative solution = new ReverseBetweenIterative();

        // 例1：经典区间，反转中间一段 -> 1 -> 4 -> 3 -> 2 -> 5
        ListNode root1 = build(new int[]{1, 2, 3, 4, 5});
        System.out.print("例1 反转[2,4]：");
        print(solution.reverseBetween(root1, 2, 4));

        // 例2：反转整个链表 [1,3] -> 3 -> 2 -> 1
        ListNode root2 = build(new int[]{1, 2, 3});
        System.out.print("例2 反转[1,3]：");
        print(solution.reverseBetween(root2, 1, 3));

        // 例3：反转从头部开始的一段 [1,2] -> 2 -> 1 -> 3 -> 4 -> 5
        ListNode root3 = build(new int[]{1, 2, 3, 4, 5});
        System.out.print("例3 反转[1,2]：");
        print(solution.reverseBetween(root3, 1, 2));

        // 例4：区间只有一个节点 [3,3]，链表不变
        ListNode root4 = build(new int[]{1, 2, 3, 4, 5});
        System.out.print("例4 反转[3,3]：");
        print(solution.reverseBetween(root4, 3, 3));
    }

    /**
     * des: 反转从 left 到 right 的链表区间（迭代 + 头插法）
     *
     * dummy 哨兵节点使 left == 1 时也有稳定的前驱；
     * pre 停在区间前一个节点；cur 始终指向区间内第一个节点，
     * 每轮把 cur.next 摘下来插到 pre 之后，做 right - left 次即完成反转。
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 1. pre 走到 left 位置的前一个节点
        ListNode pre = dummy;
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }

        // 2. 头插法反转区间：cur 固定不动，把其后的节点逐个头插到 pre 后
        ListNode cur = pre.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = cur.next;   // 待头插的节点
            cur.next = next.next;       // ① 先摘出 next，跳过它
            next.next = pre.next;       // ② next 指向区间当前头（实现反转）
            pre.next = next;            // ③ next 成为新区间头
        }

        return dummy.next;
    }

    private static ListNode build(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int num : nums) {
            cur.next = new ListNode(num);
            cur = cur.next;
        }
        return dummy.next;
    }

    private static void print(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(" -> ");
            }
            head = head.next;
        }
        System.out.println(sb);
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
