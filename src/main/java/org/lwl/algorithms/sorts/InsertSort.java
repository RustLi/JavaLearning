package org.lwl.algorithms.sorts;

import org.lwl.algorithms.sorts.base.Utils;
import java.util.Arrays;

public class InsertSort {

    public static void insertionSort(int[] arr) {
        int n = arr.length;

        // 从第二个元素开始遍历（第一个元素默认是已排序的）
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // 当前需要插入的元素
            int j = i - 1;

            // 将大于 key 的元素向后移动
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key; // 将 key 插入到正确的位置
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        insertionSort(arr);
        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
