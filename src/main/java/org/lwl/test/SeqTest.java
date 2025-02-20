package org.lwl.test;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class SeqTest {

    public static void main(String[] args) {
        List<Long> liveIds = Lists.newArrayList(3L, 4L, 1L, 2L, 31L, 231L);

        // 对输入的自然数进行排序（可选步骤，为了后续结果有序）
        liveIds.sort(Long::compareTo);

        System.out.println("输入的数字列表：" +  liveIds.toString());

        // 获取所有排列
        List<List<Long>> result = new ArrayList<>();
        List<Long> current = new ArrayList<>();

        backtrack(liveIds, 0, current, result);

        // 打印所有排列
        for (List<Long> subsequence : result) {
            System.out.println(subsequence);
        }
    }

    /**
     * 回溯算法生成子序列
     * @param nums 输入的有序数字列表
     * @param start 当前递归的起始位置
     * @param current 当前列表
     * @param result 结果列表
     */
    private static void backtrack(List<Long> nums, int start, List<Long> current, List<List<Long>> result) {
        // 如果current非空，则它是一个有效的子序列
        if (!current.isEmpty()) {
            result.add(new ArrayList<>(current));
        }

        for (int i = start; i < nums.size(); i++) {
            // 选择当前数字
            current.add(nums.get(i));

            // 继续生成后续的子序列
            backtrack(nums, i + 1, current, result);

            // 回溯：撤销选择
            current.remove(current.size() - 1);
        }
    }
}
