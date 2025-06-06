
参考：
https://www.hello-algo.com/chapter_computational_complexity/iteration_and_recursion/#222

递归：将一个大的问题逐步分解成小的问题，小的问题和大的问题具有相同的结构
1. 递：调用自身，传入更小或更简化的参数，知道达到“终止状态”
2. 归：触发到“终止状态”后，从最深处开始逐层返回，汇聚每一层的结果


举例：求1到n的和
迭代：
```java
public int sum(int n) {
    int sum = 0;
    for (int i = 1; i <= n; i++) {
        sum += i;
    }
}
```
递归：
```java
public int recur(int n) {
    //终止条件
    if (n == 1) {
        return 1;
    }
    //递：递归调用，向下传递
    int res = recur(n - 1);
    //归：返回结果
    return n + res;
}
```
尾递归：（java编译器不支持将尾递归转换为迭代，所以依然会压栈）
尾递归是指在函数调用的最后一步，即递归调用之后，没有其他操作，即函数调用结束后，返回函数调用的结果。
```java
public int tailRecur(int n, int res) {
    if (n == 0) {
        return res;
    }
    return tailRecur(n - 1, res + n);
}
```
