package org.lwl.javacommon.algorithm.divideconquer;

/**
 * 归并排序 - 分治算法经典示例
 *
 * 分治三步骤：
 *   1. 分解（Divide）：将数组从中间拆分为左右两个子数组
 *   2. 解决（Conquer）：递归地对左右子数组分别排序
 *   3. 合并（Combine）：将两个有序子数组合并为一个有序数组
 *
 * 时间复杂度：O(n log n)，所有情况均稳定
 * 空间复杂度：O(n)，merge 步骤需要辅助数组
 **/
public class MergeSortExample {

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        printArray(arr, "排序前");
        mergeSort(arr, 0, arr.length - 1);
        printArray(arr, "排序后");
    }

    /**
     * 递归拆分：将 arr[left..right] 分成两半分别排序，再合并
     */
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return; // 递归终止：子数组只剩一个元素
        }
        int mid = (left + right) / 2;
        // 分解：递归处理左半部分
        mergeSort(arr, left, mid);
        // 分解：递归处理右半部分
        mergeSort(arr, mid + 1, right);
        // 合并：将两个有序子数组合并
        merge(arr, left, mid, right);
    }

    /**
     * 合并两个有序子数组 arr[left..mid] 和 arr[mid+1..right]
     */
    static void merge(int[] arr, int left, int mid, int right) {
        int len = right - left + 1;
        int[] temp = new int[len]; // 辅助数组

        int i = left;      // 左子数组指针
        int j = mid + 1;   // 右子数组指针
        int k = 0;         // 辅助数组写入指针

        // 双指针比较，将较小值写入辅助数组
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 处理左子数组剩余元素
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 处理右子数组剩余元素
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        // 将辅助数组写回原数组
        System.arraycopy(temp, 0, arr, left, len);
    }

    static void printArray(int[] arr, String label) {
        System.out.print(label + ": ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
