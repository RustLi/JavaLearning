package org.lwl.algorithms.sorts;

public class QuickSort {

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high); // 获取分区点
            quickSort(arr, low, pivotIndex - 1);  // 递归排序左半部分
            quickSort(arr, pivotIndex + 1, high); // 递归排序右半部分
        }
    }


    /**
     * 以最后一个数为基准，找到所有比最后一个数小的，交换元素。最后将基准元素放到交换后的索引 +1 的
     *
     *   比如 {8,9,5,1,10, 7}，第一次排序后的数据为 5,1,8,9,10,7, 最后一个swap后 5，1，7，9，10，8
     **/
    private static int partition(int[] arr, int low, int high) {
        // 选择基准元素（这里选择最后一个元素作为基准）
        int pivot = arr[high];
        int i = low - 1; // i 是小于基准元素的区域的右边界

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j); // 将小于基准元素的元素交换到左侧
            }
        }
        swap(arr, i + 1, high); // 将基准元素放到正确的位置
        return i + 1; // 返回基准元素的最终位置
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
//        int[] arr = {10, 7, 8, 9, 1, 5};
        int[] arr = {8,9,5,1,10, 7};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
