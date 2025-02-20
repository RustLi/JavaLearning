package org.lwl.algorithms.sorts;

/**
 * @description: 折半查找必须保证数组本身是有序的
 * @author: RustLi
 * @date: 2019-03-12
 **/
public class BinarySearch {

    public static void main(String[] args) {
        int[] intput = {1, 2, 3, 3, 6, 7, 7};
        int key = 3;
        BinarySearch binarySearch = new BinarySearch();
        int target = binarySearch.right_bound(intput, key);
        System.out.println("target = " + target);
    }

    /**
     * 直接查找
     **/
    int binary_search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if(nums[mid] == target) {
                // 直接返回
                return mid;
            }
        }
        // 直接返回
        return -1;
    }

    /**
     * 返回左边界 如 [1, 2, 3, 3, 3, 3, 4] target = 3 返回 2
     **/
    int left_bound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 别返回，锁定左侧边界
                right = mid - 1;
            }
        }
        // 判断 target 是否存在于 nums 中
        if (left < 0 || left >= nums.length) {
            return -1;
        }
        // 判断一下 nums[left] 是不是 target
        return nums[left] == target ? left : -1;
    }

    /**
     * 返回右边界 如 [1, 2, 3, 3, 3, 3, 4] target = 3 返回 5
     **/
    int right_bound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 别返回，锁定右侧边界
                left = mid + 1;
            }
        }
        // 由于 while 的结束条件是 right == left - 1，且现在在求右边界
        // 所以用 right 替代 left - 1 更好记
        if (right < 0 || right >= nums.length) {
            return -1;
        }
        return nums[right] == target ? right : -1;
    }
}
