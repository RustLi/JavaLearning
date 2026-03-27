package org.lwl.javacommon.algorithm.divideconquer;

/**
 * 快速排序 - 分治算法经典示例
 *
 * 分治三步骤：
 *   1. 分解（Divide）：选取 pivot（取中点元素），将数组分为小于/等于/大于 pivot 的三部分
 *   2. 解决（Conquer）：递归地对 pivot 左右两侧子数组分别排序
 *   3. 合并（Combine）：原地排序，无需额外合并步骤
 *
 * 时间复杂度：平均 O(n log n)，最坏 O(n²)（选中点 pivot 可大幅降低概率）
 * 空间复杂度：O(log n)，递归栈空间
 **/
public class QuickSortExample {

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        printArray(arr, "排序前");
        quickSort(arr, 0, arr.length - 1);
        printArray(arr, "排序后");
    }

    /**
     * 递归排序：对 arr[low..high] 进行原地快速排序
     */
    static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return; // 递归终止：子数组为空或只剩一个元素
        }
        // 分解：分区，获取 pivot 最终落点
        int pivotIndex = partition(arr, low, high);
        // 解决：递归处理 pivot 左侧
        quickSort(arr, low, pivotIndex - 1);
        // 解决：递归处理 pivot 右侧
        quickSort(arr, pivotIndex + 1, high);
    }

    /**
     * 分区：选取中点元素为 pivot，将小于等于 pivot 的元素移到左侧
     * 返回 pivot 最终所在的下标
     */
    static int partition(int[] arr, int low, int high) {
        // 选中点元素为 pivot，可降低已排序数组退化为 O(n²) 的概率
        int mid = (low + high) / 2;
        swap(arr, mid, high); // 将 pivot 临时移到末尾

        int pivot = arr[high];
        int i = low - 1; // 小于等于 pivot 区域的右边界

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        // 将 pivot 放到最终位置
        swap(arr, i + 1, high);
        return i + 1;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
