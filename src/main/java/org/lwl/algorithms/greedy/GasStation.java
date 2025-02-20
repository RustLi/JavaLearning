package org.lwl.algorithms.greedy;


/**
 *
 *@author lwl
 *@date 2025/1/24
 *@description
 *
 * 加油站在 LeetCode 上是一个经典的贪心算法问题。问题描述如下：有 n 个加油站，第 i 个加油站的汽油量为 gas[i]，从第 i 个加油站开到下一个加油站需要消耗的汽油量为 cost[i]。
 * 你需要从某个加油站出发，环绕一圈回到出发加油站。如果可以完成环绕一圈，返回出发加油站的编号；否则返回 -1。假设出发时油箱是空的。
 */
public class GasStation {

    /**
     * totalGas 和 totalCost：用于记录所有加油站的总汽油量和总消耗量。如果总汽油量小于总消耗量，那么无法完成环绕一圈。
     * start：记录出发加油站的索引。
     * tank：记录当前油箱中的汽油量。
     * 循环遍历加油站：
     * 更新 totalGas 和 totalCost。
     * 更新 tank，即当前油箱中的汽油量。
     * 如果 tank 小于 0，说明从当前出发点无法到达下一个加油站，因此更新 start 为 i + 1，并重置 tank 为 0。
     * 返回结果：如果 totalGas 小于 totalCost，返回 -1；否则返回 start。
     **/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        int start = 0;
        int tank = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            tank += gas[i] - cost[i];

            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }

        return (totalGas < totalCost) ? -1 : start;
    }

    public static void main(String[] args) {
        GasStation solution = new GasStation();
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        int result = solution.canCompleteCircuit(gas, cost);
        System.out.println("Starting gas station index: " + result);
    }
}