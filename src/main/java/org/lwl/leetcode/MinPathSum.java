package org.lwl.leetcode;

import java.util.Arrays;

/**
 * LeetCode 64 - 最小路径和（提交时将类名改为 Solution）
 * <p>
 * 题目：给定一个包含非负整数的 m x n 网格 grid，请找出一条从左上角到右下角的路径，
 * 使得路径上的数字总和为最小。每次只能向下或者向右移动一步。
 * <p>
 * 关键理解一：为什么「只能向右、向下」不是限制，而是解题的许可证？
 * 这条规则把网格变成一个<b>有向无环图</b>——所有边都指向右或下，你永远回不到已经走过的格子。
 * 无环带来两个决定性好处：
 * 1. 存在天然的拓扑序（从左上往右下逐行逐列推进），依赖项必然先于被依赖项算好，
 * 于是可以直接递推，连记忆化搜索的递归栈都省了；
 * 2. 不存在「环形依赖」，无需担心绕一圈回来把自己算进去。
 * 反过来说，如果上下左右四个方向都能走，问题会立刻升级成最短路，就得请 Dijkstra 出场了。
 * 所以别把这条规则当成束缚，它是这道题能用 DP 的前提条件。
 * <p>
 * 关键理解二：为什么贪心必错？
 * 最容易想到的贪心是「每步挑右边和下面中较小的那个格子」。它错在只看下一步，
 * 而完全无视那个选择会把后续的路引向何方。一个 3x2 的最小反例：
 * <pre>
 *      1   1
 *      2  100
 *      0   0
 * </pre>
 * 从 (0,0) 出发，右边是 1、下面是 2，贪心严格地选右边（1 < 2），
 * 随后被格子形状逼着经过 100，合计 1 + 1 + 100 + 0 = 102；
 * 而正确答案是先向下走 2，再一路沿着 0 抵达终点，合计 1 + 2 + 0 + 0 = 3。
 * 局部最优 ≠ 全局最优——贪心在第一步就输掉了整局。这类题必须用 DP 看全局。
 * <p>
 * 关键理解三：为什么可以 DP？需要同时满足两个条件。
 * 1. <b>最优子结构</b>：到达 (i,j) 的最优路径，其前缀必定也是到达 (i-1,j) 或 (i,j-1) 的最优路径。
 * 用反证法一句话说明——若存在一条更省钱的路线能到达 (i-1,j)，把它接上后就能得到更省钱的到 (i,j) 的路线，与最优性矛盾。
 * 2. <b>无后效性</b>：一旦站到 (i,j) 上，往后能怎么走只取决于当前位置，与「我是怎么来的」毫无关系。
 * 这条性质保证状态里只需要坐标两个维度，不必额外记录历史路径。
 * <p>
 * 核心思路：定义状态 dp[i][j] = 从 (0,0) 走到 (i,j) 的最小路径和，则
 * <pre>
 *      dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])
 * </pre>
 * 含义直白：既然最后一步只能从上方或左方踏进来，那就看这两条路谁更省，再加上当前格子的代价。
 * 边界只有两种：第一行只能从左边来，第一列只能从上边来，各自累加即可；起点 dp[0][0] = grid[0][0]。
 * <p>
 * 空间优化的演进（三个方法其实共享同一个递推式，只是状态存得越来越省）：
 * 二维表 O(mn) → 一维滚动数组 O(n) → 原地覆盖 O(1)。
 * 其中滚动数组最值得琢磨：在更新 dp[j] 的那一瞬间，
 * <b>dp[j] 里还躺着上一行的旧值（即 dp[i-1][j]），而 dp[j-1] 已经是本行刚算出的新值（即 dp[i][j-1]）</b>，
 * 恰好凑齐转移方程需要的两个操作数，一行代码同时完成取旧值、比大小、写新值。
 * <p>
 * 时间复杂度 O(m * n)：每个格子恰好被访问一次，转移是 O(1)。
 * 空间复杂度：二维表 O(m * n)，滚动数组 O(n)，原地覆盖 O(1)（不含返回值）。
 * <p>
 * 举一反三：网格 DP 是一整类题，辨认特征是「有向无环的移动规则 + 求最优值」，套路高度统一——
 * - <b>同一骨架换个聚合函数就是新题</b>：LeetCode 62（不同路径）把 min 换成加法，
 *   dp[i][j] = dp[i-1][j] + dp[i][j-1]，一模一样的三层循环，求的却是路径条数；
 *   LeetCode 63（不同路径 II）再往 62 上加障碍物，遇到障碍把该格置 0 即可；
 * - LeetCode 120（三角形最小路径和）：形状从矩形变成三角形，仍是从上往下推，
 *   自底向上写反而更省事，dp[j] = min(dp[j], dp[j+1]) + grid[i][j]；
 * - LeetCode 931（下降路径最小和）：每步允许三个方向，但依然无环，递推照样成立；
 * - LeetCode 174（地下城游戏）：反向 DP 的经典教科书案例。因为「血量全程不能为负」这条约束
 *   破坏了正向递推的最优子结构，必须从终点倒着推，是理解「DP 方向由约束决定」的绝佳材料；
 * - 空间优化的通用判据：只要 dp[i][j] 只依赖相邻的上一行与本行，
 *   就能把二维表压成一维滚动数组，这套手法同样适用于背包问题、编辑距离等一大票题目。
 * <p>
 * 最后提醒一句：题目保证数字非负，但 DP 本身并不依赖这个条件——
 * 在无环图上，即使出现负数，最优子结构照样成立。非负是现实场景（比如耗费体力、路程开销）的设定，
 * 而不是算法的前提。真正不能碰的是「有环」，那才需要换算法。
 */
public class MinPathSum {

    // ============ 方法一：二维 DP（最标准的教科书写法，推荐用于理解） ============

    /**
     * des: 显式建一张 m x n 的 dp 表，把每个格子的最小路径和逐行逐列算出来
     */
    public int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // 起点：路径上只有它自己
        dp[0][0] = grid[0][0];

        // 第一行：上方无路可走，只能一路从左边累加过来
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // 第一列：左方无路可走，只能一路从上边累加下来
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // 其余格子：最后一步要么从上方踏进来，要么从左方踏进来，取更省的那条
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    //自顶向下
    int[][] memo;
    public int minPathSumByMemo(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        memo = new int[m][n];
        for (int[] ints : memo) {
            for (int i = 0; i < ints.length; i++) {
                ints[i] = -1;
            }
        }
        return dp(grid, m - 1, n - 1);
    }

    private int dp(int[][] grid, int i, int j) {
        if (i == 0 && j == 0){
            return grid[0][0];
        }
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        memo[i][j] = Math.min(dp(grid, i - 1, j), dp(grid, i, j - 1)) + grid[i][j];
        return memo[i][j];
    }

    // ============ 方法二：一维滚动数组（空间从 O(mn) 降到 O(n)） ============

    /**
     * des: dp[i][j] 只依赖「上一行」和「本行左边」，于是整张表可以用一行数组滚起来
     * <p>
     * 精妙之处全在 dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j] 这一行：
     * 赋值前，dp[j] 尚未被本轮刷新，装的还是上一行的值，正是 dp[i-1][j]；
     * 而 dp[j-1] 因为 j 从小到大推进，早已被本轮刷新过，正是 dp[i][j-1]。
     * 一个变量身兼新旧两职，刚刚好凑齐转移方程需要的两个操作数。
     */
    public int minPathSumByRollingArray(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];

        // 先铺好第一行：此时 dp[j] 表示 dp[0][j]
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            // 本行第一列只能从上边来，dp[0] 自上而下持续累加即可
            dp[0] += grid[i][0];

            for (int j = 1; j < n; j++) {
                // dp[j] = 上一行的值，dp[j-1] = 本行左边的值，取小者再加上当前格
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        }

        return dp[n - 1];
    }

    // ============ 方法三：原地覆盖（空间 O(1)，代码也最短） ============

    /**
     * des: 连 dp 数组都不开，直接把算好的最小路径和写回 grid 本身
     * <p>
     * 之所以敢覆盖，是因为 grid[i][j] 被读取的时机只有两次——计算右邻和下邻的时候，
     * 而那时它早已完成自己的使命。每个格子的原始值用过即弃，原地改写不会影响后续计算。
     * 代价是破坏了输入数据，工程中若调用方还要用原始网格，务必先深拷贝（注意逐行复制，
     * 浅拷贝外层数组仍会污染内层的 int[] 对象）。
     */
    public int minPathSumInPlace(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    continue;                       // 起点无需改动
                } else if (i == 0) {
                    grid[i][j] += grid[i][j - 1];   // 顶行只能从左边来
                } else if (j == 0) {
                    grid[i][j] += grid[i - 1][j];   // 首列只能从上边来
                } else {
                    grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
                }
            }
        }

        return grid[m - 1][n - 1];
    }

    // ============ 错误示范：贪心（仅用于对照，切勿使用） ============

    /**
     * des: 【错误示范】每步只挑右边和下面中较小的那个格子
     * <p>
     * 它只在「当前这一步」最优，对后续会踩到什么大坑一无所知。
     * main 中用 [[1,1],[2,100],[0,0]] 演示：贪心给出 102，而正确答案是 3。
     */
    private static int greedyWrong(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int i = 0;
        int j = 0;
        int sum = grid[0][0];

        while (i < m - 1 || j < n - 1) {
            if (i == m - 1) {
                j++;                                // 到底了，只能往右
            } else if (j == n - 1) {
                i++;                                // 到边了，只能往下
            } else if (grid[i][j + 1] <= grid[i + 1][j]) {
                j++;                                // 右边更小，往右走
            } else {
                i++;                                // 下边更小，往下走
            }
            sum += grid[i][j];
        }

        return sum;
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // ---------- 用例 1：官方示例，需要在 3 与 5 之间做出选择 ----------
        check(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}, 7);

        // ---------- 用例 2：官方示例，两行三列 ----------
        check(new int[][]{{1, 2, 3}, {4, 5, 6}}, 12);

        // ---------- 用例 3：只有一格，起点即终点 ----------
        check(new int[][]{{5}}, 5);

        // ---------- 用例 4：单行，没有任何选择余地 ----------
        check(new int[][]{{1, 2, 3}}, 6);

        // ---------- 用例 5：单列，同样只能一路走到底 ----------
        check(new int[][]{{1}, {2}, {3}}, 6);

        // ---------- 用例 6：贪心反例，仔细体会「局部最优 ≠ 全局最优」 ----------
        // 贪心严格地选 1 而非 2，结果被逼着吃下 100，得 102；正解是先下后沿 0 走，得 3
        check(new int[][]{{1, 1}, {2, 100}, {0, 0}}, 3);

        // ---------- 用例 7：全零网格，答案必为 0 ----------
        check(new int[][]{{0, 0}, {0, 0}}, 0);

        // ---------- 用例 8：严格递增，任何一条单调路径的代价都相同 ----------
        check(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 21);

        // ---------- 用例 9：中央是陷阱，绕边走反而更省 ----------
        check(new int[][]{{1, 1, 1}, {1, 9, 9}, {1, 1, 1}}, 5);

        // ---------- 用例 10：整体最小的格子不在第一步，但必须先付出较多的一步 ----------
        check(new int[][]{{1, 2}, {1, 1}}, 3);

        // ---------- 用例 11：100 x 100 全 1，验证大规模下三条路径的代价一致 ----------
        // 路径经过的格子数恒为 m + n - 1 = 199，每格为 1
        int size = 100;
        int[][] big = new int[size][size];
        for (int[] row : big) {
            Arrays.fill(row, 1);
        }
        check(big, size + size - 1);

        // ---------- 附加演示：用用例 6 的网格直观展示贪心为何失效 ----------
        demonstrateGreedyFailure();
    }

    /** des: 三种解法跑同一组用例并比对期望结果，防止某种写法在边界上跑偏 */
    private static void check(int[][] grid, int expected) {
        MinPathSum solution = new MinPathSum();

        // 三个方法各自传入深拷贝，确保每个方法都在干净输入上独立运行
        int r1 = solution.minPathSum(copy(grid));
        int r2 = solution.minPathSumByRollingArray(copy(grid));
        int r3 = solution.minPathSumInPlace(copy(grid));

        boolean pass = r1 == expected && r2 == expected && r3 == expected;

        System.out.printf("grid = %s%n", brief(grid));
        System.out.printf("  期望值     : %d%n", expected);
        System.out.printf("  二维 DP    : %d%s%n", r1, r1 == expected ? "  ✅" : "  ❌");
        System.out.printf("  滚动数组   : %d%s%n", r2, r2 == expected ? "  ✅" : "  ❌");
        System.out.printf("  原地覆盖   : %d%s%n", r3, r3 == expected ? "  ✅" : "  ❌");
        System.out.println(pass ? "  => 通过" : "  => 失败");
        System.out.println();
    }

    /** des: 演示贪心为何在本题失效 */
    private static void demonstrateGreedyFailure() {
        int[][] trap = {{1, 1}, {2, 100}, {0, 0}};
        MinPathSum solution = new MinPathSum();

        System.out.println("========== 附：贪心为何必错 ==========");
        System.out.printf("grid = %s%n", Arrays.deepToString(trap));
        System.out.println("  从 (0,0) 出发，右边 1 比下边 2 小，贪心严格地选择了往右：");
        System.out.println("    1 -> 1 -> 100 -> 0   合计 " + greedyWrong(trap));
        System.out.println("  而正确答案是先向下走 2，再一路沿着 0 抵达终点：");
        System.out.println("    1 -> 2 -> 0 -> 0     合计 " + solution.minPathSum(copy(trap)));
        System.out.println("  贪心在第一步就输掉了整局。");
        System.out.println();
    }

    /** des: 二维数组深拷贝，内层也要复制，否则原地解法会污染调用方的数据 */
    private static int[][] copy(int[][] src) {
        int[][] dst = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dst;
    }

    /** des: 网格过大时只打印尺寸和首行，避免测试输出刷屏 */
    private static String brief(int[][] grid) {
        if (grid.length <= 5 && grid[0].length <= 8) {
            return Arrays.deepToString(grid);
        }
        return grid.length + " x " + grid[0].length + " 网格，首行 = "
                + Arrays.toString(Arrays.copyOf(grid[0], 8)) + " ...";
    }
}
