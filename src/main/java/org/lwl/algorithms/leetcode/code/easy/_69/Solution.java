package org.lwl.algorithms.leetcode.code.easy._69;

/**
 * @date: 2019/12/20
 * @author: lwl
 * @description: 求平方根
 */
public class Solution {
    public static void main(String[] args) {
        int input1 = 9;
        System.out.println("input: " +input1+ " mySqrtWithMid: " + sqrtWithMid(input1));
        System.out.println(sqrtWithMid1(input1));
    }

    /**
     * des:  二分法
     * @param
     */
    private static int sqrtWithMid1(int x){
        if (x < 1){
            return 0;
        }
        int left = 0;
        int right = x;
        int result = 0;
        while (left <= right){
            int mid = left + ((right - left) >> 1);
            if (mid < 1){
                return 1;
            }
            if (mid > x/mid){
                right = mid - 1;
            }else {
                left = mid + 1;
                result = mid;
            }
        }
        return result;
    }

    /**
     * des:  二分法
     * @param
     */
    private static int sqrtWithMid(int x){
        if(x < 1) {
            return 0;
        }
        int left = 0;
        int right = x;
        while(true) {
            int mid = left + ((right - left) >> 1);
            if (mid < 1) {
                return 1;
            }
            if (mid > x / mid) {
                right = mid;
            } else {
                if (mid + 1 > x/(mid + 1)) {
                    return mid;
                }
                left = mid + 1;
            }
        }
    }

    /**
     * des: 牛顿迭代法
     * @param
     */
    private int sqrtWithIterator(int x){
        long n = x;
        while (n * n > x) {
            n = (n + x / n) >> 1;
        }
        return (int) n;
    }
}
