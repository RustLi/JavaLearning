package org.lwl.algorithms.sorts;

import org.lwl.algorithms.sorts.base.Utils;

public class ShellSort {

    public static void shellSort(int[] arr) {
        int n = arr.length;

        // 初始间隔（gap）为数组长度的一半，逐步缩小
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 对每个子序列进行插入排序
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                // 将 arr[i] 插入到正确的位置
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        shellSort(arr);
        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

}
