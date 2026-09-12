package org.lwl.leetcode.code.node;

/**
 * @author: RustLi
 * @date: 2026/8/27
 * @description: 反转链表 II（LeetCode 92）——递归解法
 *
 *  给定单链表的头指针 head 和两个整数 left、right（left <= right，从 1 开始计数），
 *  反转从位置 left 到位置 right 的链表节点，返回反转后的链表头。
 *
 *  核心思想：把问题拆成两个递归子问题。
 *    1) reverseN(head, n)：反转以 head 为起点的前 n 个节点（206 递归版的扩展，
 *       增加一个 successor 记录反转段尾部要连接的"后继节点"）。
 *    2) reverseBetween(head, left, right)：left 每递归一层就减 1，
 *       直到 left == 1 时，问题就退化为"从当前节点起反转前 right 个节点"，即调用 reverseN。
 *    left 之前的节点保持不变，逐层把 head.next 指回递归结果即可。
 *
 *  demo 示例：1 -> 2 -> 3 -> 4 -> 5，反转 [2, 4]
 *    1) reverseBetween(1, 2, 4)  ->  head.next = reverseBetween(2, 1, 3)  结果段新头 = 4
 *    2) reverseBetween(2, 1, 3)  ->  left==1，调用 reverseN(2, 3)
 *    3) reverseN(2, 3) 递归反转 2 -> 3 -> 4 为 4 -> 3 -> 2，successor = 5
 *    4) 拼回原链表：1 -> 4 -> 3 -> 2 -> 5
 */
public class ReverseBetween {

    // 记录反转区间之后的"后继节点"，供 reverseN 回溯时把反转段尾部接上
    private ListNode successor = null;

    public static void main(String[] args) {
        ReverseBetween solution = new ReverseBetween();

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
     * des: 反转从 left 到 right 的链表区间
     *
     * left == 1 说明当前节点就是反转段的头，直接反转前 right 个节点；
     * 否则递归推进到下一个节点，同时区间左右边界各减 1。
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // base case：从当前 head 开始反转前 right 个节点
        if (left == 1) {
            return reverseN(head, right);
        }
        // 递归推进：head 本身不动，反转其后的 (left-1, right-1) 区间
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }

    /**
     * des: 反转以 head 为起点的前 n 个节点，返回反转后的新头
     */
    private ListNode reverseN(ListNode head, int n) {
        // base case：只剩最后一个节点，记录其后继（反转段要接的节点），并作为新头返回
        if (n == 1) {
            successor = head.next;
            return head;
        }
        // 先反转后面 n-1 个节点，last 是反转后的新头
        ListNode last = reverseN(head.next, n - 1);
        // 把当前节点接到反转段之后：head 成为反转段的最后一个节点
        head.next.next = head;
        head.next = successor;
        return last;
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

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    /**
     * 头插法
     *
     *    以 1 → 2 → 3 → 4 → 5，反转 [2, 4]：
     *
     * 初始：dummy → 1 → [2 → 3 → 4] → 5，pre = 1，cur = 2
     *
     * 第 1 次头插（摘出 next = 3）：
     * ① cur.next = next.next     →  2 → 4        （摘掉 3）
     * ② next.next = pre.next     →  3 → 2        （3 指向原区间头 2）
     * ③ pre.next = next          →  1 → 3        （3 插到 pre 后）
     * 结果：1 → 3 → 2 → 4 → 5
     *
     * 第 2 次头插（摘出 next = 4）：
     * ① cur.next = next.next     →  2 → 5        （摘掉 4）
     * ② next.next = pre.next     →  4 → 3        （4 指向当前区间头 3）
     * ③ pre.next = next          →  1 → 4        （4 插到 pre 后）
     * 结果：1 → 4 → 3 → 2 → 5   ✓
     */
    public ListNode reverseBetween1(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0,head);

        //1.pre走到第left-1节点
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        //2.curr指向第left节点
        ListNode curr = pre.next;

        //3.头插法：每次把curr.next插到pre之后
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;//要移动的节点
            curr.next = next.next;//跳过移动节点，连接后缀
            next.next = pre.next;//next指向当前pre的后缀
            pre.next = next;//pre指向next，为新的头部
        }
        return dummy.next;
    }
}
