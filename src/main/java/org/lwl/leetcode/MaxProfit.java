package org.lwl.leetcode;

/**
 * LeetCode 121 - 买卖股票的最佳时机（提交时将类名改为 Solution）
 * <p>
 * 题目：给定数组 prices，prices[i] 表示某支股票第 i 天的价格。只能选择「某一天」买入，
 * 并在「未来的某一个不同的日子」卖出（即恰好完成一笔交易，先买后卖，不能同日买卖）。
 * 设计算法计算所能获取的最大利润；若无法获取任何利润，返回 0。
 * <p>
 * 关键理解：一次交易 = 在 i 天买入、在 j 天卖出（i < j），利润 = prices[j] - prices[i]。
 * 所以题目本质是：在所有满足 i < j 的数对中，求 prices[j] - prices[i] 的最大值。
 * 「不能获取利润就返回 0」意味着答案下界是 0——价格单边下跌时就干脆不交易。
 * <p>
 * 核心思路一：一次遍历，边扫描边维护历史最低价（推荐，O(n) / O(1)）
 * 把「卖出日」固定为第 i 天，那么最优买入日必然是第 i 天之前价格最低的那一天。
 * 于是只需一次从左到右的扫描，维护两个变量：
 * - minPrice：截至第 i 天（含）的历史最低价，即可选的买入价；
 * - maxProfit：截至第 i 天能获得的最大利润。
 * 每走到一天，先尝试「今天卖、历史最低价买」更新答案，再用今天的价格更新 minPrice。
 * 关于两句的顺序：本题先算利润再更新 minPrice 是更贴合的语义（当天不能买卖）。
 * 若写反，当天最低时会算出一笔「同日买卖」的利润，但它恰好等于 0，而答案下界也是 0，
 * 因此本题结果不受影响；不过这个习惯在「带手续费」「需返回买入日下标」等变体里会变成真 bug，建议一开始就写对。
 * <p>
 * 核心思路二：暴力枚举（O(n^2)）
 * 双重循环枚举所有 (买入日, 卖出日) 组合取最大值。时间复杂度过不了评测，但它是理解题意最直接的写法，
 * 也便于与思路一做对照验证。
 * <p>
 * 核心思路三：转化为「最大子数组和」（Kadane，O(n) / O(1)）
 * 把相邻两天的价格差看作差分数组 diff[i] = prices[i] - prices[i-1]，
 * 那么区间 (i, j] 的利润 = diff[i+1] + ... + diff[j]，即差分数组的一个连续子段和。
 * 「最大利润」于是等价于「差分数组的最大子段和」，且子段和为负时取 0（不交易），
 * 可直接复用同目录 {@link MaxSubArray} 的 Kadane 思想。
 * <p>
 * 时间复杂度 O(n)（思路一、三），O(n^2)（思路二）；空间复杂度 O(1)（思路一、二），O(n)（思路三，可优化为 O(1)）。
 * <p>
 * 举一反三：把所有 diff[i] > 0 的差分全部累加，就变成「可以多次交易」的 LeetCode 122；
 * 若再限制「最多交易 k 次」则是 LeetCode 188 —— 都是本题的推广。
 */
public class MaxProfit {

    // ============ 方法一：一次遍历，维护历史最低价（推荐，代码最简） ============

    /**
     * des: 固定卖出日为第 i 天，最优买入价必然是此前的历史最低价
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            // 不足两天，无法完成「先买后卖」，利润为 0
            return 0;
        }

        // 历史最低价，初始为第一天的价格
        int minPrice = prices[0];
        // 最大利润，初始 0 表示「不交易」这一兜底选择
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            // 先以「今天卖出」更新答案：今天卖、历史最低价买
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            // 再更新历史最低价，供后面的天使用（先算利润再更新，语义上是「今天不能买卖」）
            minPrice = Math.min(minPrice, prices[i]);
        }

        return maxProfit;
    }

    // ============ 方法二：暴力枚举，用于对照理解题意 ============

    /**
     * des: 枚举所有 i &lt; j 的组合，复杂度 O(n^2)，仅用于理解与小数据验证
     */
    public int maxProfitByBruteForce(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int maxProfit = 0;
        // i 为买入日
        for (int i = 0; i < prices.length - 1; i++) {
            // j 为卖出日，必须严格晚于买入日
            for (int j = i + 1; j < prices.length; j++) {
                maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
            }
        }
        return maxProfit;
    }

    // ============ 方法三：转化为差分数组的最大子段和（Kadane） ============

    /**
     * des: 利润 = 差分数组的连续子段和，负则归零（不交易），与最大子数组和同源
     */
    public int maxProfitByKadane(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        // 以当前位置结尾的连续子段和（即「当前这段持仓」的浮动利润）
        int current = 0;
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            // diff = prices[i] - prices[i-1]，拒绝负收益就相当于「空仓重开」
            current = Math.max(0, current + prices[i] - prices[i - 1]);
            maxProfit = Math.max(maxProfit, current);
        }

        return maxProfit;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，第 2 天(1)买、第 5 天(6)卖，利润 5 ----------
        check(new int[]{7, 1, 5, 3, 6, 4}, 5);

        // ---------- 用例 2：官方示例，价格单边下跌，不交易，利润 0 ----------
        check(new int[]{7, 6, 4, 3, 1}, 0);

        // ---------- 用例 3：单边上涨，第一天买最后一天卖，利润 4 ----------
        check(new int[]{1, 2, 3, 4, 5}, 4);

        // ---------- 用例 4：边界——空数组 / 单元素，无法交易 ----------
        check(new int[]{}, 0);
        check(new int[]{5}, 0);

        // ---------- 用例 5：边界——最低价在最后一天，永远等不到卖出时机 ----------
        check(new int[]{3, 2, 1}, 0);

        // ---------- 用例 6：V 形走势，先跌到谷底再反弹，利润 8 ----------
        check(new int[]{9, 2, 8, 1, 9, 0}, 8);

        // ---------- 用例 7：最低价与最高价之间还夹着中间价，验证不会误选 ----------
        check(new int[]{2, 4, 1}, 2);
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[] prices, int expected) {
        MaxProfit solution = new MaxProfit();
        int r1 = solution.maxProfit(prices);
        int r2 = solution.maxProfitByBruteForce(prices);
        int r3 = solution.maxProfitByKadane(prices);

        boolean pass = r1 == expected && r2 == expected && r3 == expected;
        System.out.printf("prices = %s%n", java.util.Arrays.toString(prices));
        System.out.printf("  期望值   : %d%n", expected);
        System.out.printf("  一次遍历 : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  暴力枚举 : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  Kadane   : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }
}
