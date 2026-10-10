package org.lwl.algorithms.sorts;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序 —— 两种分区（partition）实现对比
 *
 * <p>快速排序的核心只有两步：
 * <ol>
 *   <li><b>分区</b>：选一个基准值 pivot，把数组重排成「左边都 <= pivot，右边都 >= pivot」，并返回 pivot 的最终下标 p；</li>
 *   <li><b>递归</b>：对 [low, p-1] 和 [p+1, high] 两个子区间重复上述过程。</li>
 * </ol>
 * 分区的写法决定了整个算法的行为，工程上有两大流派：
 *
 * <h3>1. 双向分区（Hoare 分区，1962 年原始版本）</h3>
 * 左右两个指针从两端向中间夹逼：右指针向左找「小于 pivot」的元素，左指针向右找「大于 pivot」的元素，
 * 找到一对就交换。指针相遇时，把 pivot 换到相遇点。
 * <ul>
 *   <li>交换次数少（一趟约 n/3 次），平均只需扫描约一半元素；</li>
 *   <li>常数小，是 JDK {@code DualPivotQuicksort} 一脉的思想来源；</li>
 *   <li>缺点：遇到大量重复元素时划分会严重倾斜（见文末总结）。</li>
 * </ul>
 *
 * <h3>2. 单分区（Lomuto 分区）</h3>
 * 只有一个扫描指针 j 从左往右走，用 i 维护「小于 pivot 的区域」的右边界，
 * arr[j] < pivot 就把 arr[j] 换到该区域末尾。最后把 pivot 换到 i+1 位置。
 * <ul>
 *   <li>代码极短、含义直观（"小于区"像一个慢慢长大的雪球）；</li>
 *   <li>缺点：每个元素都要与 pivot 比较且交换更频繁，常数比 Hoare 大；</li>
 *   <li>注意：Lomuto 不能用 pivot 取最左元素 + 单向扫描的朴素形式对有序数组排序，否则退化（见总结）。</li>
 * </ul>
 */
public class QuickSort {

    /**
     * 统一入口，默认使用双向分区（Hoare）。
     * 时间复杂度：平均 O(n log n)，最坏 O(n^2)；空间复杂度：递归栈 O(log n) ~ O(n)。
     */
    public static void quickSort(int[] arr, int low, int high) {
        quickSortHoare(arr, low, high);
    }

    // ==========================================================================================
    // 方案一：双向分区（Hoare 分区）
    // ==========================================================================================

    /**
     * 双向分区的快速排序。
     *
     * <p><b>关键点：分区函数返回的是"两指针相遇点"，该位置天然满足「左边 <= pivot <= 右边」，
     * 所以递归区间是 [low, p-1] 与 [p+1, high]。不能写成 Hoare 原始论文里那种"返回 j、
     * 左半区间是 [low, j]"的形式，否则当 p == low 时会无限递归。</b>
     */
    public static void quickSortHoare(int[] arr, int low, int high) {
        if (low >= high) { // 区间只剩 0 或 1 个元素，天然有序
            return;
        }
        int pivotIndex = partitionHoare(arr, low, high);
        quickSortHoare(arr, low, pivotIndex - 1);
        quickSortHoare(arr, pivotIndex + 1, high);
    }

    /**
     * 双向分区：以最左元素为基准，左右指针夹逼交换。
     *
     * <pre>
     * 以 arr = {8, 9, 5, 1, 10, 7} 为例，pivot = arr[low] = 8，i = 0，j = 5：
     *
     * ┌──────┬───────────────────────┬─────┬─────┬───────────┬────────────────┐
     * │ 轮次 │ 指针动作              │  i  │  j  │   操作    │    数组变化    │
     * ├──────┼───────────────────────┼─────┼─────┼───────────┼────────────────┤
     * │  1   │ j 左移：arr[5]=7 < 8  │  0  │  5  │     —     │ 8  9  5  1 10 7│
     * │  1   │ i 右移：arr[1]=9 > 8  │  1  │  5  │ swap(1,5) │ 8  7  5  1 10 9│
     * ├──────┼───────────────────────┼─────┼─────┼───────────┼────────────────┤
     * │  2   │ j 左移：arr[4]=10>=8  │  1  │  3  │     —     │ 8  7  5  1 10 9│
     * │  2   │ j 左移：arr[3]=1  < 8 │  1  │  3  │     —     │ 8  7  5  1 10 9│
     * │  2   │ i 右移：i 追上 j      │  3  │  3  │ 退出循环  │ 8  7  5  1 10 9│
     * └──────┴───────────────────────┴─────┴─────┴───────────┴────────────────┘
     *
     * 循环结束时 i == j == 3，且 arr[3] = 1 <= pivot（因为右指针先走，停下时必然指向 < pivot 的元素）。
     * 最后 swap(low, i) 把基准放到相遇点：
     *
     *      8  7  5  1 10  9          →          1  7  5  8 10  9
     *      ↑        ↑                            ↑        ↑
     *     low       i                          左区<=8   pivot 归位  右区>=8
     *
     * 返回 3，即下半段递归 [0,2]、[4,5]。
     * </pre>
     *
     * <p><b>为什么内层判断用 &gt;= / &lt;= 而不是 &gt; / &lt;？</b>
     * 若写成 {@code while (i < j && arr[j] > pivot) j--;} 和 {@code while (i < j && arr[i] < pivot) i++;}，
     * 当 arr[i] == arr[j] == pivot 时两个指针都不动，swap 后位置不变，外层 {@code while (i < j)} 永远成立 → 死循环。
     * 用 &gt;= / &lt;= 保证"等于 pivot 的元素不参与交换"，指针一定前进。
     */
    private static int partitionHoare(int[] arr, int low, int high) {
        int pivot = arr[low];   // 基准取最左元素（取最右需对称改写，注意相遇点归属会变）
        int i = low;
        int j = high;

        while (i < j) {
            // 右指针向左，找第一个「小于 pivot」的元素
            while (i < j && arr[j] >= pivot) {
                j--;
            }
            // 左指针向右，找第一个「大于 pivot」的元素
            while (i < j && arr[i] <= pivot) {
                i++;
            }
            if (i < j) {
                swap(arr, i, j); // 把大的甩到右边、小的甩到左边
            }
        }
        // i == j，此位置属于「小于等于 pivot」的一侧，把基准换过来
        swap(arr, low, i);
        return i;
    }

    // ==========================================================================================
    // 方案二：单分区（Lomuto 分区）
    // ==========================================================================================

    /**
     * 单分区的快速排序。
     */
    public static void quickSortLomuto(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partitionLomuto(arr, low, high);
        quickSortLomuto(arr, low, pivotIndex - 1);
        quickSortLomuto(arr, pivotIndex + 1, high);
    }

    /**
     * 以最后一个数为基准，找到所有比最后一个数小的，交换元素。最后将基准元素放到交换后的索引 +1 的
     *
     *   low=0, high=5, pivot=arr[5]=7，初始 i=-1：
     *
     * ┌─────┬────────┬─────────────┬─────┬───────────┬──────────────┐
     * │  j  │ arr[j] │ arr[j] < 7? │  i  │   操作    │   数组变化   │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 0   │ 8      │ ✗           │ -1  │ 不动      │ 8 9 5 1 10 7 │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 1   │ 9      │ ✗           │ -1  │ 不动      │ 8 9 5 1 10 7 │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 2   │ 5      │ ✓           │ 0   │ swap(0,2) │ 5 9 8 1 10 7 │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 3   │ 1      │ ✓           │ 1   │ swap(1,3) │ 5 1 8 9 10 7 │
     * ├─────┼────────┼─────────────┼─────┼───────────┼──────────────┤
     * │ 4   │ 10     │ ✗           │ 1   │ 不动      │ 5 1 8 9 10 7 │
     * └─────┴────────┴─────────────┴─────┴───────────┴──────────────┘
     *
     *
     * 循环结束：i = 1，此时 arr[0..1] = {5,1} 都小于 7，arr[2..4] = {8,9,10} 都不小于 7。
     *
     * 最后一步 swap(i+1, high) 即 swap(2, 5)：
     *
     * 5 1 8 9 10 7
     *         ↑  ↑
     *        i+1  high(pivot)
     *
     * 交换后 → 5 1 7 9 10 8，返回 i+1 = 2。
     *
     *
     **/
    private static int partitionLomuto(int[] arr, int low, int high) {
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
        // ---------- 1. 同一组数据，两种分区结果对比 ----------
        int[] origin = {8, 9, 5, 1, 10, 7};
        int[] byHoare = origin.clone();
        int[] byLomuto = origin.clone();

        quickSortHoare(byHoare, 0, byHoare.length - 1);
        quickSortLomuto(byLomuto, 0, byLomuto.length - 1);

        System.out.println("原始数组  : " + Arrays.toString(origin));
        System.out.println("双向分区  : " + Arrays.toString(byHoare));
        System.out.println("单分区    : " + Arrays.toString(byLomuto));

        // ---------- 2. 随机对拍，验证两种实现都正确 ----------
        Random random = new Random(42);
        int rounds = 1000;
        for (int round = 0; round < rounds; round++) {
            // 刻意混入重复值（range 偏小）与负值，覆盖 >= / <= 等值分支
            int[] src = new int[random.nextInt(60)];
            for (int k = 0; k < src.length; k++) {
                src[k] = random.nextInt(20) - 10;
            }
            int[] expected = src.clone();
            Arrays.sort(expected);

            int[] h = src.clone();
            int[] l = src.clone();
            quickSortHoare(h, 0, h.length - 1);
            quickSortLomuto(l, 0, l.length - 1);

            if (!Arrays.equals(h, expected) || !Arrays.equals(l, expected)) {
                System.out.println("对拍失败，输入：" + Arrays.toString(src));
                return;
            }
        }
        System.out.println(rounds + " 组随机对拍全部通过 ✔");
    }
}
