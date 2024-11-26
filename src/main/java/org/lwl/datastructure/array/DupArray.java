package org.lwl.datastructure.array;

import org.lwl.datastructure.listnode.ListNode;

/**
 * @author: lwl
 * @date: 2022/8/22
 * @description: Leetcode-26 原地删除重复数组，不采用新的空间，
 *          【0-1-1-2】 --- 【0-1-2】
 **/
public class DupArray {
    public static void main(String[] args) {

    }

    /**
     * 一个快一个慢，保证慢的那个数组都是无重复的，前提是数组是排好序的
     **/
    int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            //快指针不与前面慢指针相等，移动慢指针
            if (nums[fast] != nums[slow]) {
                slow++;
                // 维护 nums[0..slow] 无重复
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // 数组长度为索引 + 1
        return slow + 1;
    }

    /**
     * 删除链表中的重复数据
     **/
    ListNode deleteDuplicates(ListNode head) {
        if (head == null){
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast != null) {
            if (fast.data != slow.data) {
                // nums[slow] = nums[fast];
                slow.next = fast;
                // slow++;
                slow = slow.next;
            }
            // fast++
            fast = fast.next;
        }
        //断开与后面重复元素的连接
        slow.next = null;
        return head;
    }

    /**
     * 删除某些特定元素
     **/
    int removeElement(int[] nums, int val) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
