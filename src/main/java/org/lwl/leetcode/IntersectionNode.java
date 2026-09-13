package org.lwl.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 160 - 相交链表（提交时将类名改为 Solution）
 * <p>
 * 题目：给你两个单链表的头节点 headA 和 headB，找出并返回两个单链表相交的起始节点；
 * 若两个链表不存在相交节点，返回 null。
 * <p>
 * 关键理解：相交指的是"同一个节点对象"（内存地址相同），而不是值相等。
 * 因此一旦两条链表相交，从相交点开始往后必定共用同一段后缀（Y 字形），不可能再分开。
 * <p>
 * 核心思路一：双指针"互换赛道"（推荐，最优解）
 * 指针 p1 从 A 出发、走完 A 后接着从 B 头出发；指针 p2 从 B 出发、走完 B 后接着从 A 头出发。
 * 两者都走 m + n 步，且都恰好把"非公共部分"走完，于是会在相交点相遇：
 * - 若相交：p1、p2 的路程都是 (a + c + b)、(b + c + a)，相等 → 在交点相遇；
 * - 若不相交：p1、p2 同时走到 null → 退出循环并返回 null。
 * <p>
 * 核心思路二：长度差对齐
 * 先各求长度，让长链表指针先走 |lenA - lenB| 步，之后两指针同步前进，首次相等即为交点。
 * <p>
 * 核心思路三：哈希表记录访问过的节点，以节点对象为 key（Object 默认按地址散列）。
 * <p>
 * 时间复杂度 O(m + n)：三种解法最多遍历两条链表常数次；
 * 空间复杂度：双指针 / 长度差 O(1)，哈希表 O(m)。
 * <p>
 * 易错点：比较节点必须用 == 判断引用相等，用 equals 或 val 相等会得到错误答案。
 */
public class IntersectionNode {

    /** 链表节点定义（本地调试用，LeetCode 提交时使用平台自带的 ListNode） */
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // ============ 方法一：双指针"互换赛道"（推荐，代码最短、空间 O(1)） ============

    /**
     * des: p1 走 A → B，p2 走 B → A，二者行程长度相同，相遇处即为交点
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode p1 = headA;
        ListNode p2 = headB;
        // 不相交时，p1、p2 会同时变成 null（都走完 m + n 步），循环自然结束并返回 null
        while (p1 != p2) {
            p1 = (p1 == null) ? headB : p1.next; // p1 走完 A 就换到 B 头
            p2 = (p2 == null) ? headA : p2.next; // p2 走完 B 就换到 A 头
        }
        return p1;
    }

    // ============ 方法二：长度差对齐 ============

    /**
     * des: 让两个指针距离尾部的距离相同，再同步前进
     */
    public ListNode getIntersectionNodeByLength(ListNode headA, ListNode headB) {
        int lenA = 0;
        int lenB = 0;
        for (ListNode p = headA; p != null; p = p.next) {
            lenA++;
        }
        for (ListNode p = headB; p != null; p = p.next) {
            lenB++;
        }

        // 长链表先走差值步，使两指针到尾部距离相等
        ListNode p1 = headA;
        ListNode p2 = headB;
        int diff = lenA - lenB;
        while (diff > 0) {
            p1 = p1.next;
            diff--;
        }
        while (diff < 0) {
            p2 = p2.next;
            diff++;
        }

        // 同步前进，首次相等即交点；不相交则同时走到 null
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    // ============ 方法三：哈希表（空间换时间，思路最直白） ============

    /**
     * des: 先把 A 的全部节点放入 HashSet，再遍历 B 找第一个已存在的节点
     */
    public ListNode getIntersectionNodeByHashSet(ListNode headA, ListNode headB) {
        Set<ListNode> visited = new HashSet<>();
        for (ListNode p = headA; p != null; p = p.next) {
            visited.add(p);
        }
        for (ListNode p = headB; p != null; p = p.next) {
            // 命中说明该节点在 A 中出现过，即交点
            if (visited.contains(p)) {
                return p;
            }
        }
        return null;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        IntersectionNode solution = new IntersectionNode();

        // ---------- 用例 1：相交（LeetCode 官方示例） ----------
        ListNode common = buildList(8, 4, 5);       // 公共后缀
        ListNode headA = buildList(4, 1);
        tailOf(headA).next = common;
        ListNode headB = buildList(5, 6, 1);
        tailOf(headB).next = common;

        printList("A", headA);
        printList("B", headB);
        System.out.println("  方法一(双指针) 交点 -> " + valOf(solution.getIntersectionNode(headA, headB)));
        System.out.println("  方法二(长度差) 交点 -> " + valOf(solution.getIntersectionNodeByLength(headA, headB)));
        System.out.println("  方法三(哈希表) 交点 -> " + valOf(solution.getIntersectionNodeByHashSet(headA, headB)));

        // ---------- 用例 2：不相交 ----------
        System.out.println();
        ListNode listC = buildList(2, 6, 4);
        ListNode listD = buildList(1, 5);
        printList("C", listC);
        printList("D", listD);
        System.out.println("  方法一(双指针) 交点 -> " + valOf(solution.getIntersectionNode(listC, listD)));

        // ---------- 用例 3：头节点即为交点（A 整体是 B 的前缀） ----------
        System.out.println();
        ListNode headE = buildList(1, 2, 3);
        ListNode headF = buildList(7);
        tailOf(headF).next = headE; // B 的尾部直接接上 A，交点就是 A 的头节点
        printList("E", headE);
        printList("F", headF);
        System.out.println("  方法一(双指针) 交点 -> " + valOf(solution.getIntersectionNode(headE, headF)));

        // ---------- 演示：双指针"互换赛道"的行走轨迹 ----------
        System.out.println();
        trace("A", "B", buildList(1, 2), buildList(9), buildList(3, 4));
    }

    /**
     * des: 打印双指针每一步的位置，直观展示"两者路程相同"的原理
     * 形如 A: 1 -> 2 -> 3 -> 4，B: 9 -> 3 -> 4，公共后缀 3 -> 4
     */
    private static void trace(String nameA, String nameB, ListNode prefixA, ListNode prefixB, ListNode common) {
        ListNode headA = prefixA;
        tailOf(headA).next = common;
        ListNode headB = prefixB;
        tailOf(headB).next = common;

        System.out.println("行走轨迹演示：");
        printList("  " + nameA, headA);
        printList("  " + nameB, headB);

        ListNode p1 = headA;
        ListNode p2 = headB;
        int step = 1;
        while (p1 != p2) {
            System.out.printf("  第 %d 步：p1 = %-4s p2 = %-4s%n", step, valOf(p1), valOf(p2));
            p1 = (p1 == null) ? headB : p1.next;
            p2 = (p2 == null) ? headA : p2.next;
            step++;
        }
        // 退出循环有两种可能：都在交点停下，或都变成 null（不相交）
        if (p1 == null) {
            System.out.println("  第 " + step + " 步：p1 = null p2 = null -> 两指针同时为空，不相交");
        } else {
            System.out.printf("  第 %d 步：p1 = %-4s p2 = %-4s -> 相遇，即为交点！%n", step, valOf(p1), valOf(p2));
        }
    }

    /** des: 用给定元素构造一条普通单链表 */
    private static ListNode buildList(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    /** des: 取链表尾节点，用于把公共后缀接上去 */
    private static ListNode tailOf(ListNode head) {
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        return cur;
    }

    private static String valOf(ListNode node) {
        return node == null ? "null" : String.valueOf(node.val);
    }

    private static void printList(String tag, ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) {
            sb.append(p.val);
            if (p.next != null) {
                sb.append(" -> ");
            }
        }
        System.out.println(tag + ": " + sb);
    }
}
