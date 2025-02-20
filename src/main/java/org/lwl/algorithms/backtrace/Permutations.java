package org.lwl.algorithms.backtrace;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author lwl
 *@date 2025/1/21
 *@description  回溯。 1. 全排列  2. 子序列
 *
 *  result = []
 *  def backtrack(路径, 选择列表):
 *     if 满足结束条件:
 *         result.add(路径)
 *         return
 *
 *     for 选择 in 选择列表:
 *         做选择
 *         backtrack(路径, 选择列表)
 *         撤销选择
 *
 */
public class Permutations {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = permute(nums);
        for (List<Integer> permutation : result) {
            System.out.println(permutation);
        }

        List<List<Integer>> result1 = subsets(nums);
        for (List<Integer> subset : result1) {
            System.out.println(subset);
        }
    }

    /**
     * 全排列
     **/
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, current, result);
        return result;
    }

    /**
     * 全排列
     **/
    private static void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue; // 跳过已经使用的数字
            }
            used[i] = true; // 标记当前数字为已使用
            current.add(nums[i]); // 将当前数字加入当前排列
            backtrack(nums, used, current, result); // 递归构建下一个位置的排列
            current.remove(current.size() - 1); // 回溯，移除当前数字
            used[i] = false; // 标记当前数字为未使用
        }
    }

    /**
     * 计算所有的子集
     **/
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        backtrack(nums, 0, current, result);
        return result;
    }

    /**
     * 所有子序列
     **/
    private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // 每次递归调用时，当前子集都是一个有效的子集
        if (!current.isEmpty()){
            result.add(new ArrayList<>(current));
        }

        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]); // 选择当前元素
            backtrack(nums, i + 1, current, result); // 递归构建下一个位置的子集
            current.remove(current.size() - 1); // 回溯，移除当前元素
        }
    }
}