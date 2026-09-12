package org.lwl.leetcode;

/**
 * LeetCode 109 - 有序链表转换二叉搜索树
 * 给定一个按升序排序的单链表，将其转换为平衡二叉搜索树（AVL）。
 *
 * 思路：快慢指针找中点 + 分治递归
 * 时间复杂度 O(n log n)，空间复杂度 O(log n)（递归栈）
 */
public class SortedListToBST {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 解法一：快慢指针找中点
     * 每次找到链表中点作为根节点，左右两部分递归构建子树
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        // 快慢指针找中点，pre 指向 slow 的前驱
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // 断开左半部分
        if (prev != null) {
            prev.next = null;
        }

        // slow 就是中点
        TreeNode root = new TreeNode(slow.val);
        root.left = sortedListToBST(head);        // 左半部分
        root.right = sortedListToBST(slow.next);  // 右半部分

        return root;
    }

    // ============ 解法二：中序遍历优化（O(n) 时间） ============
    // 利用中序遍历顺序和链表顺序一致的特性，一边遍历一边构建

    private ListNode current;

    public TreeNode sortedListToBST2(ListNode head) {
        current = head;
        int size = getSize(head);
        return buildInorder(0, size - 1);
    }

    private TreeNode buildInorder(int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;

        // 中序遍历：先左子树
        TreeNode leftChild = buildInorder(left, mid - 1);

        // 当前链表节点作为根
        TreeNode root = new TreeNode(current.val);
        current = current.next;

        // 再右子树
        root.left = leftChild;
        root.right = buildInorder(mid + 1, right);

        return root;
    }

    private int getSize(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }

    // ============ 测试辅助方法 ============

    /** 构建链表 */
    static ListNode buildList(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int n : nums) {
            cur.next = new ListNode(n);
            cur = cur.next;
        }
        return dummy.next;
    }

    /** 层序遍历打印树（便于验证） */
    static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("null");
            return;
        }
        java.util.Queue<TreeNode> q = new java.util.LinkedList<>();
        q.offer(root);
        java.util.List<String> res = new java.util.ArrayList<>();
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                res.add("null");
            } else {
                res.add(String.valueOf(node.val));
                q.offer(node.left);
                q.offer(node.right);
            }
        }
        // 去掉尾部 null
        int i = res.size() - 1;
        while (i >= 0 && res.get(i).equals("null")) {
            res.remove(i--);
        }
        System.out.println(res);
    }

    /** 中序遍历（验证是否为 BST） */
    static void inorder(TreeNode root, java.util.List<Integer> list) {
        if (root == null) return;
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }

    public static void main(String[] args) {
        SortedListToBST sol = new SortedListToBST();

        // 示例 1: [-10, -3, 0, 5, 9]
        int[] nums1 = {-10, -3, 0, 5, 9};
        ListNode head1 = buildList(nums1);
        TreeNode root1 = sol.sortedListToBST(head1);
        System.out.print("示例 1 层序遍历：");
        printTree(root1);
        java.util.List<Integer> inorder1 = new java.util.ArrayList<>();
        inorder(root1, inorder1);
        System.out.println("中序遍历（应为升序）：" + inorder1);

        // 示例 2: [] — 空链表
        System.out.print("示例 2（空链表）：");
        printTree(sol.sortedListToBST(null));

        // 解法二测试
        System.out.println("\n--- 解法二（O(n) 中序构建）---");
        ListNode head2 = buildList(nums1);
        TreeNode root2 = sol.sortedListToBST2(head2);
        System.out.print("层序遍历：");
        printTree(root2);
        java.util.List<Integer> inorder2 = new java.util.ArrayList<>();
        inorder(root2, inorder2);
        System.out.println("中序遍历（应为升序）：" + inorder2);

        // 多节点测试
        System.out.println("\n--- 1~7 节点测试 ---");
        int[] nums3 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root3 = sol.sortedListToBST(buildList(nums3));
        System.out.print("层序遍历：");
        printTree(root3);
    }
}
