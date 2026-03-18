package org.lwl.algorithms.bag;

/**
 *
 * @author lwl
 * @date 2026/3/16
 * @desc 0-1背包问题
 *
 *
 * 题目：背包容量为W，物品有N种，每种物品有wt的重量和val的价值，求解放入背包中物品总价值最大是多少？
 *
 *  N=3,W=4
 *  wt=[2,1,4]
 *  val=[4,2,3]
 *
 *  输出：6。重量最大为4，所以放入前两个物品，总价值为6。
 *
 *  dp[i][w]: 对于前i个物品，背包容量为w，最大价值为dp[i][w]。
 *  举例说明：对于给定的一系列物品中，对前2个物品，当背包容量为2时，最大价值为dp[2][2]。
 *
 *  dp[0][..]=dp[..][0]=0，没有物品或者没有空间的时候，最大价值就是0
 */
public class Base01Bag {

    public static void main(String[] args) {
        int N = 3;
        int W = 4;
        int[] wt = new int[]{2,1,4};
        int[] val = new int[]{4,2,3};
        int result = knapsack(wt, val, N, W);
        System.out.println("最大价值为：" + result);
    }

    private static int knapsack(int[] wt, int[] val, int N, int W){
        int[][] dp = new int[N+1][W+1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= W; j++) {
                //j 是内层循环的变量，表示当前考虑的背包总容量，从 1 到 W，
                // 代表背包在不同剩余容量下的状态，因此对应“背包剩余容量”。

                //表示第 i 个物品的重量大于当前背包剩余容量 j，此时这个物品放不进背包。
                if(wt[i-1] > j){
                    // 放不进去，只能不放
                    dp[i][j] = dp[i-1][j];
                }else {
                    // 装入或者不装入背包，择优
                    dp[i][j] = Math.max(dp[i-1][j], val[i-1] + dp[i-1][j-wt[i-1]]);
                }
            }
        }
        //遍历dp数据
        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < W+1; j++) {
                System.out.print(dp[i][j] + " ");
            }
        }

        return dp[N][W];
    }
}
