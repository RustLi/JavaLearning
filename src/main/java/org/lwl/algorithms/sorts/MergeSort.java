package org.lwl.algorithms.sorts;

import java.util.Arrays;
/**
 * @description:
 * 1.采用递归的方法，分成两部分;
 * 2.每部分直到无法再分，然后合成有序序列;
 * 3.将两个有序序列合成一个有序序列
 * @author: RustLi
 * @date: 2019-03-12
 **/
public class MergeSort {

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        int[] temp = new int[arr.length]; // 临时数组用于合并操作
        sort(arr, temp, 0, arr.length - 1);
    }

    // 递归分割
    private static void sort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2; // 防溢出写法
        sort(arr, temp, left, mid);      // 左半部分排序
        sort(arr, temp, mid + 1, right); // 右半部分排序
        merge(arr, temp, left, mid, right); // 合并有序子数组
    }

    // 合并有序区间 [left, mid] 和 [mid+1, right]
    private static void merge1(int[] arr, int[] temp, int left, int mid, int right) {
        System.arraycopy(arr, left, temp, left, right - left + 1); // 复制到临时数组

        int i = left;      // 左子数组起始指针
        int j = mid + 1;   // 右子数组起始指针
        int k = left;      // 合并目标指针

        while (i <= mid && j <= right) {
            arr[k++] = (temp[i] <= temp[j]) ? temp[i++] : temp[j++];
        }

        // 处理剩余元素
        while (i <= mid) arr[k++] = temp[i++];
        while (j <= right) arr[k++] = temp[j++];
    }


    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        //复制到临时数组
        for (int i = left; i <= right ; i++) {
            temp[i] = arr[i];
        }
        //双指针合并两个有序数组
        int i = left;      // 左子数组起始指针
        int j = mid + 1;   // 右子数组起始指针
        for (int k = left; k <= right ; k++) {
            if (i == mid + 1){
                //左边已合并完成
                arr[k] = temp[j++];
            }else if (j == right + 1){
                //右边已合并完成
                arr[k] = temp[i++];
            }else if (temp[i] > temp[j]){
                //比较左右两个数组的元素，将较小的元素放入目标数组中
                arr[k] = temp[j++];
            }else {
                arr[k] = temp[i++];
            }
        }
    }


    // 测试
    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        mergeSort(arr);
        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        // 输出: 1 5 7 8 9 10
    }
}
