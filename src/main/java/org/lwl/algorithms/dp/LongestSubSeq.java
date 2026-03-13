package org.lwl.algorithms.dp;

import java.util.Arrays;

/**
 *
 *@author lwl
 *@date 2025/2/6
 *@description  最长子序列问题
 *
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 *
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 *
 * 示例 2：
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4

 * 示例 3：
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * 提示：
 *
 * 1 <= nums.length <= 2500
 * -104 <= nums[i] <= 104
 * 进阶：
 *
 * 你能将算法的时间复杂度降低到 O(n log(n)) 吗?
 *
 *
 *   dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
 *
 *  举例：
 *   nums = [1,4,3,4,2,3]
 *   dp = [1,2,2,3,2,?]
 *  已知dp[0..4]的值，要求求解dp[5]的值，dp[5]表示以num[5]=3结尾的最长递增子序列长度。
 *   找出小于nums[5]的元素为num[0]和num[4]，必须是小于的num[5]的num[i]
 *   对比对应的dp[0]和dp[4], dp[5]等于dp[0]或者dp[4]的较大值+1, 代码如下:
 *
 *   for (int j = 0; j < i; j++) {
 *     if (nums[i] > nums[j]) {
 *         dp[i] = Math.max(dp[i], dp[j] + 1);
 *     }
 *   }
 */
public class LongestSubSeq {

    public static void main(String[] args) {
        LongestSubSeq mm = new LongestSubSeq();
        int[] nums = {1,4,3,4,2,3};
//        int ret = mm.lengthOfLIS(nums);
//        System.out.println(ret);
        int ret = mm.lengthOfLIS1(nums);
        System.out.println(ret);
//        int ret = mm.lengthOfLISBinarySearch(nums);
//        System.out.println(ret);

//        int aaa = 0;
//        for (int i = 0; i < 5; i++) {
//            int right = aaa;
//            if (i % 2 == 0){
//                aaa++;
//            }
//            System.out.println("aaa = " + aaa + ", right = " + right);
//        }
    }

    /**
     * 标准解法，时间复杂度为o(n^2)
     **/
    public int lengthOfLIS(int[] nums) {
        // 定义：dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度
        int[] dp = new int[nums.length];
        // base case：dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int res = 0;
        for (int j : dp) {
            res = Math.max(res, j);
        }
        return res;
    }

    
    /**
     * O(n log n) 解法：贪心 + 二分
     * tails[i] = 长度为 i+1 的递增子序列的最小结尾元素
     * 
     * 思路简述
        维护数组 tails：tails[i] = 当前见过的、长度为 i+1 的递增子序列里，最小的结尾元素。
        tails 严格递增，因此可以对「要插入/替换的位置」做二分。
        对每个 num：
        在 tails[0..len) 中二分出第一个 tails[i] >= num 的位置 pos；
        若 pos == len，说明可以接成更长的序列，则 tails[len++] = num；
        否则用 num 覆盖 tails[pos]，保持「同样长度下结尾尽量小」的贪心。
        最终 LIS 长度 = len。



        在你给的例子上跑一遍
        nums = [1, 4, 3, 4, 2, 3]：

       步骤 num	tails (len)	说明
        1	1	[1] (1)	空序列，直接放
        2	4	[1, 4] (2)	4 > 1，接在后面
        3	3	[1, 3] (2)	替换 4，保持长度 2 结尾更小
        4	4	[1, 3, 4] (3)	接在后面
        5	2	[1, 2, 4] (3)	替换 3
        6	3	[1, 2, 3] (3)	替换 4
        得到长度为 3，与 O(n²) DP 一致。

     */
    public int lengthOfLISBinarySearch(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] tails = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int pos = firstGte(tails, len, num);
            tails[pos] = num;
            if (pos == len) len++;
        }
        return len;
    }

    /** 在 tails[0..len) 中二分找第一个 >= target 的下标 */
    private int firstGte(int[] tails, int len, int target) {
        int lo = 0, hi = len;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (tails[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }


    private int lengthOfLIS1(int[] nums) {
        int[] top = new int[nums.length];
        int piles = 0; // 0 为化始初数堆牌
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];
            int left = 0, right = piles;
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            if (left == piles) {
                piles++;
            }
            top[left] = poker;
        }
        return piles;
    }   
}
