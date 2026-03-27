package org.lwl.algorithms.backtrace;

/**
 *
 * @author lwl
 * @date 2025/1/21
 * @description 回溯。八皇后问题
 *
 *  在 8×8 的棋盘上放置 8 个皇后，使得任意两个皇后不能在同一行、同一列、同一斜线上。
 *  使用回溯算法枚举所有合法解并打印输出，共有 92 个解。
 *
 *  回溯模板：
 *  backtrack(row):
 *      if row == 8: 找到一个解，记录并返回
 *      for col in 0..7:
 *          if isValid(queens, row, col):
 *              做选择: queens[row] = col
 *              backtrack(row + 1)
 *              撤销选择: queens[row] = -1
 *
 */
public class EightQueens {

    public static void main(String[] args) {
        int count = solve();
        System.out.println("八皇后问题共有 " + count + " 个解");
    }

    /**
     * 求解八皇后问题，返回解的总数
     **/
    public static int solve() {
        int[] queens = new int[8]; // queens[i] 表示第 i 行皇后所在的列，-1 表示未放置
        for (int i = 0; i < 8; i++) {
            queens[i] = -1;
        }
        return backtrack(queens, 0);
    }

    /**
     * 回溯：逐行放置皇后
     **/
    private static int backtrack(int[] queens, int row) {
        if (row == 8) {
            // 找到一个合法解，打印并计数
            printBoard(queens);
            return 1;
        }

        int count = 0;
        for (int col = 0; col < 8; col++) {
            if (!isValid(queens, row, col)) {
                continue; // 当前列不合法，跳过
            }
            queens[row] = col; // 做选择：将皇后放在第 row 行第 col 列
            count += backtrack(queens, row + 1); // 递归处理下一行
            queens[row] = -1; // 撤销选择
        }
        return count;
    }

    /**
     * 判断在第 row 行第 col 列放置皇后是否合法
     **/
    private static boolean isValid(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col) {
                return false; // 同列冲突
            }
            if (Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false; // 对角线冲突
            }
        }
        return true;
    }

    /**
     * 打印棋盘，Q 表示皇后，. 表示空格
     **/
    private static void printBoard(int[] queens) {
        System.out.println("----解----");
        for (int i = 0; i < 8; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                sb.append(queens[i] == j ? "Q" : "."); // 皇后位置打印 Q，其余打印点
                if (j < 7) {
                    sb.append(" ");
                }
            }
            System.out.println(sb);
        }
    }
}
