package algorithms.leetcode.code.dp;

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
        System.out.println(fibonacci.fib(10));
    }


    public int fib(int n) {
        int[] memo = new int[n + 1];
        return fibWithMemo(memo,n);
    }

    /**
     * des: 带备忘录，时间复杂度从o(2n次幂) 到 o(n)
     * @param
     */
    public int fibWithMemo(int[] memo, int n){
        if(n == 0 || n ==1 ) return n;
        //增加一个备忘录，防止重复计算；时间复杂度o(n)；
        if(memo[n] != 0) return memo[n] % 1000000007;
        memo[n] = fibWithMemo(memo, n - 1) + fibWithMemo(memo, n - 2);
        return memo[n] % 1000000007;
    }


    /**
     * des: 自底向上，递归反向
     * @param
     */
    public int fib2(int n) {
        int a = 0, b = 1, sum;
        for(int i = 0; i < n; i++){
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;
    }

}
