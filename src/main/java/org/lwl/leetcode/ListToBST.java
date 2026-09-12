package org.lwl.leetcode;

import org.lwl.datastructure.listnode.ListNode;
import org.lwl.datastructure.tree.TreeNode;

/**
 * LeetCode 109 - 有序链表转换二叉搜索树
 * 提交时将整个类名改为 Solution
 */
class ListToBST {

    // ============ 方法一：快慢指针（推荐先掌握） ============
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode((Integer) head.val);

        ListNode prev = null, slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null; // 断开左半

        TreeNode root = new TreeNode((Integer) slow.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(slow.next);
        return root;
    }
}
