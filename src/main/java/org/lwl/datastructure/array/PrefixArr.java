package org.lwl.datastructure.array;

/**
 * @author: lwl
 * @date: 2022/8/24
 * @description: 前缀和数组，常见于一些数组求和类的题；数组不被修改的情况下，频繁的查询某区间的累加和；
 *              方案：定义一个全局的数组，存储前N项的和；
 **/
public class PrefixArr {

    // 前缀和数组
    private int[] preSum;

    /* 输入一个数组，构造前缀和 */
    public PrefixArr(int[] nums) {
        // preSum[0] = 0，便于计算累加和
        preSum = new int[nums.length + 1];
        // 计算 nums 的累加和
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
    }

    /* 查询闭区间 [left, right] 的累加和 */
    public int sumRange(int left, int right) {
        return preSum[right + 1] - preSum[left];
    }
}
