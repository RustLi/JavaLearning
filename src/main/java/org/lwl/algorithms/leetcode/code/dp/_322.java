package org.lwl.algorithms.leetcode.code.dp;

import java.util.Arrays;

/**
 * @date: 2021/4/8
 * @author: lwl
 * @description:
 *
 *
 * 322. 零钱兑换
 *
 *  给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 你可以认为每种硬币的数量是无限的。
 *  示例 1：
 *
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * 示例 2：
 *
 * 输入：coins = [2], amount = 3
 * 输出：-1
 * 示例 3：
 *
 * 输入：coins = [1], amount = 0
 * 输出：0
 * 示例 4：
 *
 * 输入：coins = [1], amount = 1
 * 输出：1
 * 示例 5：
 *
 * 输入：coins = [1], amount = 2
 * 输出：2
 *  
 *
 * 提示：
 *
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 231 - 1
 * 0 <= amount <= 104
 *
 */
public class _322 {

    public static void main(String[] args) {
        _322 mm = new _322();
        int[] coins = {1,2,5};
        int ret = mm.coinChange(coins,11);
        System.out.println(ret);
    }

    int[] memo;
    public int coinChange(int[] coins, int amount) {
        memo = new int[amount + 1];
//        Arrays.fill(memo, -2);
        for (int i = 0, length = memo.length; i < length; i++) {
            memo[i] = -2;
        }
//        return dp(coins,amount);

        return  dp2(coins,amount);
    }

    //带备忘录
    private int dp(int[] coins, int amount){
        if(amount == 0) return 0;
        if(amount < 0) return -1;

        //如果计算过，不再计算，降低时间复杂度o(n)；
        if(memo[amount] != -2) return memo[amount];

        int ret = Integer.MAX_VALUE;
        for(int coin : coins){
            //子问题
            int subProblem = dp(coins, amount - coin);
            //子问题无解的话跳过
            if(subProblem == -1) continue;
            ret = Math.min(ret, subProblem + 1);
        }
        memo[amount] =  (ret == Integer.MAX_VALUE) ? -1 : ret;
        return memo[amount];
    }

    /**
     * des: 自底而上
     * @param
     */
    public int dp2(int[] coins ,int amount){
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        dp[0] = 0;
        //所有状态取值
        for (int i = 0, length = dp.length; i < length; i++) {
            for (int coin: coins) {
                //子问题无解
                if (i - coin < 0) continue;
                //状态转移
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }

        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }
}
