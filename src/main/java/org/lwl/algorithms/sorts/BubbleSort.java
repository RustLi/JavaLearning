package org.lwl.algorithms.sorts;

public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped; // 用于标记是否发生交换

        // 外层循环控制遍历次数
        for (int i = 0; i < n - 1; i++) {
            swapped = false; // 每次遍历前重置标记

            // 内层循环比较相邻元素并交换
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1); // 交换相邻元素
                    swapped = true; // 标记发生交换
                }
            }

            // 如果未发生交换，说明数组已经有序，提前退出
            if (!swapped) {
                break;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        bubbleSort(arr);
        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

}
