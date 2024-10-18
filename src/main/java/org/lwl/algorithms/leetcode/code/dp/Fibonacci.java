package org.lwl.algorithms.leetcode.code.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2021/4/8
 * @author: lwl
 * @description:
 *
 * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：
 *
 * F(0) = 0,   F(1) = 1
 * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
 * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 */
public class Fibonacci {

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
//        System.out.println(fibonacci.fib(10));
//        System.out.println(fibonacci.numWays(10));
        System.out.println(fibonacci.fib2(2));
//        System.out.println(fibonacci.numWays1(2));
    }


    public int fib(int n) {
        int[] memo = new int[n + 1];
        return fibWithMemo(memo,n);
    }

    /**
     * des: 带备忘录，时间复杂度从o(2n次幂) 到 o(n)
     */
    public int fibWithMemo(int[] memo, int n){
        if(n == 0 || n ==1 ){
            return 1;
        }
        //增加一个备忘录，防止重复计算；时间复杂度o(n)；
        if(memo[n] != 0) {
            return memo[n] % 1000000007;
        }
        memo[n] = fibWithMemo(memo, n - 1) + fibWithMemo(memo, n - 2);
        return memo[n] % 1000000007;
    }


    /**
     * des: 自底向上，递归反向  动态规划，时间复杂度o(1)
     */
    public int fib2(int n) {
        int a = 0, b = 1, sum;
        for(int i = 0; i <= n; i++){
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;
    }


    public int numWays1(int n) {
        if (n <= 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int a = 1;
        int b = 2;
        int temp = 0;
        for (int i = 3; i <= n; i++) {
            temp = (a + b)% 1000000007;
            a = b;
            b = temp;
        }
        return temp;
    }


    //使用哈希map，充当备忘录的作用
    Map<Integer, Integer> tempMap = new HashMap();
    public int numWays(int n) {
        // n = 0 也算1种
        if (n == 0 || n == 1) {
            return 1;
        }
        //先判断有没计算过，即看看备忘录有没有
        if (tempMap.containsKey(n)) {
            //备忘录有，即计算过，直接返回
            return tempMap.get(n);
        } else {
            int temp = (numWays(n - 1) + numWays(n - 2)) % 1000000007;
            // 备忘录没有，即没有计算过，执行递归计算,并且把结果保存到备忘录map中，对1000000007取余（这个是leetcode题目规定的）
            tempMap.put(n, temp);
            return temp;
        }
    }
}
