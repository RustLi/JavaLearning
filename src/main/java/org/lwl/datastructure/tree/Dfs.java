package org.lwl.datastructure.tree;

import java.util.*;

public class Dfs {

    /**
     * 前序遍历：根 → 左 → 右
     */
    public List<Integer> preorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            res.add(cur.val);
            // 先压右，后压左，保证左先被弹出
            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);
        }
        return res;
    }


    /**
     * 中序遍历：左 → 根 → 右
     */
    public List<Integer> inorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            // 一路往左走到头
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            res.add(cur.val);
            // 转向右子树
            cur = cur.right;
        }
        return res;
    }

    /**
     * 后序遍历：左 → 右 → 根
     */
    public List<Integer> postorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        stack1.push(root);

        while (!stack1.isEmpty()) {
            TreeNode cur = stack1.pop();
            stack2.push(cur);
            // 先左、再右入栈1
            if (cur.left != null) stack1.push(cur.left);
            if (cur.right != null) stack1.push(cur.right);
        }
        // 弹出stack2得到后序
        while (!stack2.isEmpty()) {
            res.add(stack2.pop().val);
        }
        return res;
    }

    /**
     * 后序遍历：左 → 右 → 根
     */
    public List<Integer> postorder1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);                  // 先收集：根 → 右 → 左
            // 注意压栈顺序与弹出顺序相反：先压左、后压右 → 弹出时先右后左
            if (node.left != null)  stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        Collections.reverse(result);               // 反转 → 左 → 右 → 根
        return result;
    }


}
