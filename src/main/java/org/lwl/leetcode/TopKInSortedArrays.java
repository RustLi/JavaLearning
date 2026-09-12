package org.lwl.leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
/**
 * @author: RustLi
 * @date: 2026/8/24
 * @description:
 *
 * 我们把规矩缩小：假设有 **3 个降序数组**，每个数组有 **4 个元素**，我们要从中找出 **Top 3（最大的 3 个数）**。
 *
 * ---
 *
 * ### 示例数据
 *
 * * **数组 A**：`[10, 8, 5, 2]`
 * * **数组 B**：`[12, 7, 4, 1]`
 * * **数组 C**：`[9,  6, 3, 0]`
 *
 * 我们的目标是找出这 12 个数里最大的 3 个数（显然应该是 `12, 10, 9`）。
 *
 * ---
 *
 * ### 图解执行过程
 *
 * 维护一个容量大小上限为 **3** 的**最小堆**。
 *
 * #### 步骤 1：用第 1 个数组（A）初始化最小堆
 *
 * 取数组 A 前 3 个元素 `10, 8, 5` 放入堆中：
 *
 * * **堆内容**：`[5, 8, 10]`
 * * **堆顶（最小值）**：`5`
 *
 * ---
 *
 * #### 步骤 2：处理第 2 个数组（B：`[12, 7, 4, 1]`）
 *
 * * **检查 `12**`：
 * * `12 > 堆顶(5)`，说明 `5` 淘汰了！
 * * 弹出 `5`，压入 `12`。
 * * **堆更新为**：`[8, 10, 12]`，当前**堆顶变为了 `8**`。
 *
 *
 * * **检查 `7**`：
 * * `7 <= 堆顶(8)`！
 * * **触发剪枝（关键优化）**：因为数组 B 是降序的，7 都不够大，7 后面的 `4` 和 `1` 肯定更小，**直接跳过数组 B 剩下的元素**！
 *
 *
 *
 * ---
 *
 * #### 步骤 3：处理第 3 个数组（C：`[9, 6, 3, 0]`）
 *
 * * **检查 `9**`：
 * * `9 > 堆顶(8)`，说明 `8` 淘汰了！
 * * 弹出 `8`，压入 `9`。
 * * **堆更新为**：`[9, 10, 12]`，当前**堆顶变为了 `9**`。
 *
 *
 * * **检查 `6**`：
 * * `6 <= 堆顶(9)`！
 * * **触发剪枝**：6 比堆顶小，后面更小的 `3` 和 `0` 不用看了，直接跳出！
 *
 *
 *
 * ---
 *
 * ### 最终结果
 *
 * 遍历结束，堆里的 3 个数就是：`[9, 10, 12]`，这就是全局最大的 3 个数！
 *
 * ---
 *
 * ### 为什么这个算法极快？
 *
 * 在上面的例子中，我们一共只比较了 **5 个数字**（A的10,8,5；B的12,7；C的9,6），剩下的 7 个数字（4, 1, 3, 0 等）被**剪枝逻辑直接忽略**了。
 *
 * 扩展到 20 个 500 元素的数组找 Top 500 时，绝大多数数组看前几个元素就会触发剪枝，效率非常高。
 *
 **/

public class TopKInSortedArrays {

    /**
     * 从 20 个降序排列的数组中找出前 500 大的数
     *
     * @param arrays 20 个已按降序排列的数组
     * @param k      需要找出的前 K 大元素数量 (例如 500)
     * @return 包含前 K 大元素的数组 (内部无序，若需有序可额外 sort)
     */
    public static int[] findTopK(int[][] arrays, int k) {
        // 创建容量为 k 的最小堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        // 1. 先将第 1 个数组的前 k 个元素放入堆中
        int[] firstArray = arrays[0];
        int initialCount = Math.min(k, firstArray.length);
        for (int i = 0; i < initialCount; i++) {
            minHeap.offer(firstArray[i]);
        }

        // 2. 遍历剩余的数组
        for (int i = 1; i < arrays.length; i++) {
            int[] currentArray = arrays[i];
            
            for (int val : currentArray) {
                // 核心剪枝：如果当前值小于等于堆顶，由于数组是降序的，
                // 后面的元素必然更小，直接跳出当前数组的循环
                if (val <= minHeap.peek()) {
                    break;
                }

                // 当前值比堆顶大：弹出堆顶（抛弃较小值），将当前值压入堆
                minHeap.poll();
                minHeap.offer(val);
            }
        }

        // 3. 将堆中的 k 个元素导出到结果数组中
        int[] result = new int[minHeap.size()];
        int index = 0;
        while (!minHeap.isEmpty()) {
            result[index++] = minHeap.poll();
        }

        return result;
    }

    public static void main(String[] args) {
        // 模拟 20 个大小为 500 的降序数组
        int numArrays = 20;
        int arraySize = 500;
        int k = 500;
        int[][] arrays = new int[numArrays][arraySize];

        // 填充测试数据（降序）
        for (int i = 0; i < numArrays; i++) {
            for (int j = 0; j < arraySize; j++) {
                // 生成一些有交集的随机降序数据
                arrays[i][j] = 100000 - (j * 10) - i;
            }
        }

        long startTime = System.nanoTime();
        int[] topK = findTopK(arrays, k);
        long endTime = System.nanoTime();

        System.out.println("成功找到 Top " + topK.length + " 个元素。");
        System.out.println("耗时: " + (endTime - startTime) / 1e6 + " ms");
        
        // 如果需要结果从大到小有序输出，可以对结果做一次排序或反转
        Arrays.sort(topK);
        System.out.println("Top 500 中的最小值（堆顶）：" + topK[0]);
        System.out.println("Top 500 中的最大值：" + topK[topK.length - 1]);
    }
}