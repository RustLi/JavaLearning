package org.lwl.algorithms.leetcode.code.str;

import java.util.Arrays;

/**
 *
 *@author  lwl
 *@date 2024/11/12
 *@description
 *
 * 前缀和是一种非常有用的技术，特别是在处理数组或列表的子区间求和问题时。通过预先计算每个位置的前缀和，可以在常数时间内回答任意子区间的和，从而大大降低算法的时间复杂度。以下是前缀和的基本概念和使用方法：
 * 基本概念
 * 前缀和：对于一个给定的数组 arr，其前缀和数组 prefixSum 定义为 prefixSum[i] = arr[0] + arr[1] + ... + arr[i]。
 * 作用：通过前缀和数组，可以快速计算任意子区间的和。例如，子区间 [i, j] 的和可以通过 prefixSum[j] - prefixSum[i-1] 快速得到（注意边界条件）。
 */
public class PrefixSum {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int[] prefixSum = computePrefixSum(arr);
        System.out.println("前缀和数组: " + Arrays.toString(prefixSum));

        subArray();
    }


    /**
     * 计算前缀和
     **/
    public static int[] computePrefixSum(int[] arr) {
        int n = arr.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = arr[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }
        return prefixSum;
    }

    public static void subArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] prefixSum = PrefixSum.computePrefixSum(arr);
        int i = 1, j = 3;
        int sum = subarraySum(prefixSum, i, j);
        System.out.println("子区间 [" + i + ", " + j + "] 的和: " + sum);
    }

    /**
     * 计算子区间
     **/
    public static int subarraySum(int[] prefixSum, int i, int j) {
        if (i == 0) {
            return prefixSum[j];
        }
        return prefixSum[j] - prefixSum[i - 1];
    }

}
