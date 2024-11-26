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
//        int ret = binarySearch.binarySearch(intput, key);
//        System.out.println(ret);

        int target = binarySearch.right_bound(intput,key);
        System.out.println("target = " + target);
    }

    private int binarySearch(int[] a, int key) {
        int low, mid, high;
        low = 0;//最小下标
        high = a.length - 1;//最大小标
        while (low <= high) {
            mid = low + (high - low) / 2;//折半下标
            if (key < a[mid]) {
                high = mid - 1;//关键字比折半值小，则最大下标调成折半下标的前一位
            } else if (key > a[mid]) {
                low = mid + 1; //关键字比折半值大，则最小下标调成折半下标的下一位
            } else {
                return mid; //关键字和折半值相等时返回折半下标
            }
        }
        return -1;
    }

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
     * 左侧边界，如[1，2，3，3，4，5，7] target = 3，返回 2
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
        // 此时 target 比所有数都大，返回 -1
        if (left == nums.length) return -1;
        // 判断一下 nums[left] 是不是 target
        return nums[left] == target ? left : -1;
    }

    /**
     * 右侧边界，[1，2，2，3，4，5，7] target = 3，返回 3
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
        // 此时 left - 1 索引越界
        if (left - 1 < 0) return -1;
        // 判断一下 nums[left] 是不是 target
        return nums[left - 1] == target ? (left - 1) : -1;
    }

    /**
     * des: 递归实现
     * @param
     */
    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1);
    }

    private static int rank(int key, int[] a, int lo, int hi) { //如果key存在于a[]中，它的索引不会小于lo且不会大于hi
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) return rank(key, a, lo, mid - 1);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi);
        else return mid;
    }


    private int bSearch(int[] in, int target){
        if (in == null || in.length <=0){
            return -1;
        }
        int left = 0, right = in.length - 1;
        while (left <= right){
            int mid = left + (right - left)/2;
            if (in[mid] < target){
                left = mid + 1;
            }else if (in[mid] > target){
                right = mid - 1;
            }else if (in[mid] == target){
                //查找直接返回
                return mid;

                //查找左侧边界
//                right = mid - 1;

                //查找右侧边界
//                left = mid + 1;
            }
        }
        return -1;
    }
}
